package com.example.navigation

interface Navigator {
    fun navigateToDestinationCleaningStack(destination: NavigationDestination)
    fun navigateBack()
    fun navigateToDestination(destination: NavigationDestination)
    fun navigateWithSafety(destination: NavigationDestination)
}