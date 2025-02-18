package com.example.core.domain.authentication.login.repository

import com.example.core.utils.Response
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    fun login(email: String, password: String) : Flow<Response<Boolean>>
}