package com.example.home.ui.pet_buddies.pet_add_post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PetAddPostViewModel @Inject constructor(): ViewModel() {

    private val _eventFlow = MutableSharedFlow<PetAddPostEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: PetAddPostEvent) {
        when (event) {
            is PetAddPostEvent.NavigateBack -> {
                viewModelScope.launch {
                    _eventFlow.emit(PetAddPostEvent.NavigateBack)
                }
            }
        }
    }
}