package com.example.authentication.ui.login

sealed class LoginEvent {
    data class EmailChanged(val email: String) : LoginEvent()
    data class PasswordChanged(val password: String) : LoginEvent()
    data class PasswordVisibilityChanged(val isVisible: Boolean) : LoginEvent()
    data object ForgotPassword : LoginEvent()
    data object SignUp : LoginEvent()
    data object AuthenticateWithGoogle : LoginEvent()
    data object Login : LoginEvent()
    data object LoginSuccess : LoginEvent()
    data class Error(val message: String) : LoginEvent()
}