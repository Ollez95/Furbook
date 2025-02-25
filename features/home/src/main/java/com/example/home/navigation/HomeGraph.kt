package com.example.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.home.ui.main.MainScreen
import com.example.navigation.HomeNavigation
import com.example.navigation.Navigator

fun NavGraphBuilder.homeGraph(navigator: Navigator) {
    composable<HomeNavigation.Main> {
        MainScreen(navigator = navigator)
    }
}