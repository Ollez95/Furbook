package com.example.home.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.home.ui.home.HomeScreen
import com.example.home.ui.main.MainEvent
import com.example.home.ui.main.MainScreenEnum
import com.example.home.ui.pet_buddies.pet_posts.PetBuddiesScreen
import com.example.navigation.BottomSheetNavigation

fun NavGraphBuilder.bottomSheetGraph(
    appNavController: NavController,
    onEvent: (MainEvent) -> Unit)
{
    composable<BottomSheetNavigation.Main> {
        onEvent(MainEvent.NavigateToBottomSheetScreens(MainScreenEnum.HOME))
        HomeScreen(appNavController)
    }
    composable<BottomSheetNavigation.PetBuddies> {
        onEvent(MainEvent.NavigateToBottomSheetScreens(MainScreenEnum.PET_BUDDIES))
        PetBuddiesScreen()
    }
    composable<BottomSheetNavigation.Inbox> {
        onEvent(MainEvent.NavigateToBottomSheetScreens(MainScreenEnum.INBOX))
        Column {
            Text("To be made-...")
        }
    }
}




