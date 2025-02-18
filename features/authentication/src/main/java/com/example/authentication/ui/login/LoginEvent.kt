package com.example.authentication.ui.login

sealed class LoginEvent {
    data class EmailChanged(val email: String) : LoginEvent()
    data class PasswordChanged(val password: String) : LoginEvent()
    data class PasswordVisibilityChanged(val isVisible: Boolean) : LoginEvent()
    data object NavigateToForgotPassword : LoginEvent()
    data object NavigateToSignUp : LoginEvent()
    data object NavigateToAuthenticateWithGoogle : LoginEvent()
    data object Login : LoginEvent()
    data object LoginSuccess : LoginEvent()
    data class LoginError(val message: String) : LoginEvent()
}