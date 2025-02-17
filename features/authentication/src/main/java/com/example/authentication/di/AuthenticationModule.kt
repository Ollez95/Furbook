package com.example.authentication.di

import com.example.authentication.data.login.fake_repository.LoginFakeRepositoryImpl
import com.example.authentication.domain.login.repository.LoginRepository
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
    fun provideLoginRepository(): LoginRepository = LoginFakeRepositoryImpl()
}

