package com.example.core.domain.home.petbuddies.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.ui.R

enum class AnimalEnum(
    @StringRes val animalName: Int,
    @DrawableRes val imageUrl: Int)
{
    DOG(R.string.pet_buddies_dog_title, R.drawable.dog_filter),
    CAT(R.string.pet_buddies_cat_title, R.drawable.cat_filter),
    BIRD(R.string.pet_buddies_bird_title, R.drawable.bird_filter),
    RABBIT(R.string.pet_buddies_rabbit_title, R.drawable.rabbit_filter),
    HAMSTER(R.string.pet_buddies_hamster_title, R.drawable.hamster_filter),
    FISH(R.string.pet_buddies_fish_title, R.drawable.fish_filter)
}

val listAnimals = listOf(
    AnimalEnum.DOG,
    AnimalEnum.CAT,
    AnimalEnum.BIRD,
    AnimalEnum.RABBIT,
    AnimalEnum.HAMSTER,
    AnimalEnum.FISH)