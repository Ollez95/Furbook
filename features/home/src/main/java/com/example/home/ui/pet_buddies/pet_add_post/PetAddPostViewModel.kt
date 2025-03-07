package com.example.home.ui.pet_buddies.pet_add_post

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.authentication.repository.usecase.GetCurrentUserUseCase
import com.example.core.domain.home.petbuddies.model.Tag
import com.example.core.domain.home.petbuddies.usecases.CreateAnimalPostUseCase
import com.example.core.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PetAddPostViewModel @Inject constructor(
    private val createAnimalPostUseCase: CreateAnimalPostUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(PetBuddiesPostState())
    val state: StateFlow<PetBuddiesPostState> = _state

    private val _eventFlow = MutableSharedFlow<PetAddPostEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: PetAddPostEvent) {
        when (event) {
            is PetAddPostEvent.NavigateBack -> navigateBack()
            is PetAddPostEvent.OnExpandClicked -> onExpandedClicked(event.isExpanded)
            is PetAddPostEvent.OnAnimalSelected -> onAnimalSelected(event.animal)
            is PetAddPostEvent.OnDescriptionChanged -> onDescriptionChanged(event.description)
            is PetAddPostEvent.OnImageSelected -> onImageSelected(event.imageUri)
            is PetAddPostEvent.OnNewTagChanged -> onNewTagChanged(event.newTag)
            is PetAddPostEvent.OnTagChanged -> onTagChanged(event.newTag)
            is PetAddPostEvent.OnTagSelected -> onTagSelected(event.tag)
            is PetAddPostEvent.OnAddPostClicked -> onAddPostClicked(event.uri)
            is PetAddPostEvent.OnAddPostError -> onAddPostError(event.message)
            is PetAddPostEvent.OnPostErrorReset -> resetErrorMessage()
        }
    }

    private fun navigateBack() {
        viewModelScope.launch {
            _eventFlow.emit(PetAddPostEvent.NavigateBack)
        }
    }

    private fun onExpandedClicked(isExpanded: Boolean) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isExpanded = isExpanded)
        }
    }

    private fun onAnimalSelected(animal: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(selectedAnimal = animal)
        }
    }

    private fun onDescriptionChanged(description: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(description = description)
        }
    }

    private fun onImageSelected(uri: Uri) {
        viewModelScope.launch {
            _state.value = _state.value.copy(imageUri = uri)
        }
    }

    private fun onNewTagChanged(newTag: Tag) {
        viewModelScope.launch {
            _state.value = _state.value.copy(newTag = newTag)
        }
    }

    private fun onTagChanged(newTag: Tag) {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                tags = _state.value.tags + newTag.copy(tag = newTag.tag.trim())
            )
        }
    }

    private fun onTagSelected(tag: Tag) {
        viewModelScope.launch {
            _state.value = if (state.value.selectedTags.contains(tag)) {
                state.value.copy(selectedTags = state.value.selectedTags - tag)
            } else {
                state.value.copy(selectedTags = state.value.selectedTags + tag)
            }
        }
    }

    private fun onAddPostClicked(uri: Uri) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                val user = (getCurrentUserUseCase() as? Response.Success)?.data ?: return@launch
                val animalPost = _state.value.toAnimalPostModel(user.username)

                when (createAnimalPostUseCase(uri, animalPost)) {
                    is Response.Success -> _eventFlow.emit(PetAddPostEvent.OnAddPostSuccess)
                    else -> _eventFlow.emit(PetAddPostEvent.OnAddPostError("Failed to add post"))
                }
            } catch (e: Exception) {
                _eventFlow.emit(PetAddPostEvent.OnAddPostError(e.message ?: "An Unknown Error occurred"))
            } finally {
                _state.update { it.copy(isLoading = false) }
            }
        }
    }

    private fun onAddPostError(errorMessage: String){
        viewModelScope.launch {
            _state.value = _state.value.copy(errorMessage = errorMessage)
        }
    }

    private fun resetErrorMessage(){
        viewModelScope.launch {
            _state.value = _state.value.copy(errorMessage = null)
        }
    }
}