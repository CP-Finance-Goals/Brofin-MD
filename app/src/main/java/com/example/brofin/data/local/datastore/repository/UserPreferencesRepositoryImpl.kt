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
            val preferredLanguage = preferences[PreferencesKeys.PREFERRED_LANGUAGE] ?: "en"
            val budgetNotificationEnabled = preferences[PreferencesKeys.BUDGET_NOTIFICATION_ENABLED] != false
            val transactionReminderEnabled = preferences[PreferencesKeys.TRANSACTION_REMINDER_ENABLED] != false
            val dailyBudgetLimit = preferences[PreferencesKeys.DAILY_BUDGET_LIMIT] ?: 0.0
            val monthlyBudgetLimit = preferences[PreferencesKeys.MONTHLY_BUDGET_LIMIT] ?: 0.0

            UserPreferences(
                isDarkMode = isDarkMode,
                preferredLanguage = preferredLanguage,
                budgetNotificationEnabled = budgetNotificationEnabled,
                transactionReminderEnabled = transactionReminderEnabled,
                dailyBudgetLimit = dailyBudgetLimit,
                monthlyBudgetLimit = monthlyBudgetLimit
            )
        }

    override suspend fun updateDarkMode(isDarkMode: Boolean?) {
        dataStore.edit { preferences ->
            if (isDarkMode == null) {
                preferences.remove(PreferencesKeys.IS_DARK_MODE) // Hapus nilai untuk merepresentasikan null
            } else {
                preferences[PreferencesKeys.IS_DARK_MODE] = isDarkMode // Simpan nilai true/false
            }
        }
    }

    override suspend fun updatePreferredLanguage(language: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.PREFERRED_LANGUAGE] = language
        }
    }

    override suspend fun updateBudgetNotificationEnabled(isEnabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.BUDGET_NOTIFICATION_ENABLED] = isEnabled
        }
    }

    override suspend fun updateTransactionReminderEnabled(isEnabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.TRANSACTION_REMINDER_ENABLED] = isEnabled
        }
    }

    override suspend fun updateDailyBudgetLimit(limit: Double) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.DAILY_BUDGET_LIMIT] = limit
        }
    }

    override suspend fun updateMonthlyBudgetLimit(limit: Double) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.MONTHLY_BUDGET_LIMIT] = limit
        }
    }
}