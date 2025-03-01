package com.example.core.database.supabase.authentication.error

import com.example.core.database.supabase.model.AuthenticationErrorResponse
import com.example.core.database.supabase.model.AuthenticationErrorResponse.Companion.fromCode
import com.example.core.utils.exceptions.NotValidEmail
import com.example.core.utils.exceptions.NotValidPasswordOrEmail
import com.example.core.utils.exceptions.NotValidUsernameEmailOrPassword
import io.github.jan.supabase.auth.exception.AuthRestException
import io.github.jan.supabase.exceptions.HttpRequestException
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.plugins.HttpRequestTimeoutException

object AuthenticationExceptionHandler {
    fun handleAuthenticationException(exception: Exception): AuthenticationErrorResponse {
        return when (exception) {
            is AuthRestException -> fromCode(exception.errorCode?.value ?: "", exception.description ?: "")
            is HttpRequestTimeoutException,
            is ConnectTimeoutException-> AuthenticationErrorResponse.Timeout
            is NotValidPasswordOrEmail -> AuthenticationErrorResponse.InvalidEmailOrPassword
            is NotValidEmail -> AuthenticationErrorResponse.InvalidEmail
            is NotValidUsernameEmailOrPassword -> AuthenticationErrorResponse.InvalidUsernameEmailOrPassword
            is HttpRequestException -> AuthenticationErrorResponse.NoInternet
            else -> AuthenticationErrorResponse.UnknownError
        }
    }
}