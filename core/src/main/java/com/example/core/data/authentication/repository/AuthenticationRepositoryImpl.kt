package com.example.core.data.authentication.repository

import com.example.core.database.supabase.authentication.error.AuthenticationExceptionHandler.handleAuthenticationException
import com.example.core.domain.authentication.repository.AuthenticationRepository
import com.example.core.utils.Response
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.providers.builtin.Email
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val auth: Auth,
) : AuthenticationRepository {
    override fun login(email: String, password: String): Flow<Response<Boolean>> = flow {
        emit(Response.Loading)
        try {
            auth.signInWith(Email) {
                this.email = email
                this.password = password
            }
            emit(Response.Success(true))
        } catch (e: Exception) {
            emit(Response.Error(handleAuthenticationException(e).message))
        }
    }

    override fun register(username: String, email: String, password: String, passwordConfirmation: String): Flow<Response<Boolean>> = flow {
        emit(Response.Loading)
        try {
            auth.signUpWith(Email) {
                this.email = email
                this.password = password
            }
            emit(Response.Success(true))
        } catch (e: Exception) {
            emit(Response.Error(handleAuthenticationException(e).message))
        }
    }

    override fun recoverPassword(email: String): Flow<Response<Boolean>> = flow {
        emit(Response.Loading)
        try {
            auth.resetPasswordForEmail(email)
            emit(Response.Success(true))
        } catch (e: Exception) {
            emit(Response.Error(handleAuthenticationException(e).message))
        }
    }

}