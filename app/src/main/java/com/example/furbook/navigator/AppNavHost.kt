package com.example.furbook.navigator

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.authentication.navigation.authenticationGraph
import com.example.navigation.OnBoardingNavigation
import com.example.onboarding.navigation.onBoardingGraph

@Composable
fun FurbookNavigation() {
    val navController: NavHostController = rememberNavController()
    val navigator = FurbookNavigator(navController)

    NavHost(navController = navController, startDestination = OnBoardingNavigation.Onboarding) {
        onBoardingGraph(navigator = navigator)
        authenticationGraph(navigator = navigator)
    }
}
