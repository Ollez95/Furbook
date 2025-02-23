package com.example.core.data.authentication.repository

import com.example.core.database.supabase.authentication.error.AuthenticationExceptionHandler.handleAuthenticationException
import com.example.core.domain.authentication.repository.AuthenticationRepository
import com.example.core.utils.Response
import com.example.datastore.authentication.IsUserLoggedInDatastore
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.providers.builtin.Email
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val isUserLoggedInDataStore: IsUserLoggedInDatastore,
    private val auth: Auth,
) : AuthenticationRepository {
    override fun login(email: String, password: String): Flow<Response<Boolean>> = flow {
        emit(Response.Loading)
        try {
            auth.signInWith(Email) {
                this.email = email
                this.password = password
            }
            saveUserToken()
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
            saveUserToken()
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

    override suspend fun isUserLoggedIn(): Response<Boolean> {
        return try {
            val token = isUserLoggedInDataStore.getValueDataStoreOnce() // ✅ Uses the suspend function
            if (token.isNullOrEmpty()) {
                Response.Error("Token is empty")
            } else {
                try {
                    auth.retrieveUser(token)
                    auth.refreshCurrentSession()
                    saveUserToken()
                    Response.Success(true)
                } catch (e: Exception) {
                    Response.Error(handleAuthenticationException(e).message)
                }
            }
        } catch (e: Exception) {
            Response.Error("Failed to fetch login status: ${e.message}")
        }
    }

    override fun logout(): Flow<Response<Boolean>> = flow {
        emit(Response.Loading)
        try {
            auth.signOut()
            isUserLoggedInDataStore.clearValueDatastore()
            emit(Response.Success(true))
        } catch (e: Exception) {
            emit(Response.Error(handleAuthenticationException(e).message))
        }
    }

    override suspend fun saveUserToken() {
        val token = auth.currentSessionOrNull()?.accessToken
        if (token.isNullOrEmpty()) throw Exception("Token is empty")
        isUserLoggedInDataStore.saveValueDatastore(token)
    }
}