package com.example.navigation

import android.annotation.SuppressLint
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import timber.log.Timber

private const val THROTTLE_INTERVAL = 500L // Throttle time in milliseconds
private const val CURRENT_STACK_ROUTE = "Current stack route: "
private var LAST_NAVIGATION_TIME = 0L

fun NavController.navigateToDestination(destination: NavigationDestination) {
    addThrottleTime()
    this.navigate(destination)
    logBackStack()
}

fun NavController.navigateBack() {
    addThrottleTime()
    this.navigateUp()
    logBackStack()
}

fun NavController.navigateWithSafety(destination: NavigationDestination) {
    addThrottleTime()
    with(this) {
        val isNavigatedBack = popBackStack(destination, inclusive = false)

        if (!isNavigatedBack) {
            navigate(destination)
        }
    }
    logBackStack()
}
fun NavController.navigateToDestinationCleaningStack(
    navigationToClean: NavigationDestination,
    navigateToDestination: NavigationDestination,
    saveCurrentState: Boolean = false,  // 🔹 False to avoid unnecessary state restoration
    cleanCurrentNavigation: Boolean = true,  // 🔹 True to remove the cleaned destination
    useTheSameInstance: Boolean = true,  // 🔹 True to prevent duplicate instances
    restorePreviousState: Boolean = false, // 🔹 False to avoid restoring previous states incorrectly
) {
    addThrottleTime()
    navigate(navigateToDestination) {
        popUpTo(navigationToClean) {
            saveState = saveCurrentState
            inclusive = cleanCurrentNavigation
        }
        launchSingleTop = useTheSameInstance
        restoreState = restorePreviousState
    }
    logBackStack()
}

@SuppressLint("RestrictedApi")
fun NavController.logBackStack() {
    val listWithRoutes = mutableListOf<String>()
    this.addOnDestinationChangedListener { controller, _, _ ->
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

private fun addThrottleTime() {
    val currentTime = System.currentTimeMillis()

    if (currentTime - LAST_NAVIGATION_TIME < THROTTLE_INTERVAL) {
        return // Ignore rapid consecutive navigations
    }

    LAST_NAVIGATION_TIME = currentTime
}