package com.example.core.data.authentication.fake_repository

import com.example.core.domain.authentication.repository.AuthenticationRepository
import com.example.core.utils.Constants.ACCOUNT_DO_NOT_EXIST
import com.example.core.utils.Constants.CREDENTIALS_NOT_MATCH
import com.example.core.utils.Constants.INVALID_CREDENTIALS
import com.example.core.utils.Response
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AuthenticationFakeRepositoryImpl: AuthenticationRepository {
    override fun login(email: String, password: String): Flow<Response<Boolean>> = flow {
        emit(Response.Loading) // ✅ Start with Loading state
        delay(2000) // Simulating network delay

        if (email == "user@example.com" && password == "password123") {
            emit(Response.Success(true))
        } else {
            emit(Response.Error(INVALID_CREDENTIALS))
        }
    }

    override fun register(username: String, email: String, password: String, passwordConfirmation: String): Flow<Response<Boolean>> = flow {
        emit(Response.Loading) // ✅ Start with Loading state
        delay(2000) // Simulating network delay

        if (username == "1" && email == "1" && password == "1" && passwordConfirmation == "1") {
            emit(Response.Success(true))
        } else {
            emit(Response.Error(CREDENTIALS_NOT_MATCH))
        }
    }

    override fun recoverPassword(email: String): Flow<Response<Boolean>> = flow {
        emit(Response.Loading) // ✅ Start with Loading state
        delay(2000) // Simulating network delay

        if (email == "user@example.com") {
            emit(Response.Success(true))
        } else {
            emit(Response.Error(ACCOUNT_DO_NOT_EXIST))
        }
    }
}