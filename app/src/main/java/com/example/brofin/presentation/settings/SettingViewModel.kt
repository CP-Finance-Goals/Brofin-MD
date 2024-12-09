package com.example.brofin.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brofin.data.mapper.toUserProfileEntity
import com.example.brofin.domain.StateApp
import com.example.brofin.domain.models.UserProfile
import com.example.brofin.domain.repository.BrofinRepository
import com.example.brofin.domain.repository.RemoteDataRepository
import com.example.brofin.domain.repository.datastore.UserPreferencesRepository
import com.example.brofin.utils.ReponseUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val brofinRepository: BrofinRepository,
    private val remoteDataRepository: RemoteDataRepository
): ViewModel() {

    private val _userProfile = MutableStateFlow<UserProfile?>(null)
    val userProfile = _userProfile.asStateFlow()

    private val _updateUserProfile = MutableStateFlow<StateApp<Boolean>>(StateApp.Idle)
    val updateProfile = _updateUserProfile.asStateFlow()

    private fun getUserData() {
        viewModelScope.launch {
            brofinRepository.getUserFlow().collect {
                if (it == null) {
                    _userProfile.value = null
                } else {
                    _userProfile.value = it
                }
            }
        }
    }

    init {
        getUserData()
    }

    fun updateSaving(saving: Double){
        viewModelScope.launch{
            _updateUserProfile.value = StateApp.Loading
            val userData = userProfile.value?.copy(savings = saving)
            if (userData != null) {
                val usernameRequestBody =
                    userData.name?.toRequestBody("text/plain".toMediaTypeOrNull())
                val savingsRequestBody =
                    saving.toString().toRequestBody("text/plain".toMediaTypeOrNull())
                val genderRequestBody =
                    userData.gender?.toRequestBody("text/plain".toMediaTypeOrNull())
                val dobRequestBody = userData.dob?.toRequestBody("text/plain".toMediaTypeOrNull())

                val response = remoteDataRepository.editUserProfile(
                    username =  usernameRequestBody,
                    savings = savingsRequestBody,
                    gender =  genderRequestBody,
                    dob =  dobRequestBody,
                    photo =  null
                )

                if (response.message == ReponseUtils.UPDATED_USER_PROFILE_SUCCESS) {
                    brofinRepository.insertOrUpdateUserProfile(userData.toUserProfileEntity())
                    _updateUserProfile.value = StateApp.Success(true)
                } else {
                    _updateUserProfile.value = StateApp.Error("gagal update data tabungan")
                }
            }
        }
    }

    val userPreferencesFlow = userPreferencesRepository.userPreferencesFlow

    fun updateDarkMode(isDarkMode: Boolean?) {
        viewModelScope.launch {
            userPreferencesRepository.updateDarkMode(isDarkMode)
        }
    }

    fun resetState() {
        _updateUserProfile.value = StateApp.Idle
    }

    fun logout() {
        viewModelScope.launch {
            userPreferencesRepository.updateToken(null)
            brofinRepository.logout()
        }
    }

}