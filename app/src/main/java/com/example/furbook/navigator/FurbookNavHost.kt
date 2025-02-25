package com.example.furbook.navigator

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.authentication.navigation.authenticationGraph
import com.example.home.navigation.homeGraph
import com.example.navigation.AuthenticationNavigation
import com.example.navigation.HomeNavigation
import com.example.navigation.NavigationDestination
import com.example.navigation.OnBoardingNavigation
import com.example.onboarding.navigation.onBoardingGraph


@Composable
fun FurbookNavigation(state: FurbookState) {
    val navController: NavHostController = rememberNavController()
    val navigator = FurbookNavigator(navController)

    NavHost(navController = navController, startDestination = startDestination(state)) {
        onBoardingGraph(navigator = navigator)
        authenticationGraph(navigator = navigator)
        homeGraph(navigator)
    }
}

private fun startDestination(state: FurbookState): NavigationDestination {
    return when {
        !state.isOnboardingCompleted -> OnBoardingNavigation.Onboarding
        !state.isUserAuthenticated -> AuthenticationNavigation.Login
        else -> HomeNavigation.Main
    }
}
