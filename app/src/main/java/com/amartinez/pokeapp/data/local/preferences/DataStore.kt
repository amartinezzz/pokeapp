package com.amartinez.pokeapp.data.local.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class PreferenceManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val IS_FIRST_TIME = booleanPreferencesKey("is_first_access")
    private val USER_NAME = stringPreferencesKey("user_name")
    private val USER_PASSWORD_HASH = stringPreferencesKey("user_password")
    private val IS_BIOMETRIC_ENABLED = booleanPreferencesKey("is_biometric_enabled")

    val isFirstAccess: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[IS_FIRST_TIME] != false
        }

    suspend fun saveFirstAccessCompleted() {
        context.dataStore.edit { preferences ->
            preferences[IS_FIRST_TIME] = false
        }
    }

    private suspend fun getSavedUser(): String? {
        return context.dataStore.data.map { prefs ->
            prefs[USER_NAME]
        }.first()
    }

    private suspend fun getSavedPassword(): String? {
        return context.dataStore.data.map { prefs ->
            prefs[USER_PASSWORD_HASH]
        }.first()
    }

    suspend fun isUserRegistered(user: String, password: String): Boolean {
        val savedUser = getSavedUser()
        val savedPassword = getSavedPassword()

        return savedUser == user &&
                savedPassword == password
    }

    suspend fun registerUser(name: String, password: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_NAME] = name
            preferences[USER_PASSWORD_HASH] = password
        }
    }

    suspend fun saveBiometricPreference(isEnabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[IS_BIOMETRIC_ENABLED] = isEnabled
        }
    }

    suspend fun isBiometricPreferenceEnabled(): Boolean {
        return context.dataStore.data.map { prefs ->
            prefs[IS_BIOMETRIC_ENABLED]
        }.first() == true
    }
}