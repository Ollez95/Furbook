package com.example.core.di.onboarding

import com.example.core.data.onboarding.repository.OnBoardingRepositoryImpl
import com.example.core.domain.onboarding.repository.OnBoardingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OnBoardingModule {
    @Provides
    @Singleton
    fun provideOnBoardingRepository(): OnBoardingRepository = OnBoardingRepositoryImpl()
}