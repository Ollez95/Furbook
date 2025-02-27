package com.example.core.domain.authentication.shared

import com.example.core.domain.authentication.repository.AuthenticationRepository
import com.example.core.utils.Response
import javax.inject.Inject

class GetCurrentUserIdUseCase @Inject constructor(
    private val repository: AuthenticationRepository,
) {
    suspend operator fun invoke(): Response<String> = repository.getCurrentUserId()
}