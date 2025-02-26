package com.example.core.data.authentication.repository.remote

import com.example.core.domain.authentication.repository.UserRepository
import com.example.core.domain.shared.model.User
import com.example.core.utils.Response
import io.github.jan.supabase.postgrest.Postgrest
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryRemote @Inject constructor(
    private val postgrest: Postgrest,
) : UserRepository {

    override suspend fun getUserById(id: String): Response<User> {
        return try {
            val result = postgrest[USERS_DATABASE]
                .select { filter { User::id eq id } }
                .decodeSingle<User>()

            Timber.d(GET_USER_ID_SUCCESS + id)
            Response.Success(result)
        } catch (e: Exception) {
            Timber.e(e.message)
            Response.Error(e.message ?: ERROR_MESSAGE)
        }
    }

    override suspend fun createUser(user: User): Response<Boolean> {
        return try {
            postgrest[USERS_DATABASE].insert(user) // ✅ Ensure `id` & `created_at` are not sent
            Timber.d(CREATE_USER_ID_SUCCESS + user.id)
            Response.Success(true)
        } catch (e: Exception) {
            Timber.e(e.message)
            Response.Error(e.message ?: ERROR_MESSAGE)
        }
    }

    override suspend fun updateUser(user: User): Response<Boolean> {
        return try {
            postgrest[USERS_DATABASE]
                .update(user) {
                    filter { User::id eq user.id }
                }
            Timber.d(UPDATE_USER_ID_SUCCESS + user.id)
            Response.Success(true)
        } catch (e: Exception) {
            Timber.e(e.message)
            Response.Error(e.message ?: ERROR_MESSAGE)
        }
    }

    companion object {
        private const val ERROR_MESSAGE = "An error occurred"
        private const val USERS_DATABASE = "users"

        private const val GET_USER_ID_SUCCESS = "Successfully retrieved user in remote database with id: "
        private const val CREATE_USER_ID_SUCCESS = "Successfully created user in remote database with id: "
        private const val UPDATE_USER_ID_SUCCESS = "Successfully updated user in remote database with id: "
    }
}