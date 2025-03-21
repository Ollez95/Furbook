package com.example.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.home.ui.add_pet.AddPetScreen
import com.example.home.ui.main.MainScreen
import com.example.home.ui.pet_buddies.pet_add_post.PetAddPostScreen
import com.example.navigation.BottomSheetNavigation
import com.example.navigation.HomeNavigation

fun NavGraphBuilder.homeGraph(navController: NavController) {
    composable<BottomSheetNavigation.Main> {
        MainScreen(appNavController = navController)
    }
    composable<HomeNavigation.AddPet> {
        AddPetScreen(navController)
    }
    composable<HomeNavigation.PetAddPost> {
        PetAddPostScreen(navController)
    }
}