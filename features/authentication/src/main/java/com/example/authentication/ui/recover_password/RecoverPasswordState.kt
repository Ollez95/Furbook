package com.example.authentication.ui.recover_password

import androidx.compose.material3.SnackbarHostState

data class RecoverPasswordState(
    val email: String = "",
    val isLoading: Boolean = false,
    val snackBarHostState: SnackbarHostState = SnackbarHostState()
)




