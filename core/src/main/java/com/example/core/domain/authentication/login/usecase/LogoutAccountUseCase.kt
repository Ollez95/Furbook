package com.example.core.domain.authentication.login.usecase

import com.example.core.domain.authentication.repository.AuthenticationRepository
import com.example.core.utils.Response
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LogoutAccountUseCase @Inject constructor(
    private val repository: AuthenticationRepository,
) {
    operator fun invoke(): Flow<Response<Boolean>> {
        return repository.logout()
    }
}