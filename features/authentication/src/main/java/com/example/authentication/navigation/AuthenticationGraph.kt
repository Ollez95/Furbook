package com.example.authentication.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.authentication.ui.login.LoginScreen
import com.example.authentication.ui.recover_password.RecoverPasswordScreen
import com.example.authentication.ui.register.RegisterScreen
import com.example.navigation.AuthenticationNavigation

fun NavGraphBuilder.authenticationGraph(navController: NavController) {
    composable<AuthenticationNavigation.Login> {
        LoginScreen(navController = navController)
    }
    composable<AuthenticationNavigation.Register> {
        RegisterScreen(navController = navController)
    }
    composable<AuthenticationNavigation.RecoverPassword> {
        RecoverPasswordScreen(navController = navController)
    }
}