package com.example.authentication.domain.login.repository

import com.example.core.utils.Response
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    fun login(email: String, password: String) : Flow<Response<String>>


    companion object {
        const val INVALID_CREDENTIALS = "Invalid credentials"
        const val LOGIN_SUCCESSFUL = "Login Successful"
    }
}