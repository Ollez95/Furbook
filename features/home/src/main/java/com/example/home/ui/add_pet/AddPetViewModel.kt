package com.example.home.ui.add_pet

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class AddPetViewModel @Inject constructor(

) : ViewModel() {

    private val _eventFlow = MutableSharedFlow<AddPetEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _state = MutableStateFlow(AddPetState())
    val state: StateFlow<AddPetState> = _state.stateIn(
        scope = viewModelScope, started = SharingStarted.WhileSubscribed(5_000), initialValue = AddPetState()
    )

    fun onEvent(event: AddPetEvent) {
        when (event) {
            is AddPetEvent.OnNameChanged -> onPetNameChanged(event.name)
            is AddPetEvent.OnTypeChanged -> onPetTypeChanged(event.type)
            is AddPetEvent.OnBreedChanged -> onPetBreedChanged(event.breed)
            is AddPetEvent.OnBirthdateChanged -> onPetBirthdateChanged(event.birthdate)
            is AddPetEvent.OnGenderChanged -> onGenderChanged(event.gender)
            is AddPetEvent.OnWeightChanged -> onWeightChanged(event.weight)
            is AddPetEvent.OnColorChanged -> onColorChanged(event.color)
            is AddPetEvent.OnMicroChipChanged -> onMicrochipNumberChanged(event.chipNumber)
            is AddPetEvent.OnImageUriChanged -> onImageUriChanged(event.imageUri)
            is AddPetEvent.OnNavigateBack -> onNavigateBack()
        }
    }

    private fun onPetNameChanged(petName: String)  { _state.value = _state.value.copy(name = petName) }
    private fun onPetTypeChanged(petType: PetType) { _state.value = _state.value.copy(type = petType) }
    private fun onPetBreedChanged(petBreed: String) { _state.value = _state.value.copy(breed = petBreed) }
    private fun onPetBirthdateChanged(petBirthdate: LocalDate) { _state.value = _state.value.copy(birthdate = petBirthdate) }
    private fun onGenderChanged(gender: Gender) { _state.value = _state.value.copy(gender = gender) }
    private fun onWeightChanged(weight: Float) { _state.value = _state.value.copy(weight = weight) }
    private fun onColorChanged(color: String) { _state.value = _state.value.copy(color = color) }
    private fun onMicrochipNumberChanged(microchipNumber: String) { _state.value = _state.value.copy(microchipNumber = microchipNumber) }
    private fun onImageUriChanged(imageUri: Uri) { _state.value = _state.value.copy(imageUri = imageUri) }
    private fun onNavigateBack() { viewModelScope.launch { _eventFlow.emit(AddPetEvent.OnNavigateBack) } }
}