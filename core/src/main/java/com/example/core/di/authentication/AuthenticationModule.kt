package com.example.core.di.authentication

import com.example.core.data.authentication.fake_repository.AuthenticationFakeRepositoryImpl
import com.example.core.domain.authentication.repository.AuthenticationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthenticationModule {

    @Provides
    @Singleton
    fun provideAuthenticationRepository(): AuthenticationRepository = AuthenticationFakeRepositoryImpl()
}

