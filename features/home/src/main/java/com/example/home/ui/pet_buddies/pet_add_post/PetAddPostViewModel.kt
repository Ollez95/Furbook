package com.example.home.ui.pet_buddies.pet_add_post

import androidx.lifecycle.ViewModel
import com.example.home.ui.main.MainEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class PetAddPostViewModel @Inject constructor(): ViewModel() {

    private val _eventFlow = MutableSharedFlow<MainEvent>()
    val eventFlow = _eventFlow.asSharedFlow()
}