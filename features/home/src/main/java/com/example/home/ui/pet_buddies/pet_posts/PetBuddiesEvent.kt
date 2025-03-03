package com.example.home.ui.pet_buddies.pet_posts

import com.example.core.domain.home.petbuddies.model.AnimalEnum

interface PetBuddiesEvent {
    data object OnRefresh : PetBuddiesEvent
    data class OnAnimalFilterClicked(val animalType: Set<AnimalEnum>) : PetBuddiesEvent
}