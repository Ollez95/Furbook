import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.datastore.Datastore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

abstract class DatastoreAbstraction<T>(
    private val context: Context,
    datastoreName: String,
    private val key: Preferences.Key<T>,
) : Datastore<T> {

    private val Context.dataStore by preferencesDataStore(name = datastoreName)

    override suspend fun saveValueDatastore(value: T) {
        context.dataStore.edit { preferences ->
            when (value) {
                is String -> preferences[key] = value
                is Boolean -> preferences[key] = value
                is Int -> preferences[key] = value
                is Float -> preferences[key] = value
                is Long -> preferences[key] = value
                else -> throw IllegalArgumentException("Unsupported type")
            }
        }
    }

    override fun getValueDatastore(): Flow<T?> {
        return context.dataStore.data.map { preferences ->
            preferences[key]
        }
    }

    override suspend fun clearValueDatastore() {
        context.dataStore.edit { preferences ->
            preferences.remove(key)
        }
    }
}
