package com.example.datastore.onboarding

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import com.example.datastore.DatastoreAbstraction
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WasOnBoardingExecutedDatastore
@Inject constructor(dataStore: DataStore<Preferences>) : DatastoreAbstraction<Boolean>(
    dataStore = dataStore,
    key = ONBOARDING_COMPLETED_KEY
) {
    companion object {
        val ONBOARDING_COMPLETED_KEY = booleanPreferencesKey("onboarding_completed")
    }
}
