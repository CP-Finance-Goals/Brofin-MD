package com.example.brofin.presentation.settings

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.brofin.domain.repository.AuthRepository
import com.example.brofin.domain.repository.datastore.UserPreferencesRepository
import com.example.brofin.worker.BudgetingReminderWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val authRepository: AuthRepository,
    @ApplicationContext private val context: Context
): ViewModel() {

    val userPreferencesFlow = userPreferencesRepository.userPreferencesFlow

    fun updateDarkMode(isDarkMode: Boolean?) {
        viewModelScope.launch {
            userPreferencesRepository.updateDarkMode(isDarkMode)
        }
    }

    // Fungsi untuk mengaktifkan atau menonaktifkan reminder
    fun updateNotification(isEnabled: Boolean) {
        viewModelScope.launch {
            userPreferencesRepository.updateBudgetNotificationEnabled(isEnabled)
            if (isEnabled) {
                scheduleWorker() // Jadwalkan Worker
            } else {
                cancelWorker() // Hentikan Worker
            }
        }
    }

    // Jadwalkan Worker
    private fun scheduleWorker() {
        val workRequest = OneTimeWorkRequestBuilder<BudgetingReminderWorker>()
            .setInitialDelay(1, TimeUnit.MINUTES) // Jalankan setiap 1 menit
            .build()

        WorkManager.getInstance(context).enqueueUniqueWork(
            "BudgetingReminderWorker",
            ExistingWorkPolicy.REPLACE,
            workRequest
        )
    }

    // Hentikan Worker
    private fun cancelWorker() {
        WorkManager.getInstance(context).cancelUniqueWork("BudgetingReminderWorker")
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
        }
    }


}