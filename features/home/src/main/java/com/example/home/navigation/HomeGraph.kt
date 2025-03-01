package com.example.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.home.ui.chat.ChatScreen
import com.example.home.ui.chat.HomeScreen
import com.example.home.ui.chat.InboxScreen
import com.example.navigation.HomeNavigation

fun NavGraphBuilder.homeGraph() {
    composable<HomeNavigation.Main> {
        HomeScreen()
    }
    composable<HomeNavigation.Inbox> {
        InboxScreen()
    }
    composable<HomeNavigation.Chat> {
        ChatScreen()
    }
}

