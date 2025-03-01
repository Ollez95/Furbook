package com.example.core.di.onboarding

import android.content.Context
import com.example.core.data.onboarding.repository.LottieRepositoryImpl
import com.example.core.data.onboarding.repository.OnBoardingRepositoryImpl
import com.example.core.domain.onboarding.repository.LottieRepository
import com.example.core.domain.onboarding.repository.OnBoardingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OnBoardingModule {
    @Provides
    @Singleton
    fun provideOnBoardingRepository(): OnBoardingRepository = OnBoardingRepositoryImpl()

    @Provides
    @Singleton
    fun provideLottieRepository(@ApplicationContext context: Context): LottieRepository {
        return LottieRepositoryImpl(context)
    }
}