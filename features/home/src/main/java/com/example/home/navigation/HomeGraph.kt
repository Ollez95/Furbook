package com.example.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.home.ui.main.MainScreen
import com.example.navigation.HomeNavigation

fun NavGraphBuilder.homeGraph() {
    composable<HomeNavigation.Main> {
        MainScreen()
    }
}