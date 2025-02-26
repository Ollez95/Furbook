package com.example.core.database.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.core.domain.shared.model.User

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: String,
    val email: String,
    val username: String
)

fun UserEntity.toDomainModel(): User {
    return User(id = this.id, username = this.username, email = this.email)
}