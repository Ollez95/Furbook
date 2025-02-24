package com.example.core.data.authentication.repository

import com.example.core.domain.authentication.repository.UserRepository
import com.example.core.domain.shared.model.User
import com.example.core.utils.Response
import io.github.jan.supabase.postgrest.Postgrest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val postgrest: Postgrest,
) : UserRepository {

    override fun getUserById(id: String): Flow<Response<User>> = flow {
        emit(Response.Loading)
        try {
            val result = postgrest[USERS_DATABASE]
                .select { filter { User::id eq id } }
                .decodeSingle<User>()
            emit(Response.Success(result))
        } catch (e: Exception) {
            emit(Response.Error(e.message ?: ERROR_MESSAGE))
        }
    }

    override fun createUser(user: User): Flow<Response<Boolean>> = flow {
        emit(Response.Loading)
        try {
            postgrest[USERS_DATABASE].insert(user) // ✅ Ensure `id` & `created_at` are not sent
            emit(Response.Success(true))
        } catch (e: Exception) {
            emit(Response.Error(e.message ?: ERROR_MESSAGE))
        }
    }

    override fun updateUser(user: User): Flow<Response<Boolean>> = flow {
        emit(Response.Loading)
        try {
            postgrest[USERS_DATABASE]
                .update(user) {
                    filter { User::id eq user.id }
                }

            emit(Response.Success(true))
        } catch (e: Exception) {
            emit(Response.Error(e.message ?: ERROR_MESSAGE))
        }
    }

    companion object {
        private const val ERROR_MESSAGE = "An error occurred"
        private const val USERS_DATABASE = "users"
    }
}