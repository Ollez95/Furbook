package com.example.navigation

interface Navigator {
    /**
     *Navigate to a destination and clean the navigation stack
     * @param cleanCurrentNavigation: Boolean to clean the current navigation stack
     * @param navigationToClean: NavigationDestination to clean
     * @param navigateToDestination: NavigationDestination to navigate
     */
    fun navigateToDestinationCleaningStack(cleanCurrentNavigation: Boolean,
                                           navigationToClean: NavigationDestination,
                                           navigateToDestination: NavigationDestination)

    /**
     * Navigate back to the previous screen
     */
    fun navigateBack()

    /**
     * Navigate to a destination
     * @param destination: NavigationDestination to navigate
     */
    fun navigateToDestination(destination: NavigationDestination)


    fun navigateWithSafety(destination: NavigationDestination)
}