package com.example.core.di.authentication

import com.example.core.data.authentication.repository.remote.AuthenticationRepositoryImpl
import com.example.core.domain.authentication.repository.AuthenticationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthenticationModule {

    @Binds
    @Singleton
    abstract fun provideAuthenticationRepository(impl: AuthenticationRepositoryImpl): AuthenticationRepository
}

