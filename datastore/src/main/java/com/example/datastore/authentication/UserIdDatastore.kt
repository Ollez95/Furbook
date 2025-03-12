package com.example.datastore.authentication

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.datastore.DatastoreAbstraction
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserIdDatastore @Inject constructor(dataStore: DataStore<Preferences>) : DatastoreAbstraction<String>(
    dataStore = dataStore,
    key = USER_ID
) {
    companion object {
        val USER_ID = stringPreferencesKey("user_id")
    }
}