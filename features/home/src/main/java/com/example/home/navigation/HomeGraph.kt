package com.example.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.home.ui.pet_buddies.pet_posts.PetBuddiesScreen
import com.example.home.ui.home.HomeScreen
import com.example.home.ui.main.MainEvent
import com.example.home.ui.main.MainScreenEnum
import com.example.navigation.HomeNavigation

fun NavGraphBuilder.homeGraph(onEvent: (MainEvent) -> Unit) {
    composable<HomeNavigation.Main> {
        onEvent(MainEvent.NavigateToBottomSheetScreens(MainScreenEnum.HOME))
        HomeScreen()
    }
    composable<HomeNavigation.PetBuddies> {
        onEvent(MainEvent.NavigateToBottomSheetScreens(MainScreenEnum.PET_BUDDIES))
        PetBuddiesScreen()
    }
    composable<HomeNavigation.Inbox> {
        onEvent(MainEvent.NavigateToBottomSheetScreens(MainScreenEnum.INBOX))
        HomeScreen()
    }
}




