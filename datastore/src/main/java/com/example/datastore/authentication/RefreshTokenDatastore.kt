package com.example.datastore.authentication

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.datastore.DatastoreAbstraction
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RefreshTokenDatastore @Inject constructor(dataStore: DataStore<Preferences>) : DatastoreAbstraction<String>(
    dataStore = dataStore,
    key = AUTHENTICATION_TOKEN
) {
    companion object {
        val AUTHENTICATION_TOKEN = stringPreferencesKey("refresh_token")
    }
}
