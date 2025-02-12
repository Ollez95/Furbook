package com.example.onboarding.di

import com.example.onboarding.data.repository.OnBoardingRepositoryImpl
import com.example.onboarding.domain.repository.OnBoardingRepository
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