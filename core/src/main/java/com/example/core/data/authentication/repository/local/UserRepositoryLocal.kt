package com.example.core.data.authentication.repository.local

import com.example.core.database.local.dao.UserDao
import com.example.core.database.local.model.toDomainModel
import com.example.core.domain.authentication.repository.UserRepository
import com.example.core.domain.shared.model.User
import com.example.core.domain.shared.model.toEntity
import com.example.core.utils.Response
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryLocal @Inject constructor(
    private val userDao: UserDao,
) : UserRepository {

    override suspend fun getUserById(id: String): Response<User> {
        return try {
            val result = userDao.getUser(id)?.toDomainModel() ?: User()
            Timber.d(GET_USER_ID_SUCCESS + id)
            Response.Success(result)
        } catch (e: Exception) {
            Timber.e(e.message)
            Response.Error(e.message ?: ERROR_MESSAGE)
        }
    }

    override suspend fun createUser(user: User): Response<Boolean> {
        return try {
            userDao.insertUser(user.toEntity())
            Timber.d(CREATE_USER_ID_SUCCESS + user.id)
            Response.Success(true)
        } catch (e: Exception) {
            Timber.e(e.message)
            Response.Error(e.message ?: ERROR_MESSAGE)
        }
    }

    override suspend fun updateUser(user: User): Response<Boolean> {
        return try {
            userDao.updateUser(user.toEntity())
            Timber.d(UPDATE_USER_ID_SUCCESS + user.id)
            Response.Success(true)
        } catch (e: Exception) {
            Timber.e(e.message)
            Response.Error(e.message ?: ERROR_MESSAGE)
        }
    }

    companion object {
        private const val ERROR_MESSAGE = "An error occurred"

        private const val GET_USER_ID_SUCCESS = "Successfully retrieved user in local database with id: "
        private const val CREATE_USER_ID_SUCCESS = "Successfully created user in local database with id: "
        private const val UPDATE_USER_ID_SUCCESS = "Successfully updated user in local database with id: "
    }
}