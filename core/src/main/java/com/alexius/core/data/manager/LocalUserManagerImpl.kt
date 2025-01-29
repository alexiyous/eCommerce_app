package com.alexius.core.data.manager

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.alexius.core.domain.manager.LocalUserManager
import com.alexius.core.util.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalUserManagerImpl(private val application: Application): LocalUserManager {


    override fun readAppEntry(): Flow<Boolean> {
        return application.dataStore.data.map { preferences ->
            preferences[PreferenceKeys.APP_ENTRY] == true
        }
    }

    override suspend fun saveAppEntry(value: Boolean) {
        application.dataStore.edit { settings ->
            settings[PreferenceKeys.APP_ENTRY] = value
        }
    }
}

private val readOnlyProperty = preferencesDataStore(name = Constants.USER_SETTINGS)

val Context.dataStore: DataStore<Preferences> by readOnlyProperty

private object PreferenceKeys {
    val APP_ENTRY = booleanPreferencesKey(Constants.APP_ENTRY)
}