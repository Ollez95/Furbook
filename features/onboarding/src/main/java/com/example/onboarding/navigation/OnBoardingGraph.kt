package com.example.onboarding.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.navigation.OnBoardingNavigation
import com.example.onboarding.ui.OnBoardingScreen

fun NavGraphBuilder.onBoardingGraph(navController: NavController) {
    composable<OnBoardingNavigation.Onboarding> {
        OnBoardingScreen(navController = navController)
    }
}