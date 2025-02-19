package com.example.authentication.ui.register

import com.example.core.domain.authentication.register.model.RegisterModel

data class RegisterState(
    val registerModel: RegisterModel = RegisterModel(),
    val isLoading: Boolean = false,
)