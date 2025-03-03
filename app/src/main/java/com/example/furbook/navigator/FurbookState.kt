package com.example.furbook.navigator

data class FurbookState(
    val isLoading: Boolean = true,
    val isOnboardingCompleted: Boolean = false,
    val isUserAuthenticated: Boolean = false
)