package com.example.core.database.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.core.database.local.dao.UserDao
import com.example.core.database.local.model.UserEntity

@Database(
    entities = [UserEntity::class],
    version = 1
)
abstract class FurbookDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}