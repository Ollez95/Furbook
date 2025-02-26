package com.example.core.domain.authentication.repository

import com.example.core.domain.shared.model.User
import com.example.core.utils.Response

interface UserRepository {
    suspend fun getUserById(id: String): Response<User>
    suspend fun createUser(user: User): Response<Boolean>
    suspend fun updateUser(user: User): Response<Boolean>
}