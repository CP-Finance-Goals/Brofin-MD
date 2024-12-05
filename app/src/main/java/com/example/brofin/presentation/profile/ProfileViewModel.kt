package com.example.brofin.presentation.profile

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brofin.data.mapper.toUserProfile
import com.example.brofin.domain.StateApp
import com.example.brofin.domain.models.UserProfile
import com.example.brofin.domain.repository.BrofinRepository
import com.example.brofin.domain.repository.RemoteDataRepository
import com.example.brofin.utils.preparePhotoPart
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val brofinRepository: BrofinRepository,
    private val remoteDataRepository: RemoteDataRepository
): ViewModel() {

    private val _dataProfile = MutableStateFlow<StateApp<UserProfile>>(StateApp.Idle)
    val dataProfile = _dataProfile.asStateFlow()


    init {
        getUserProfile()
    }

    private fun getUserProfile() {
       viewModelScope.launch {
           _dataProfile.value = StateApp.Loading
           try{
              val data = brofinRepository.getUserProfile()
               if (data != null) {
                   _dataProfile.value = StateApp.Success(data.toUserProfile())
               } else {
                   _dataProfile.value = StateApp.Error("Data tidak di ketahui")
               }
          } catch (e: Exception) {
              Log.e(TAG, "getUserProfile: ${e.message}")
               _dataProfile.value = StateApp.Error("Terjadi kesalahan")
          }
       }
    }

    fun resetState () {
        _dataProfile.value = StateApp.Idle
    }

    fun editProfile(name: String?, dob: String?, photoUri: Uri? = null, contentResolver: ContentResolver, context: Context) {
        viewModelScope.launch {
            _dataProfile.value = StateApp.Loading
            try {
                val currentData = brofinRepository.getUserProfile()

                val updatedData = currentData?.copy(
                    name = name ?: currentData.name,
                    dob = dob ?: currentData.dob
                )

                remoteDataRepository.editUserProfile(
                    username = updatedData?.name?.toRequestBody("text/plain".toMediaTypeOrNull()) ?: currentData?.name?.toRequestBody("text/plain".toMediaTypeOrNull()),
                    dob = updatedData?.dob?.toRequestBody("text/plain".toMediaTypeOrNull()) ?: currentData?.dob?.toRequestBody("text/plain".toMediaTypeOrNull()),
                    photo = preparePhotoPart(photoUri, contentResolver, context) ,
                    gender = updatedData?.gender?.toRequestBody("text/plain".toMediaTypeOrNull()) ?: currentData?.gender?.toRequestBody("text/plain".toMediaTypeOrNull()),
                    savings = updatedData?.savings?.toString()?.toRequestBody("text/plain".toMediaTypeOrNull()) ?: currentData?.savings?.toString()?.toRequestBody("text/plain".toMediaTypeOrNull())
                )

                if (updatedData != null) {
                    _dataProfile.value = StateApp.Success(updatedData.toUserProfile())
                } else {
                    getUserProfile()
                    _dataProfile.value = StateApp.Error("Error updating profile")
                }

            } catch (e: Exception) {
                Log.e(TAG, "editProfile: ${e.message}")
                _dataProfile.value = StateApp.Error("Terjadi kesalahan saat mengupdate profile")
            }
        }
    }

    companion object {
        const val TAG = "ProfileViewModel"
    }


}