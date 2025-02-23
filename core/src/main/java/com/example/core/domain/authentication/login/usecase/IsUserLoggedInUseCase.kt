package com.example.core.domain.authentication.login.usecase

import com.example.core.domain.authentication.repository.AuthenticationRepository
import com.example.core.utils.Response
import javax.inject.Inject

class IsUserLoggedInUseCase @Inject constructor(
    private val repository: AuthenticationRepository,
)  {
    suspend operator fun invoke(): Response<Boolean> =
        repository.isUserLoggedIn()
}