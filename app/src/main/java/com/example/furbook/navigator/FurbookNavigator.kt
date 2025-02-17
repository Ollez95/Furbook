package com.example.furbook.navigator

import androidx.navigation.NavController
import com.example.navigation.NavigationDestination
import com.example.navigation.Navigator

class FurbookNavigator(private val navController: NavController) : Navigator {
    override fun navigateToDestinationCleaningStack(destination: NavigationDestination) {
        navController.navigate(destination) {
            popUpTo(navController.currentBackStackEntry?.destination?.route ?: return@navigate) { inclusive = true }
            launchSingleTop = true
        }

    }

    override fun navigateBack() {
        navController.navigateUp()
    }

    override fun navigateToDestination(destination: NavigationDestination) {
        navController.navigate(destination)
    }
}