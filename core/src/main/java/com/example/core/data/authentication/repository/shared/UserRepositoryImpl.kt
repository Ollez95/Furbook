package com.example.core.data.authentication.repository.shared

import com.example.core.data.authentication.repository.local.UserRepositoryLocal
import com.example.core.data.authentication.repository.remote.UserRepositoryRemote
import com.example.core.domain.authentication.repository.UserRepository
import com.example.core.domain.shared.model.User
import com.example.core.utils.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val localRepository: UserRepositoryLocal,
    private val remoteRepository: UserRepositoryRemote
): UserRepository {
    override suspend fun getUserById(id: String): Response<User> {
        val localUser = localRepository.getUserById(id)
        if (localUser is Response.Success) {
            return localUser
        }
        val remoteUser = remoteRepository.getUserById(id)
        if (remoteUser is Response.Success) {
            return remoteUser
        }
        return Response.Error(GET_USER_ERROR)
    }

    override suspend fun createUser(user: User): Response<Boolean> {
        val remoteUser = remoteRepository.createUser(user)
        if (remoteUser is Response.Error) {
            return Response.Error("Failed to create user in remote: ${remoteUser.message}")
        }

        val localUser = localRepository.createUser(user)
        if (localUser is Response.Error) {
            return Response.Error("Failed to save user locally: ${localUser.message}")
        }

        return Response.Success(true) // ✅ Both operations were successful
    }

    override suspend fun updateUser(user: User): Response<Boolean> {
        val remoteUser = remoteRepository.createUser(user)
        val localUser = localRepository.createUser(user)

        if (remoteUser is Response.Success && localUser is Response.Success) {
            return Response.Success(true)
        }

        return Response.Error(PUT_USER_ERROR)
    }

    companion object {
        private const val GET_USER_ERROR = "GET User error"
        private const val POST_USER_ERROR = "POST User error"
        private const val PUT_USER_ERROR = "PUT User error"
    }
}