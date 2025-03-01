package com.example.home.ui.composables

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import com.example.navigation.HomeNavigation
import com.example.navigation.NavigationDestination
import com.example.navigation.navigateToDestinationCleaningStack

@Composable
fun MainBottomNavigationBar(
    modifier: Modifier = Modifier,
    navController: NavController) {
    NavigationBar(
        modifier = modifier
    ) {
        val currentRoute = navController.currentDestination
        BottomNavItem.entries.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.title) },
                label = { Text(item.title) },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigateToDestinationCleaningStack(HomeNavigation.Main, item.route)
                }
            )
        }
    }
}

enum class BottomNavItem(val route: NavigationDestination, val title: String, val icon: ImageVector) {
    Home(HomeNavigation.Main, "Home", Icons.Default.Home),
    Chat(HomeNavigation.Chat, "Chat", Icons.Default.Call),
    Inbox(HomeNavigation.Inbox, "Inbox", Icons.Default.Email)
}