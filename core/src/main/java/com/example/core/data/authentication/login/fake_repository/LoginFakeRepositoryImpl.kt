package com.example.core.data.authentication.login.fake_repository

import com.example.core.domain.authentication.login.repository.LoginRepository
import com.example.core.utils.Constants.INVALID_CREDENTIALS
import com.example.core.utils.Response
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class LoginFakeRepositoryImpl: LoginRepository {
    override fun login(email: String, password: String): Flow<Response<Boolean>> = flow {
        emit(Response.Loading) // ✅ Start with Loading state
        delay(2000) // Simulating network delay

        if (email == "user@example.com" && password == "password123") {
            emit(Response.Success(true))
        } else {
            emit(Response.Error(INVALID_CREDENTIALS))
        }
    }
}