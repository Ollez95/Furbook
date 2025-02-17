package com.example.authentication.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.authentication.domain.login.repository.LoginRepository
import com.example.core.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: LoginRepository
): ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state

    private val _eventFlow = MutableSharedFlow<LoginEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.PasswordChanged -> {
                _state.update { it.copy(password = event.password) }
            }

            is LoginEvent.PasswordVisibilityChanged -> {
                _state.update { it.copy(isPasswordVisible = event.isVisible) }
            }
            is LoginEvent.Login -> {
                login(state.value.email, state.value.password)
            }

            is LoginEvent.EmailChanged -> {
                _state.update { it.copy(email = event.email) }
            }

            is LoginEvent.ForgotPassword -> {
                viewModelScope.launch { _eventFlow.emit(LoginEvent.ForgotPassword) }
            }
            is LoginEvent.SignUp -> {
                viewModelScope.launch { _eventFlow.emit(LoginEvent.SignUp) }
            }
            is LoginEvent.AuthenticateWithGoogle -> {
                viewModelScope.launch { _eventFlow.emit(LoginEvent.AuthenticateWithGoogle) }
            }
            else -> {}
        }

    }

    private fun login(email: String, password: String) {
        viewModelScope.launch {
            repository.login(email, password).collectLatest { response ->
                when (response) {
                    is Response.Loading -> {
                        _state.update { it.copy(isLoading = true) }
                    }
                    is Response.Success -> {
                        _state.update { it.copy(isLoading = false) }
                        _eventFlow.emit(LoginEvent.LoginSuccess)  // Correct success event
                    }
                    is Response.Error -> {
                        _state.update { it.copy(isLoading = false) }
                        _eventFlow.emit(LoginEvent.Error(response.message))
                    }
                }
            }
        }
    }

}