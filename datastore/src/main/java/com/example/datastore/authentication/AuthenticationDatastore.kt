import android.content.Context
import androidx.datastore.preferences.core.stringPreferencesKey
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthenticationDataStore @Inject constructor(
    @ApplicationContext context: Context,
) : DatastoreAbstraction<String>(
    context = context,
    datastoreName = AUTHENTICATION_PREFS, // ✅ Now passed dynamically
    key = AUTHENTICATION_TOKEN
) {
    companion object {
        val AUTHENTICATION_TOKEN = stringPreferencesKey("auth_token")
        const val AUTHENTICATION_PREFS = "auth_prefs"
    }
}
