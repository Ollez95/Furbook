package com.example.home.ui.main

import com.example.core.domain.shared.model.User

data class MainState(
    val isLoading: Boolean = false,
    val user: User = User()
)