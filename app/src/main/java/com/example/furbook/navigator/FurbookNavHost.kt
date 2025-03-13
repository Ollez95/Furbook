package com.example.furbook.navigator

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.authentication.navigation.authenticationGraph
import com.example.home.navigation.homeGraph
import com.example.navigation.AuthenticationNavigation
import com.example.navigation.BottomSheetNavigation
import com.example.navigation.NavigationDestination
import com.example.navigation.OnBoardingNavigation
import com.example.onboarding.navigation.onBoardingGraph


@Composable
fun FurbookNavigation(state: FurbookState) {
    val navController: NavHostController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination(state)
    )
    {
        onBoardingGraph(navController = navController)
        authenticationGraph(navController = navController)

        navigation<BottomSheetNavigation.BottomNavigation>(startDestination = BottomSheetNavigation.Main) {
            homeGraph(navController = navController)
        }
    }
}

private fun startDestination(state: FurbookState): NavigationDestination {
    return when {
        !state.isOnboardingCompleted -> OnBoardingNavigation.Onboarding
        !state.isUserAuthenticated -> AuthenticationNavigation.Login
        else -> BottomSheetNavigation.BottomNavigation
    }
}



