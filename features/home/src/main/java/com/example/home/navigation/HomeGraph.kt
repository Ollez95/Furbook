package com.example.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.home.ui.pet_buddies.PetBuddiesScreen
import com.example.home.ui.home.HomeScreen
import com.example.navigation.HomeNavigation

fun NavGraphBuilder.homeGraph() {
    composable<HomeNavigation.Main> {
        HomeScreen()
    }
    composable<HomeNavigation.Inbox> {
        HomeScreen()
    }
    composable<HomeNavigation.PetBuddies> {
        PetBuddiesScreen()
    }
}

