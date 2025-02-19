package com.example.authentication.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.authentication.login.usecase.LoginAccountUseCase
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
    private val getLoginAccountUseCase: LoginAccountUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state

    private val _eventFlow = MutableSharedFlow<LoginEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.PasswordChanged -> onPasswordChanges(event.password)
            is LoginEvent.PasswordVisibilityChanged -> onPasswordVisibilityChanges(event.isVisible)
            is LoginEvent.Login -> login(state.value.loginModel.email, state.value.loginModel.password)
            is LoginEvent.EmailChanged -> onEmailChanges(event.email)
            is LoginEvent.NavigateToForgotPassword -> navigateToForgotPassword()
            is LoginEvent.NavigateToSignUp -> navigateToSignUp()
            is LoginEvent.NavigateToAuthenticateWithGoogle -> navigateToAuthenticateWithGoogle()
            else -> { /* Ignore */
            }
        }
    }

    private fun login(email: String, password: String) {
        viewModelScope.launch {
            getLoginAccountUseCase.invoke(email, password).collectLatest { response ->
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
                        _eventFlow.emit(LoginEvent.LoginError(response.message))
                    }
                }
            }
        }
    }

    private fun onPasswordChanges(password: String) {
        _state.update { it.copy(loginModel = it.loginModel.copy(password = password)) }
    }

    private fun onPasswordVisibilityChanges(isVisible: Boolean) {
        _state.update { it.copy(isPasswordVisible = isVisible) }
    }

    private fun onEmailChanges(email: String) {
        _state.update { it.copy(loginModel = it.loginModel.copy(email = email)) }
    }

    private fun navigateToForgotPassword() {
        viewModelScope.launch {
            _eventFlow.emit(LoginEvent.NavigateToForgotPassword)
        }
    }

    private fun navigateToSignUp() {
        viewModelScope.launch {
            _eventFlow.emit(LoginEvent.NavigateToSignUp)
        }
    }

    private fun navigateToAuthenticateWithGoogle() {
        viewModelScope.launch {
            _eventFlow.emit(LoginEvent.NavigateToAuthenticateWithGoogle)
        }
    }
}