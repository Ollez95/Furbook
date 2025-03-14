package com.example.core.domain.home.add_pet.model

import java.time.LocalDate

data class PetModel(
    val id: String = "",
    val name: String = "",
    val type: PetType = PetType.DOG,
    val breed: String = "",
    val birthdate: LocalDate? = null,
    val gender: Gender = Gender.UNKNOWN,
    val weight: Float = 0f,
    val color: String = "",
    val microchipNumber: String = "",
    val imageUri: String = ""
)

enum class Gender {
    MALE, FEMALE, UNKNOWN
}

enum class PetType {
    DOG, CAT, BIRD, OTHER
}
