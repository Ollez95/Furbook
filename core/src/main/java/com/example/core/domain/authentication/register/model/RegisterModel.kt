package com.example.core.domain.authentication.register.model

data class RegisterModel(
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val passwordConfirmation: String = ""
)