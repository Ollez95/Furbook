package com.example.core.domain.authentication.repository

import com.example.core.domain.shared.model.User
import com.example.core.utils.Response
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUserById(id: String): Flow<Response<User>>
    fun createUser(user: User): Flow<Response<Boolean>>
    fun updateUser(user: User): Flow<Response<Boolean>>
}