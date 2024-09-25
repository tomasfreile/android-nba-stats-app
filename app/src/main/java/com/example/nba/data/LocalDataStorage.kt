package com.example.nba.data

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
val Context.dataStore by preferencesDataStore(name = "NBA_DATA_STORE")
object PreferencesKeys {
    val TEAM_ICON = stringPreferencesKey("team_icon_")
}
suspend fun saveToDataStore(context: Context, key: String, value: String) {
    val preferencesKey = stringPreferencesKey(key)
    context.dataStore.edit { preferences ->
        preferences[preferencesKey] = value
    }
}

suspend fun getFromDataStore(context: Context, key: String): String? {
    val preferencesKey = stringPreferencesKey(key)
    val preferences = context.dataStore.data.first()
    return preferences[preferencesKey]
}
