package com.example.brofin.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brofin.data.mapper.toUserProfile
import com.example.brofin.domain.models.UserProfile
import com.example.brofin.domain.repository.BrofinRepository
import com.example.brofin.domain.repository.datastore.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val brofinRepository: BrofinRepository
): ViewModel() {

    private val _userProfile = MutableStateFlow<UserProfile?>(null)
    val userProfile = _userProfile.asStateFlow()

    private fun getUserData() {
        viewModelScope.launch {
            brofinRepository.getUserProfile().let {
                _userProfile.value = it?.toUserProfile()
            }
        }
    }

    init {
        getUserData()
    }

    val userPreferencesFlow = userPreferencesRepository.userPreferencesFlow

    fun updateDarkMode(isDarkMode: Boolean?) {
        viewModelScope.launch {
            userPreferencesRepository.updateDarkMode(isDarkMode)
        }
    }

    fun logout() {
        viewModelScope.launch {
            userPreferencesRepository.updateToken(null)
            brofinRepository.logout()
        }
    }

}