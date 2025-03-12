package com.example.home.ui.home

import com.example.core.domain.shared.model.User

data class HomeState(
    val isLoading: Boolean = false,
    val user: User? = null
)
