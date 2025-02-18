package com.example.core.di.authentication

import com.example.core.data.authentication.login.fake_repository.LoginFakeRepositoryImpl
import com.example.core.data.authentication.register.fake_repository.RegisterFakeRepositoryImpl
import com.example.core.domain.authentication.login.repository.LoginRepository
import com.example.core.domain.authentication.register.repository.RegisterRepository
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

    @Provides
    @Singleton
    fun provideRegisterRepository(): RegisterRepository = RegisterFakeRepositoryImpl()
}

