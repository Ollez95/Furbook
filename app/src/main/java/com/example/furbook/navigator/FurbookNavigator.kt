package com.example.furbook.navigator

import androidx.navigation.NavController
import com.example.navigation.NavigationDestination
import com.example.navigation.Navigator

class FurbookNavigator(private val navController: NavController) : Navigator {
    override fun navigateToDestinationCleaningStack(destination: NavigationDestination) {
        navController.navigate(destination) {
            popUpTo(navController.graph.startDestinationId) {
                inclusive = true // ✅ Removes all previous destinations
            }
            launchSingleTop = true
        }
    }

    override fun navigateBack() {
        navController.navigateUp()
    }

    override fun navigateToDestination(destination: NavigationDestination) {
        navController.navigate(destination)
    }

    override fun navigateWithSafety(destination: NavigationDestination) {
        val currentTime = System.currentTimeMillis()

        if (currentTime - LAST_NAVIGATION_TIME < THROTTLE_INTERVAL) {
            return // Ignore rapid consecutive navigations
        }

        LAST_NAVIGATION_TIME = currentTime

        with(navController) {
            val isNavigatedBack = popBackStack(destination, inclusive = false)

            if (!isNavigatedBack) {
                navigate(destination)
            }
        }
    }

    companion object {
        private var LAST_NAVIGATION_TIME = 0L
        private const val THROTTLE_INTERVAL = 500L // Throttle time in milliseconds
    }
}