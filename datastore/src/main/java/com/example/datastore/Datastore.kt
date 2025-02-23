package com.example.datastore

import kotlinx.coroutines.flow.Flow

interface Datastore<T> {
    suspend fun saveValueDatastore(value: T)
    fun getValueDatastore(): Flow<T?>
    suspend fun clearValueDatastore()
    suspend fun getValueDataStoreOnce(): T?
}
