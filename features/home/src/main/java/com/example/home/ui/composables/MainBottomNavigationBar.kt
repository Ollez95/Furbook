package com.example.home.ui.composables

import android.media.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.navigation.HomeNavigation
import com.example.navigation.NavigationDestination
import com.example.navigation.navigateToDestinationCleaningStack
import androidx.navigation.NavDestination.Companion.hasRoute

@Composable
fun MainBottomNavigationBar(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination

    NavigationBar(modifier = modifier) {
        BottomNavItem.toList.forEach { item ->
            val isSelected = currentRoute?.hierarchy?.any { it.hasRoute(item.route::class) } == true
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = if(isSelected) { item.iconSelected } else { item.iconNotSelected },
                        contentDescription = item.title
                    )
                },
                alwaysShowLabel = true,
                label = { Text(item.title) },
                selected = isSelected,
                onClick = {
                    if(!isSelected){
                        navController.navigateToDestinationCleaningStack(
                            navigationToClean = HomeNavigation.Main,
                            navigateToDestination = item.route,
                            cleanCurrentNavigation = false
                        )
                    }
                }
            )
        }
    }
}

sealed class BottomNavItem(
    val route: NavigationDestination,
    val title: String,
    val iconSelected: ImageVector,
    val iconNotSelected: ImageVector
) {
    data object Home: BottomNavItem(
        route = HomeNavigation.Main,
        title = "Home", iconSelected = Icons.Filled.Home,
        iconNotSelected = Icons.Outlined.Home)
    data object Chat: BottomNavItem(
        route = HomeNavigation.Chat,
        title = "Chat",
        iconSelected = Icons.Filled.Call,
        iconNotSelected = Icons.Outlined.Call)
    data object Inbox: BottomNavItem(
        route = HomeNavigation.Inbox,
        title = "Inbox",
        iconSelected = Icons.Filled.Email,
        iconNotSelected = Icons.Outlined.Email)

    companion object{
        val toList = listOf(Home, Chat, Inbox)
    }
}
