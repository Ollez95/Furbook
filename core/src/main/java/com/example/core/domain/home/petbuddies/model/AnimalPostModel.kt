package com.example.core.domain.home.petbuddies.model

import com.example.core.data.home.petbuddies.remote.model.AnimalPostModelDto
import com.example.core.data.home.petbuddies.remote.model.TagDto

data class AnimalPostModel(
    val id: String = "",
    val dateCreated: String = "",
    val userId: String = "",
    val username: String = "",
    val imageUrl: String = "",
    val animal: String = "",
    val tags: List<Tag> = emptyList(),
    val description: String = "",
)

data class Tag(
    val tag: String = "",
    val color: String = "color"
)

fun AnimalPostModel.toAnimalPostModelDto() = AnimalPostModelDto(
    username = username,
    imageUrl = imageUrl,
    animal = animal,
    tags = tags.map { it.toTagDto() }, // Convert Tag -> TagDto
    description = description
)

fun Tag.toTagDto() = TagDto(
    tag = tag,
    color = color
)