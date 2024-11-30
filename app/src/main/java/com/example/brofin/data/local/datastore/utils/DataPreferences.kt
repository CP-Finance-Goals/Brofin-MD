package com.example.brofin.data.local.datastore.utils

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

private const val USER_PREFERENCES = "user_preferences"
val Context.userPreferences by preferencesDataStore(name = USER_PREFERENCES)

data class UserPreferences(
    val isDarkMode: Boolean? = false,
    val token: String? = null
)

object PreferencesKeys {
    val IS_DARK_MODE = booleanPreferencesKey("is_dark_mode")
    val TOKEN = stringPreferencesKey("token")
}
