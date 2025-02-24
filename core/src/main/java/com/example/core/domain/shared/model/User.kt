package com.example.core.domain.shared.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String,
    val mail: String,
    val username: String,

)

