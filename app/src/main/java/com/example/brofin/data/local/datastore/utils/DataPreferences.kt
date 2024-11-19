package com.example.brofin.data.local.datastore.utils

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

private const val USER_PREFERENCES = "user_preferences"
val Context.userPreferences by preferencesDataStore(name = USER_PREFERENCES)

data class UserPreferences(
    val isDarkMode: Boolean? = false,                    // Preferensi tema aplikasi (dark/light mode)
    val preferredLanguage: String = "en",               // Bahasa yang diinginkan pengguna
    val budgetNotificationEnabled: Boolean = true,      // Notifikasi anggaran
    val transactionReminderEnabled: Boolean = true,     // Pengingat untuk transaksi harian/bulanan
    val dailyBudgetLimit: Double = 0.0,                 // Batas harian anggaran
    val monthlyBudgetLimit: Double = 0.0                // Batas bulanan anggaran
)

object PreferencesKeys {
    val IS_DARK_MODE = booleanPreferencesKey("is_dark_mode")
    val PREFERRED_LANGUAGE = stringPreferencesKey("preferred_language")
    val BUDGET_NOTIFICATION_ENABLED = booleanPreferencesKey("budget_notification_enabled")
    val TRANSACTION_REMINDER_ENABLED = booleanPreferencesKey("transaction_reminder_enabled")
    val DAILY_BUDGET_LIMIT = doublePreferencesKey("daily_budget_limit")
    val MONTHLY_BUDGET_LIMIT = doublePreferencesKey("monthly_budget_limit")
}
