package com.example.navigation

import android.annotation.SuppressLint
import androidx.navigation.NavController
import timber.log.Timber

class NavigatorImpl(private val navController: NavController) : Navigator {

    override fun navigateToDestinationCleaningStack(
        cleanCurrentNavigation: Boolean,
        navigationToClean: NavigationDestination,
        navigateToDestination: NavigationDestination,
    ) {
        addThrottleTime()
        navController.navigate(navigateToDestination) {
            popUpTo(navigationToClean) {
                inclusive = cleanCurrentNavigation
            }
            launchSingleTop = true
        }
        logBackStack(navController)
    }

    override fun navigateBack() {
        addThrottleTime()
        navController.navigateUp()
        logBackStack(navController)
    }

    override fun navigateToDestination(destination: NavigationDestination) {
        addThrottleTime()
        navController.navigate(destination)
        logBackStack(navController)
    }

    override fun navigateWithSafety(destination: NavigationDestination) {
        addThrottleTime()

        with(navController) {
            val isNavigatedBack = popBackStack(destination, inclusive = false)

            if (!isNavigatedBack) {
                navigate(destination)
            }
        }
        logBackStack(navController)
    }

    private fun addThrottleTime() {
        val currentTime = System.currentTimeMillis()

        if (currentTime - LAST_NAVIGATION_TIME < THROTTLE_INTERVAL) {
            return // Ignore rapid consecutive navigations
        }

        LAST_NAVIGATION_TIME = currentTime
    }

    @SuppressLint("RestrictedApi")
    private fun logBackStack(navController: NavController) {
        val listWithRoutes = mutableListOf<String>()
        navController.addOnDestinationChangedListener { controller, _, _ ->
            val route = controller
                .currentBackStack.value
                .map { it.destination.route }
                .joinToString(", ")

            if (route !in listWithRoutes) {
                listWithRoutes.add(route)
            }
        }
        Timber.d(CURRENT_STACK_ROUTE + listWithRoutes.last())
    }

    companion object {
        private var LAST_NAVIGATION_TIME = 0L
        private const val THROTTLE_INTERVAL = 500L // Throttle time in milliseconds
        private const val CURRENT_STACK_ROUTE = "Current stack route: "
    }
}


