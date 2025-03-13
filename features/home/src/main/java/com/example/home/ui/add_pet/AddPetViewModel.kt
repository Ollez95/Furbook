package com.example.home.ui.add_pet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AddPetViewModel @Inject constructor(): ViewModel() {

    private val _state = MutableStateFlow(AddPetState())
    val state: StateFlow<AddPetState> = _state.stateIn(
        scope = viewModelScope, started = SharingStarted.WhileSubscribed(5_000), initialValue = AddPetState()
    )
}