package com.example.core.domain.authentication.login.usecase

import com.example.core.domain.authentication.repository.AuthenticationRepository
import com.example.core.utils.Response
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginAccountUseCase @Inject constructor(
    private val repository: AuthenticationRepository,
) {
    operator fun invoke(email: String, password: String): Flow<Response<String>> {
        return repository.login(
            email = email,
            password = password,
        )
    }
}