package com.example.authentication.ui.register

interface RegisterEvent {
    data class UsernameChanged(val username: String) : RegisterEvent
    data class EmailChanged(val email: String) : RegisterEvent
    data class PasswordChanged(val password: String) : RegisterEvent
    data class PasswordConfirmationChanged(val passwordConfirmation: String) : RegisterEvent
    data object Register : RegisterEvent
    data object RegisterSuccess : RegisterEvent
    data class RegisterError(val message: String) : RegisterEvent
    data object NavigateToLogin : RegisterEvent
}