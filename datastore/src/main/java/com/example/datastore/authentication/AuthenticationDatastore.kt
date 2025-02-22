package com.example.datastore.authentication

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthenticationDataStore @Inject constructor(@ApplicationContext private val context: Context) {

    // ✅ Extension property to get DataStore instance
    private val Context.dataStore by preferencesDataStore(name = DATASTORE_NAME)

    // Keys for stored values
    private val AUTH_TOKEN_KEY = stringPreferencesKey("auth_token")

    // ✅ Save auth token
    suspend fun saveAuthToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[AUTH_TOKEN_KEY] = token
        }
    }

    // ✅ Read auth token
    fun getAuthToken(): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[AUTH_TOKEN_KEY]
        }
    }

    // ✅ Clear auth token (Logout)
    suspend fun clearAuthToken() {
        context.dataStore.edit { preferences ->
            preferences.remove(AUTH_TOKEN_KEY)
        }
    }

    companion object {
        private const val DATASTORE_NAME = "auth_preferences"
    }
}