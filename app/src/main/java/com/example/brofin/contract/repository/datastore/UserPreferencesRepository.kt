package com.example.brofin.contract.repository.datastore

import com.example.brofin.data.local.datastore.utils.UserPreferences
import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {
    val userPreferencesFlow: Flow<UserPreferences>

    suspend fun updateDarkMode(isDarkMode: Boolean)

    suspend fun updatePreferredLanguage(language: String)

    suspend fun updateBudgetNotificationEnabled(isEnabled: Boolean)

    suspend fun updateTransactionReminderEnabled(isEnabled: Boolean)

    suspend fun updateDailyBudgetLimit(limit: Double)

    suspend fun updateMonthlyBudgetLimit(limit: Double)
}
