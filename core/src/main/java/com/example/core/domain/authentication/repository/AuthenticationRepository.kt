package com.example.core.domain.authentication.repository

import com.example.core.utils.Response
import kotlinx.coroutines.flow.Flow

interface AuthenticationRepository {
    fun login(email: String, password: String): Flow<Response<Boolean>>
    fun register(username: String, email: String, password: String, passwordConfirmation: String): Flow<Response<Boolean>>
    fun recoverPassword(email: String): Flow<Response<Boolean>>
    suspend fun saveUserToken()
    fun isUserLoggedIn(): Flow<Response<Boolean>>
    fun logout(): Flow<Response<Boolean>>
}