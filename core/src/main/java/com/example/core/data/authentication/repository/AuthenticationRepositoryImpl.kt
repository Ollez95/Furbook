package com.example.core.data.authentication.repository

import com.example.core.domain.authentication.repository.AuthenticationRepository
import com.example.core.utils.Response
import kotlinx.coroutines.flow.Flow

class AuthenticationRepositoryImpl : AuthenticationRepository {
    override fun login(email: String, password: String): Flow<Response<Boolean>> {
        TODO("Not yet implemented")
    }

    override fun register(username: String, email: String, password: String, passwordConfirmation: String): Flow<Response<Boolean>> {
        TODO("Not yet implemented")
    }

    override fun recoverPassword(email: String): Flow<Response<Boolean>> {
        TODO("Not yet implemented")
    }

}