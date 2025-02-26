package com.example.core.database.local.di

import android.content.Context
import androidx.room.Room
import com.example.core.database.local.FurbookDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providesFurbookDatabase(
        @ApplicationContext context: Context,
    ): FurbookDatabase = Room.databaseBuilder(
        context, FurbookDatabase::class.java,
        "furbook-database").build()
}