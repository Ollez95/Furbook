package com.example.home.ui.pet_buddies.pet_posts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.home.petbuddies.usecases.GetAnimalPostsUseCase
import com.example.core.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PetBuddiesViewModel @Inject constructor(
    private val getAnimalPostsUseCase: GetAnimalPostsUseCase
) : ViewModel() {


    private val _eventFlow = MutableSharedFlow<PetBuddiesEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _state = MutableStateFlow(PetBuddiesState())
    val state: StateFlow<PetBuddiesState> = _state
        .onStart { fetchData(isRefreshing = false) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = PetBuddiesState()
        )
    fun onEvent(event: PetBuddiesEvent) {
        when (event) {
            is PetBuddiesEvent.OnRefresh -> fetchData(isRefreshing = true)
            is PetBuddiesEvent.OnAnimalFilterClicked -> {
                viewModelScope.launch {
                    _state.update { it.copy(animalFilters = event.animalType) }
                }
            }
        }
    }

    private fun fetchData(isRefreshing: Boolean) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = !isRefreshing, isRefreshing = isRefreshing) } // Update states based on context
            try {
                when (val response = getAnimalPostsUseCase.invoke()) {
                    is Response.Success -> {
                        _state.update { it.copy(animalPosts = response.data) }
                    }
                    else -> {
                        _eventFlow.emit(PetBuddiesEvent.OnAnimalPostsError("Failed to load posts"))
                    }
                }
            } catch (e: Exception) {
                _eventFlow.emit(PetBuddiesEvent.OnAnimalPostsError(e.message ?: "An Unknown Error occurred"))
            } finally {
                _state.update { it.copy(isLoading = false, isRefreshing = false) } // Ensure both states are reset
            }
        }
    }
}