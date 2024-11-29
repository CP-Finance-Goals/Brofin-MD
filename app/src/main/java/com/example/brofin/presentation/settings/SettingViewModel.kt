package com.example.brofin.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brofin.domain.repository.datastore.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository,
): ViewModel() {

    val userPreferencesFlow = userPreferencesRepository.userPreferencesFlow

    fun updateDarkMode(isDarkMode: Boolean?) {
        viewModelScope.launch {
            userPreferencesRepository.updateDarkMode(isDarkMode)
        }
    }

    fun logout() {
        viewModelScope.launch {
            userPreferencesRepository.updateToken(null)
        }
    }

}