package com.example.home.ui.pet_buddies.pet_add_post

import android.net.Uri
import com.example.core.domain.home.petbuddies.model.Tag

interface PetAddPostEvent {
    object NavigateBack : PetAddPostEvent
    data class OnAnimalSelected(val animal: String) : PetAddPostEvent
    data class OnExpandClicked(val isExpanded: Boolean) : PetAddPostEvent
    data class OnDescriptionChanged(val description: String) : PetAddPostEvent
    data class OnImageSelected(val imageUri: Uri) : PetAddPostEvent
    data class OnTagSelected(val tag: Tag) : PetAddPostEvent
    data class OnNewTagChanged(val newTag: Tag) : PetAddPostEvent
    data class OnTagChanged(val newTag: Tag) : PetAddPostEvent
    data class OnAddPostClicked(val uri: Uri): PetAddPostEvent
    object OnAddPostSuccess: PetAddPostEvent
    data class OnAddPostError(val message: String): PetAddPostEvent
    object OnPostErrorReset: PetAddPostEvent
}