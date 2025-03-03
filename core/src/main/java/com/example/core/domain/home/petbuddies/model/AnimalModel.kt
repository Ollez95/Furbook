package com.example.core.domain.home.petbuddies.model

data class AnimalModel(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val age: Int = 0,
    val breed: String = "",
)



