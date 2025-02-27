package com.example.home.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.home.ui.chat.ChatScreen
import com.example.home.ui.chat.InboxScreen
import com.example.navigation.HomeNavigation

fun NavGraphBuilder.homeGraph() {
    composable<HomeNavigation.Main> {
        InboxScreen(PaddingValues(16.dp))
    }
    composable<HomeNavigation.Inbox> {
        InboxScreen(PaddingValues(16.dp))
    }
    composable<HomeNavigation.Chat> {
        ChatScreen(PaddingValues(16.dp))
    }
}

