package com.example.core.di.module

import com.example.core.data.authentication.repository.local.UserRepositoryLocal
import com.example.core.data.authentication.repository.remote.UserRepositoryRemote
import com.example.core.data.authentication.repository.shared.UserRepositoryImpl
import com.example.core.domain.authentication.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SharedModule {

    @Provides
    @Singleton
    fun provideUserRepository(
        localRepository: UserRepositoryLocal,
        remoteRepository: UserRepositoryRemote
    ): UserRepository {
        return UserRepositoryImpl(localRepository, remoteRepository)
    }
}