package open.your.eyes.miichibaseapp.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import open.your.eyes.miichibaseapp.ui.main.MainViewModel
import open.your.eyes.miichibaseapp.utils.printLog
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Single data store instance
 */
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(DataStoreManager.dataStoreName)


@Singleton
class DataStoreManager @Inject constructor(
    private val context: Context
) : DataStoreHelper {
    private val dataStore: DataStore<Preferences> by lazy {
        context.dataStore
    }

    companion object {
        const val dataStoreName = "dataStore_AppName"
        val keyUserName = stringPreferencesKey("KEY_USER_NAME")
        val keyIsLogin = booleanPreferencesKey("isLogin")
        val a = preferencesOf()
    }

    //run on IO thread
    override suspend fun saveUserName(name: String) {
        dataStore.put(keyUserName, name)
    }

    override suspend fun getUserName(): String {
        return dataStore.get(keyUserName)
    }



    private suspend fun <T> DataStore<Preferences>.put(key: Preferences.Key<T>, value: T) {
        edit { settings ->
            settings.putAll()
            settings[key] = value
        }
    }

    private suspend inline fun <reified T> DataStore<Preferences>.get(key: Preferences.Key<T>): T {
        return data.map { preferences ->
            preferences[key] ?: defaultValue()
        }.first()
    }

    /**
     * using Gson to parser Object to String
     */
    private inline fun <reified T> defaultValue(): T {
        return when (T::class) {
            Boolean::class -> {
                false as T
            }
            Int::class -> {
                0 as T
            }
            Long::class -> {
                0L as T
            }
            Float::class -> {
                0f as T
            }
            String::class -> {
                "" as T
            }
            Set::class -> {
                mutableSetOf<String>() as T
            }
            else -> {
                throw IllegalStateException("Type value not support")
            }
        }
    }


}