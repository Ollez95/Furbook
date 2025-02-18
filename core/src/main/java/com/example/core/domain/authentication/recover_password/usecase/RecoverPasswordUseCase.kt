package com.example.core.domain.authentication.recover_password.usecase

import com.example.core.domain.authentication.repository.AuthenticationRepository
import com.example.core.utils.Response
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RecoverPasswordUseCase @Inject constructor(
    private val repository: AuthenticationRepository
) {
    operator fun invoke(email: String): Flow<Response<Boolean>> {
        return repository.recoverPassword(email)
    }
}
