package com.example.core.domain.home.petbuddies.model

import kotlinx.serialization.Serializable

@Serializable
data class AnimalPostModel(
    val id: String = "",
    val name: String = "",
    val imageUrl: String = "",
    val animal: String = "",
    val tags: List<Tag> = emptyList(),
    val description: String = "",
)

@Serializable
data class Tag(
    val tag: String = "",
    val color: String = "color"
)