package com.example.authentication.data.login.fake_repository

import com.example.authentication.domain.login.repository.LoginRepository
import com.example.authentication.domain.login.repository.LoginRepository.Companion.INVALID_CREDENTIALS
import com.example.authentication.domain.login.repository.LoginRepository.Companion.LOGIN_SUCCESSFUL
import com.example.core.utils.Response
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoginFakeRepositoryImpl: LoginRepository {
    override fun login(email: String, password: String): Flow<Response<String>> = flow {
        emit(Response.Loading) // ✅ Start with Loading state
        delay(2000) // Simulating network delay

        if (email == "user@example.com" && password == "password123") {
            emit(Response.Success(LOGIN_SUCCESSFUL)) // ✅ Success response
        } else {
            emit(Response.Error(INVALID_CREDENTIALS)) // ❌ Error response
        }
    }
}