package com.example.onboarding.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.navigation.Navigator
import com.example.navigation.OnBoardingNavigation
import com.example.onboarding.ui.OnBoardingScreen

fun NavGraphBuilder.onBoardingGraph(navigator: Navigator) {
    composable<OnBoardingNavigation.Onboarding> {
        OnBoardingScreen(navigator = navigator)
    }
}