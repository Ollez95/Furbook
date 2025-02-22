package com.example.datastore.onboarding

import DatastoreAbstraction
import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OnBoardingDataStore @Inject constructor(
    @ApplicationContext context: Context,
) : DatastoreAbstraction<Boolean>(
    context = context,
    datastoreName = ONBOARDING_PREFS, // ✅ Unique DataStore name
    key = ONBOARDING_COMPLETED_KEY // ✅ Key for onboarding completion
) {
    companion object {
        val ONBOARDING_COMPLETED_KEY = booleanPreferencesKey("onboarding_completed")
        const val ONBOARDING_PREFS = "onboarding_prefs"
    }
}
