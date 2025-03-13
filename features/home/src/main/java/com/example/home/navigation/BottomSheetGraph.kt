package com.example.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.home.ui.add_pet.AddPetScreen
import com.example.home.ui.home.HomeScreen
import com.example.home.ui.main.MainEvent
import com.example.home.ui.main.MainScreenEnum
import com.example.home.ui.pet_buddies.pet_posts.PetBuddiesScreen
import com.example.navigation.BottomSheetNavigation
import com.example.navigation.HomeNavigation

fun NavGraphBuilder.bottomSheetGraph(
    appNavController: NavController,
    onEvent: (MainEvent) -> Unit)
{
    composable<BottomSheetNavigation.Main> {
        onEvent(MainEvent.NavigateToBottomSheetScreens(MainScreenEnum.HOME))
        HomeScreen(appNavController)
    }
    composable<BottomSheetNavigation.Inbox> {
        onEvent(MainEvent.NavigateToBottomSheetScreens(MainScreenEnum.INBOX))
        AddPetScreen()
    }
    composable<BottomSheetNavigation.PetBuddies> {
        onEvent(MainEvent.NavigateToBottomSheetScreens(MainScreenEnum.PET_BUDDIES))
        PetBuddiesScreen()
    }
}




