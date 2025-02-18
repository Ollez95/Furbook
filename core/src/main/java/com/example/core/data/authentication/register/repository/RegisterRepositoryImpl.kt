package com.example.core.data.authentication.register.repository

import com.example.core.domain.authentication.register.repository.RegisterRepository
import com.example.core.utils.Response
import kotlinx.coroutines.flow.Flow

internal class RegisterRepositoryImpl: RegisterRepository {
    override fun register(username: String, email: String, password: String, passwordConfirmation: String): Flow<Response<Boolean>> {
        TODO("Not yet implemented")
    }
}