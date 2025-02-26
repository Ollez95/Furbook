package com.example.core.domain.shared.model

import com.example.core.database.local.model.UserEntity
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String? = null,
    val mail: String = "",
    val username: String = "",
)

fun User.toEntity(): UserEntity {
    return UserEntity(id = this.id ?: "", username = this.username, mail = this.mail)
}

