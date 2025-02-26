package com.example.core.navigation.model

data class NavigationResponse(
    val isOnboardingCompleted: Boolean,
    val isUserAuthenticated: Boolean,
    val userId: String
)
