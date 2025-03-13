package com.example.home.ui.composables

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Pets
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.navigation.BottomSheetNavigation
import com.example.navigation.HomeNavigation
import com.example.navigation.NavigationDestination
import com.example.navigation.navigateToDestinationCleaningStack

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
                        imageVector = if (isSelected) {
                            item.iconSelected
                        } else {
                            item.iconNotSelected
                        },
                        contentDescription = item.title
                    )
                },
                alwaysShowLabel = true,
                label = { Text(item.title) },
                selected = isSelected,
                onClick = {
                    if (!isSelected) {
                        navController.navigateToDestinationCleaningStack(
                            navigationToClean = BottomSheetNavigation.Main,
                            navigateToDestination = item.route,
                            cleanCurrentNavigation = false,
                            useTheSameInstance = true,
                            restorePreviousState = true,
                            saveCurrentState = true
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
    val iconNotSelected: ImageVector,
) {
    data object Home : BottomNavItem(
        route = BottomSheetNavigation.Main,
        title = "Home", iconSelected = Icons.Filled.Home,
        iconNotSelected = Icons.Outlined.Home
    )

    data object Chat : BottomNavItem(
        route = BottomSheetNavigation.PetBuddies,
        title = "Pet Buddies",
        iconSelected = Icons.Filled.Pets,
        iconNotSelected = Icons.Outlined.Pets
    )

    data object Inbox : BottomNavItem(
        route = BottomSheetNavigation.Inbox,
        title = "Inbox",
        iconSelected = Icons.Filled.Email,
        iconNotSelected = Icons.Outlined.Email
    )

    companion object {
        val toList = listOf(Home, Chat, Inbox)
    }
}
