package com.example.home.ui.pet_buddies

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.home.ui.main.MainState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PetBuddiesViewModel @Inject constructor(

): ViewModel() {

    private val _state = MutableStateFlow(PetBuddiesState())
    val state: StateFlow<PetBuddiesState> = _state
        .onStart { loadData() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = PetBuddiesState()
        )

    private fun loadData() {
        viewModelScope.launch {
            _state.update { it.copy(isRefreshing = true) }
        }
    }

    fun onEvent(event: PetBuddiesEvent) {
        when (event) {
            is PetBuddiesEvent.OnRefresh -> refreshData()
            is PetBuddiesEvent.OnAnimalFilterClicked -> {
                viewModelScope.launch {
                    _state.update { it.copy(animalFilters = event.animalType) }
                }
            }
        }
    }

    private fun refreshData(){

    }
}