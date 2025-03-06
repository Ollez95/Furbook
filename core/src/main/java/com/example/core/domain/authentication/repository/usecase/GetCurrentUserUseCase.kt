package com.example.core.domain.authentication.repository.usecase

import com.example.core.domain.authentication.repository.AuthenticationRepository
import com.example.core.domain.shared.model.User
import com.example.core.utils.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetCurrentUserUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) {
    suspend operator fun invoke(): Response<User> {
        return authenticationRepository.getCurrentUser()
    }
}