package com.example.core.domain.authentication.register.usecase

import com.example.core.domain.authentication.repository.AuthenticationRepository
import com.example.core.utils.Response
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegisterAccountUseCase @Inject constructor(
    private val repository: AuthenticationRepository,
) {
    operator fun invoke(username: String, email: String, password: String, passwordConfirmation: String): Flow<Response<Boolean>> {
        return repository.register(
            username = username,
            email = email,
            password = password,
            passwordConfirmation = passwordConfirmation
        )
    }
}