package com.example.core.di.module

import com.example.core.data.authentication.repository.UserRepositoryImpl
import com.example.core.domain.authentication.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
fun interface DatabaseModule {

    @Binds
    @Singleton
    abstract fun provideUserRepository(impl: UserRepositoryImpl): UserRepository
}