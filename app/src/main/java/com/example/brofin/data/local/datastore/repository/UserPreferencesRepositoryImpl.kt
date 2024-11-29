package com.example.brofin.data.local.datastore.repository

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.example.brofin.data.local.datastore.utils.PreferencesKeys
import com.example.brofin.data.local.datastore.utils.UserPreferences
import com.example.brofin.domain.repository.datastore.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserPreferencesRepositoryImpl @Inject constructor(
     private val dataStore: DataStore<Preferences>
) : UserPreferencesRepository {
    override val userPreferencesFlow: Flow<UserPreferences> = dataStore.data
        .catch{ exception ->
            if (exception is IOException){
                emit(emptyPreferences())
            }else{
                throw exception
            }
        }
        .map{ preferences ->
            val isDarkMode = preferences[PreferencesKeys.IS_DARK_MODE] == true
            val token = preferences[PreferencesKeys.TOKEN]

            UserPreferences(
                isDarkMode = isDarkMode,
                token = token
            )
        }

    override suspend fun updateToken(token: String?) {
        dataStore.edit { preferences ->
            if (token == null) {
                preferences.remove(PreferencesKeys.TOKEN)
            } else {
                preferences[PreferencesKeys.TOKEN] = token
            }
        }
    }

    override suspend fun updateDarkMode(isDarkMode: Boolean?) {
        dataStore.edit { preferences ->
            if (isDarkMode == null) {
                preferences.remove(PreferencesKeys.IS_DARK_MODE)
            } else {
                preferences[PreferencesKeys.IS_DARK_MODE] = isDarkMode
            }
        }
    }

}