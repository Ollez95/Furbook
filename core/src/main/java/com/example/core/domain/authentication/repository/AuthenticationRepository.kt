package com.example.core.domain.authentication.repository

import com.example.core.utils.Response
import kotlinx.coroutines.flow.Flow

interface AuthenticationRepository {
    fun login(email: String, password: String): Flow<Response<String>>
    fun register(username: String, email: String, password: String, passwordConfirmation: String): Flow<Response<String>>
    fun recoverPassword(email: String): Flow<Response<Boolean>>
    suspend fun saveUserToken()
    suspend fun isUserLoggedIn(): Response<String>
    fun logout(): Flow<Response<Boolean>>
}