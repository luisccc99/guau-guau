package com.guau_guau.guau_guau.data

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*


class UserPreferences(
    context: Context
) {
    private val applicationContext = context.applicationContext
    private val dataStore: DataStore<Preferences>

    init {
        dataStore = applicationContext.createDataStore(
            name = "my_data_store"
        )
    }

    val authToken: Flow<String?>
        get() = dataStore.data.map { preferences ->
            preferences[KEY_AUTH]
        }

    val userId: Flow<String?>
        get() = dataStore.data.map { preferences ->
            preferences[USER_ID]
        }

    val expTime: Flow<String?>
        get() = dataStore.data.map { preferences ->
            preferences[EXP_TIME]
        }

    suspend fun saveAuthToken(authToken: String) {
        dataStore.edit { preferences ->
            preferences[KEY_AUTH] = authToken
        }
    }

    suspend fun saveUserId(userId: String) {
        dataStore.edit { preferences ->
            preferences[USER_ID] = userId
        }
    }

    suspend fun saveExpTime(expTime: String) {
        dataStore.edit { preferences ->
            preferences[EXP_TIME] = expTime
        }
    }

    suspend fun clear() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    companion object {
        private val KEY_AUTH = preferencesKey<String>("key_auth")
        private val USER_ID = preferencesKey<String>("user_id")
        private val EXP_TIME = preferencesKey<String>("exp_time")
    }

}