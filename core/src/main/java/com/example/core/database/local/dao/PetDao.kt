package com.example.core.database.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.core.database.local.model.UserEntity

@Dao
interface PetDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPet(user: UserEntity)

    @Query("SELECT * FROM users WHERE id = :userId")
    suspend fun getPet(userId: String): UserEntity?
}