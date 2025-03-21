package com.example.core.data.home.petbuddies.remote.model

import com.example.core.domain.home.petbuddies.model.AnimalPostModel
import com.example.core.domain.home.petbuddies.model.Tag
import com.example.core.utils.time.TimeUtils.formatTimestamp
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class AnimalPostModelDto(
    @SerialName("id") val id: String? = null,
    @SerialName("created_at") val createdAt: String? = null,
    @SerialName("user_id") val userId: String? = null,
    @SerialName("username") val username: String = "",
    @SerialName("image_url") val imageUrl: String = "",
    @SerialName("animal") val animal: String = "",
    @SerialName("tags") val tags: List<TagDto>? = null,
    @SerialName("description") val description: String = ""
)

@Serializable
data class TagDto(
    @SerialName("tag") val tag: String = "",
    @SerialName("color") val color: String = "color"
)

fun AnimalPostModelDto.toAnimalPostModel() = AnimalPostModel(
    id = id ?: "",
    dateCreated = formatTimestamp(createdAt ?: ""),
    userId = userId ?: "",
    username = username,
    imageUrl = imageUrl,
    animal = animal,
    tags = buildList {
        add(Tag(tag = animal, color = "black"))
        addAll(tags?.map { it.toTag() } ?: emptyList())
    },
    description = description
)

fun TagDto.toTag() = Tag(
    tag = tag,
    color = color
)