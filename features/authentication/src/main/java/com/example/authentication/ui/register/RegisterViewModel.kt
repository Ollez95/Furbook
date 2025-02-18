package com.example.authentication.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.authentication.register.repository.RegisterRepository
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
class RegisterViewModel @Inject constructor(
    private val repository: RegisterRepository
): ViewModel() {

    private val _state = MutableStateFlow(RegisterState())
    val state: StateFlow<RegisterState> = _state

    private val _eventFlow = MutableSharedFlow<RegisterEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.Register -> register(state.value.registerModel.username, state.value.registerModel.email,
                state.value.registerModel.password, state.value.registerModel.passwordConfirmation)
            is RegisterEvent.UsernameChanged -> _state.update { it.copy(registerModel = it.registerModel.copy(username = event.username)) }
            is RegisterEvent.EmailChanged -> _state.update { it.copy(registerModel = it.registerModel.copy(email = event.email)) }
            is RegisterEvent.PasswordChanged -> _state.update { it.copy(registerModel = it.registerModel.copy(password = event.password)) }
            is RegisterEvent.PasswordConfirmationChanged -> _state.update { it.copy(registerModel = it.registerModel.copy(passwordConfirmation = event.passwordConfirmation)) }

            /*is LoginEvent.PasswordChanged -> _state.update { it.copy(loginModel = it.loginModel.copy(password = event.password)) }
            is LoginEvent.PasswordVisibilityChanged -> _state.update { it.copy(isPasswordVisible = event.isVisible) }
            is LoginEvent.Login -> login(state.value.loginModel.email, state.value.loginModel.password)
            is LoginEvent.EmailChanged -> _state.update { it.copy(loginModel = it.loginModel.copy(email = event.email)) }
            is LoginEvent.NavigateToForgotPassword -> viewModelScope.launch { _eventFlow.emit(LoginEvent.NavigateToForgotPassword) }
            is LoginEvent.NavigateToSignUp -> viewModelScope.launch { _eventFlow.emit(LoginEvent.NavigateToSignUp) }
            is LoginEvent.NavigateToAuthenticateWithGoogle -> viewModelScope.launch { _eventFlow.emit(LoginEvent.NavigateToAuthenticateWithGoogle) }
            else -> { /* Ignore */
            }*/
        }
    }

    private fun register(username: String, email: String, password: String, confirmationPassword: String) {
        viewModelScope.launch {
            repository.register(username, email, password, confirmationPassword).collectLatest { response ->
                when (response) {
                    is Response.Loading -> {
                        _state.update { it.copy(isLoading = true) }
                    }

                    is Response.Success -> {
                        _state.update { it.copy(isLoading = false) }
                        _eventFlow.emit(RegisterEvent.RegisterSuccess)  // Correct success event
                    }

                    is Response.Error -> {
                        _state.update { it.copy(isLoading = false) }
                        _eventFlow.emit(RegisterEvent.RegisterError(response.message))
                    }
                }
            }
        }
    }
}