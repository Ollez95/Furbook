package com.example.authentication.ui.register

import androidx.compose.material3.SnackbarHostState
import com.example.core.domain.authentication.register.model.RegisterModel

data class RegisterState(
    val registerModel: RegisterModel = RegisterModel(),
    val isLoading: Boolean = false,
    val snackBarHostState: SnackbarHostState = SnackbarHostState())