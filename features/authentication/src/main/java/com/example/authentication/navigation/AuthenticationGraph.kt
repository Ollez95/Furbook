package com.example.authentication.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.authentication.ui.login.LoginScreen
import com.example.authentication.ui.register.RegisterScreen
import com.example.navigation.AuthenticationNavigation
import com.example.navigation.Navigator

fun NavGraphBuilder.authenticationGraph(navigator: Navigator) {
    composable<AuthenticationNavigation.Login> {
        LoginScreen(navigator = navigator)
    }
    composable<AuthenticationNavigation.Register> {
        RegisterScreen()
    }
    composable<AuthenticationNavigation.ForgotPassword> {
        RegisterScreen()
    }
    composable<AuthenticationNavigation.Register> {
        RegisterScreen()
    }
}