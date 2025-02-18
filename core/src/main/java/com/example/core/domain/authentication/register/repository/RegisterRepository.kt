package com.example.core.domain.authentication.register.repository

import com.example.core.utils.Response
import kotlinx.coroutines.flow.Flow

interface RegisterRepository {
   fun register(username: String, email: String, password: String, passwordConfirmation: String): Flow<Response<Boolean>>
}