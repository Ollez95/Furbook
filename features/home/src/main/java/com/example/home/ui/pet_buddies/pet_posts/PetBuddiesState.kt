package com.example.home.ui.pet_buddies.pet_posts

import com.example.core.domain.home.petbuddies.model.AnimalEnum
import com.example.core.domain.home.petbuddies.model.AnimalPostModel

data class PetBuddiesState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val animalPosts: List<AnimalPostModel> = emptyList(),
    val animalFilters: Set<AnimalEnum> = emptySet()
)
