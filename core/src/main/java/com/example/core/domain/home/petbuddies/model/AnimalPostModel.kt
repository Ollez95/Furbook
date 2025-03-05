package com.example.core.domain.home.petbuddies.model

import android.net.Uri
import androidx.compose.ui.graphics.Color

data class AnimalPostModel(
    val id: String = "",
    val name: String = "",
    val imageUrl: Uri = Uri.EMPTY,
    val animal: String = "",
    val tags: List<Tag> = emptyList(),
    val description: String = "",
)

data class Tag(
    val tag: String = "",
    val color: Color = Color.Black
)