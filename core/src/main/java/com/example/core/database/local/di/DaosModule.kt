package com.example.core.database.local.di

import com.example.core.database.local.FurbookDatabase
import com.example.core.database.local.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaosModule {
    @Provides
    fun providesUserDao(
        database: FurbookDatabase,
    ): UserDao = database.userDao()
}