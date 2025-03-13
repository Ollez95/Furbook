package com.example.home.ui.add_pet

import android.net.Uri
import java.time.LocalDate

interface AddPetEvent {
    object OnNavigateBack: AddPetEvent
    data class OnNameChanged(val name: String) : AddPetEvent
    data class OnTypeChanged(val type: PetType) : AddPetEvent
    data class OnBreedChanged(val breed: String) : AddPetEvent
    data class OnBirthdateChanged(val birthdate: LocalDate) : AddPetEvent
    data class OnGenderChanged(val gender: Gender) : AddPetEvent
    data class OnWeightChanged(val weight: Float) : AddPetEvent
    data class OnColorChanged(val color: String) : AddPetEvent
    data class OnMicroChipChanged(val chipNumber: String) : AddPetEvent
    data class OnImageUriChanged(val imageUri: Uri) : AddPetEvent
    data class OnSubmit(val petModel: AddPetState): AddPetEvent
}