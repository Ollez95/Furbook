package com.example.home.ui.add_pet

import android.net.Uri
import java.time.LocalDate

data class AddPetState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null,
    val name: String = "",
    val nameError: String? = null,
    val type: PetType = PetType.DOG,
    val breed: String = "",
    val birthdate: LocalDate? = null,
    val gender: Gender = Gender.UNKNOWN,
    val weight: String = "",
    val color: String = "",
    val microchipNumber: String = "",
    val imageUri: Uri? = null
)

enum class Gender {
    MALE, FEMALE, UNKNOWN
}

enum class PetType {
    DOG, CAT, BIRD, OTHER
}