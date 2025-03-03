package com.example.core.domain.home.petbuddies.model

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.example.ui.R

data class AnimalPostModel(
    val id: String = "",
    val name: String = "",
    @DrawableRes val imageUrl: Int = R.drawable.on_boarding_image_1,
    val tags: Tag = Tag(),
    val description: String = ""
)

data class Tag(
    val tag: String = "",
    val color: Color = Color.Black
)