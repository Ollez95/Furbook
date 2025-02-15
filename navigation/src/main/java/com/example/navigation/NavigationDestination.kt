package com.example.navigation

import kotlinx.serialization.Serializable

interface NavigationDestination

@Serializable
sealed class OnBoardingNavigation : NavigationDestination {

    @Serializable
    data object Onboarding : OnBoardingNavigation()
}

@Serializable
sealed class AuthenticationNavigation : NavigationDestination {
    @Serializable
    data object Register : AuthenticationNavigation()

    @Serializable
    data object Login : AuthenticationNavigation()

    @Serializable
    data class ForgetPassword(val email: String) : AuthenticationNavigation()
}