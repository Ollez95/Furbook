package com.example.core.data.authentication.register.fake_repository

import com.example.core.domain.authentication.register.repository.RegisterRepository
import com.example.core.utils.Constants.INVALID_CREDENTIALS
import com.example.core.utils.Response
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class RegisterFakeRepositoryImpl: RegisterRepository {
    override fun register(username: String, email: String, password: String, passwordConfirmation: String): Flow<Response<Boolean>> = flow {
        emit(Response.Loading) // ✅ Start with Loading state
        delay(2000) // Simulating network delay

        if (username == "1" && email == "1" && password == "1" && passwordConfirmation == "1") {
            emit(Response.Success(true))
        } else {
            emit(Response.Error(INVALID_CREDENTIALS))
        }
    }
}