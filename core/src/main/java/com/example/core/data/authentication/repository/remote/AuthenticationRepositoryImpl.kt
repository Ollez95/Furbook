package com.example.core.data.authentication.repository.remote

import com.example.core.database.supabase.authentication.error.AuthenticationExceptionHandler.handleAuthenticationException
import com.example.core.domain.authentication.repository.AuthenticationRepository
import com.example.core.domain.authentication.repository.UserRepository
import com.example.core.domain.shared.model.User
import com.example.core.utils.Response
import com.example.core.utils.authentication.AuthenticationUtils.validateLoginCredentials
import com.example.core.utils.authentication.AuthenticationUtils.validateRecoveryCredentials
import com.example.core.utils.authentication.AuthenticationUtils.validateRegisterCredentials
import com.example.datastore.authentication.IsUserLoggedInDatastore
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.providers.builtin.Email
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val isUserLoggedInDataStore: IsUserLoggedInDatastore,
    private val auth: Auth,
    private val userRepository: UserRepository,
) : AuthenticationRepository {

    override fun login(email: String, password: String): Flow<Response<String>> = flow {
        Timber.Forest.d(START_LOGIN_PROCESS)
        emit(Response.Loading)
        try {
            validateLoginCredentials(email, password)
            auth.signInWith(Email) {
                this.email = email
                this.password = password
            }

            processRegisterUser()

            saveUserToken()
            val currentId = getCurrentUserId()

            Timber.d(USER_WAS_LOGIN)
            emit(Response.Success(currentId))
        } catch (e: Exception) {
            Timber.e(ERROR_DURING_LOGIN + e.message)
            emit(Response.Error(handleAuthenticationException(e).message))
        }
    }

    override fun register(
        username: String,
        email: String,
        password: String,
        passwordConfirmation: String,
    ): Flow<Response<String>> = flow {

        Timber.d(START_REGISTRATION_PROCESS)
        emit(Response.Loading)

        try {
            validateRegisterCredentials(username, email, password, passwordConfirmation)
            auth.signUpWith(Email) {
                this.email = email
                this.password = password
            }

            processRegisterUser()

            saveUserToken()
            val currentId = getCurrentUserId()

            Timber.d(REGISTRATION_WAS_SUCCESSFUL)
            emit(Response.Success(currentId))
        } catch (e: Exception) {
            Timber.e(ERROR_DURING_REGISTRATION + e.message)
            emit(Response.Error(handleAuthenticationException(e).message))
            Timber.i(TODO_ROLLBACK)
        }
    }

    override fun recoverPassword(email: String): Flow<Response<Boolean>> = flow {
        Timber.d(START_PASSWORD_RECOVERY_PROCESS)
        emit(Response.Loading)
        try {
            validateRecoveryCredentials(email)
            auth.resetPasswordForEmail(email)
            Timber.d(PASSWORD_RECOVERY_EMAIL_SENT + email)
            emit(Response.Success(true))
        } catch (e: Exception) {
            Timber.e(e.message ?: "")
            emit(Response.Error(handleAuthenticationException(e).message))
        }
    }

    override suspend fun isUserLoggedIn(): Response<String> {
        return try {
            val token = isUserLoggedInDataStore.getValueDataStoreOnce()
            if (token.isNullOrEmpty()) {
                Timber.e(NO_USER_IS_LOGGED_IN)
                Response.Error(NO_USER_IS_LOGGED_IN)
            } else {
                try {
                    auth.retrieveUser(token)
                    auth.refreshCurrentSession()
                    saveUserToken()

                    val currentId = getCurrentUserId()
                    Timber.d(USER_IS_LOGGED_IN)
                    Response.Success(currentId)
                } catch (e: Exception) {
                    userRepository.removeUser(User(id = getCurrentUserId()))
                    isUserLoggedInDataStore.clearValueDatastore()

                    Timber.e(e.message ?: "")
                    Response.Error(handleAuthenticationException(e).message)
                }
            }
        } catch (e: Exception) {
            Timber.e(e.message)
            Response.Error("Failed to fetch login status: ${e.message}")
        }
    }

    override fun logout(): Flow<Response<Boolean>> = flow {
        emit(Response.Loading)
        try {
            clearLocalDatabase()
            auth.signOut()
            Timber.d(USER_WAS_LOGOUT)
            emit(Response.Success(true))
        } catch (e: Exception) {
            clearLocalDatabase()
            Timber.e(e.message ?: "")
            emit(Response.Error(handleAuthenticationException(e).message))
        }
    }

    override suspend fun refreshCurrentSession(): Response<Boolean> {
        TODO()
    }


    override suspend fun saveUserToken() {
        val token = auth.currentSessionOrNull()?.accessToken
        if (token.isNullOrEmpty()) throw Exception(TOKEN_IS_EMPTY)
        isUserLoggedInDataStore.saveValueDatastore(token)
        Timber.d(TOKEN_WAS_SUCCESSFULLY_SAVED)
    }

    private fun getCurrentUserId() = auth.currentSessionOrNull()?.user?.id ?: throw Exception(USER_ID_NOT_FOUND)

    private fun getUserData(): User {
        val currentId = auth.currentSessionOrNull()?.user?.id ?: ""
        val user = auth.currentSessionOrNull()?.user
        val mail = user?.email ?: ""
        val username = user?.userMetadata?.get("username") ?: "No user metadata"
        Timber.d("User data: $currentId, $mail, $username")
        return User(id = currentId, mail = mail, username = username.toString())
    }

    private suspend fun processRegisterUser() {
        val user = getUserData()
        val registerResponse = userRepository.createUser(user)
        if (registerResponse is Response.Error) {
            throw Exception(registerResponse.message)
        }
        Timber.d(USER_CREATED_SUCCESSFULLY + user.id)
    }

    private suspend fun clearLocalDatabase() {
        val result = userRepository.removeUser(User(id = getCurrentUserId()))
        if (result is Response.Error) {
            Timber.e(result.message)
        }
        isUserLoggedInDataStore.clearValueDatastore()
    }

    companion object {
        private const val START_REGISTRATION_PROCESS = "Starting registration process"
        private const val START_LOGIN_PROCESS = "Starting login process"
        private const val START_PASSWORD_RECOVERY_PROCESS = "Starting password recovery process"
        private const val ERROR_DURING_REGISTRATION = "Error during registration: "
        private const val ERROR_DURING_LOGIN = "Error during login: "
        private const val NO_USER_IS_LOGGED_IN = "No user is logged in"
        private const val TOKEN_IS_EMPTY = "Token is empty"
        private const val TOKEN_WAS_SUCCESSFULLY_SAVED = "Token was successfully saved"
        private const val REGISTRATION_WAS_SUCCESSFUL = "Registration process completed successfully"
        private const val USER_ID_NOT_FOUND = "User ID not found after sign-up"
        private const val USER_CREATED_SUCCESSFULLY = "User created successfully: userId= "
        private const val USER_WAS_LOGOUT = "User was successfully logged out"
        private const val USER_IS_LOGGED_IN = "User is logged in"
        private const val USER_WAS_LOGIN = "User was successfully login"
        private const val PASSWORD_RECOVERY_EMAIL_SENT = "Password recovery email sent to "
        private const val TODO_ROLLBACK = "TODO: We need to handle rollback if user creation fails."
    }
}