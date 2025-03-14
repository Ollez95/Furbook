package com.example.core.database.local.di

import com.example.core.database.local.FurbookDatabase
import com.example.core.database.local.dao.PetDao
import com.example.core.database.local.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaosModule {

    @Singleton
    @Provides
    fun providesUserDao(database: FurbookDatabase): UserDao = database.userDao()

    @Singleton
    @Provides
    fun providesPetDao(database: FurbookDatabase): PetDao = database.petDao()
}