package com.example.home.ui.add_pet

import android.net.Uri
import com.example.core.domain.home.add_pet.model.Gender
import com.example.core.domain.home.add_pet.model.PetType
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
    val weight: Float = 0f,
    val color: String = "",
    val microchipNumber: String = "",
    val imageUri: Uri? = null
)

