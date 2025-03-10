package com.example.home.ui.pet_buddies.pet_add_post

import android.net.Uri
import com.example.core.domain.home.petbuddies.model.AnimalPostModel
import com.example.core.domain.home.petbuddies.model.Tag

data class PetBuddiesPostState(
    val isLoading: Boolean = false,
    val selectedAnimal: String? = null,
    val isExpanded: Boolean = false,
    val description: String = "",
    val imageUri: Uri? = Uri.EMPTY,
    val tags: List<Tag> = listOf(Tag("Cute"), Tag("Adopt"), Tag("Playful"), Tag("Rescue")),
    val selectedTags: Set<Tag> = emptySet(),
    val newTag: Tag = Tag(),
    val errorMessage: String? = null
)

fun PetBuddiesPostState.toAnimalPostModel(userName: String) =
    AnimalPostModel(
        name = userName,
        animal = selectedAnimal ?: "",
        tags = selectedTags.toList(),
        description = description
    )
