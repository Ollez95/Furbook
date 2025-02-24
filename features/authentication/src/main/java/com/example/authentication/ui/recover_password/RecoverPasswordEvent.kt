package com.example.authentication.ui.recover_password

interface RecoverPasswordEvent {
    data class EmailChanged(val email: String) : RecoverPasswordEvent
    data object RecoverPassword : RecoverPasswordEvent
    data object RecoverPasswordSuccess : RecoverPasswordEvent
    data object NavigateToLogin : RecoverPasswordEvent
    data class RecoverPasswordError(val message: String) : RecoverPasswordEvent
}