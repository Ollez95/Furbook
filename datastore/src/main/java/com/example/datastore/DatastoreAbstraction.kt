package com.example.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

abstract class DatastoreAbstraction<T>(
    private val dataStore: DataStore<Preferences>,
    private val key: Preferences.Key<T>,
) : Datastore<T> {

    override suspend fun saveValueDatastore(value: T) {
        dataStore.edit { preferences ->
            preferences[key] = value // Direct assignment
        }
    }

    override fun getValueDatastore(): Flow<T?> {
        return dataStore.data.map { preferences ->
            preferences[key]
        }
    }

    override suspend fun clearValueDatastore() {
        dataStore.edit { preferences ->
            preferences.remove(key)
        }
    }

    override suspend fun getValueDataStoreOnce(): T? {
        return dataStore.data
            .map { it[key] }
            .first()
    }
}
