package com.example.authentication.ui.login

import com.example.core.domain.authentication.login.models.LoginModel

data class LoginState(
    val loginModel: LoginModel = LoginModel(),
    val isLoading: Boolean = false,
    val isPasswordVisible: Boolean = false,
)