package com.example.brofin.domain.repository.datastore

import com.example.brofin.data.local.datastore.utils.UserPreferences
import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {
    val userPreferencesFlow: Flow<UserPreferences>

    suspend fun updateToken(token: String?)

    suspend fun updateDarkMode(isDarkMode: Boolean?)

}
