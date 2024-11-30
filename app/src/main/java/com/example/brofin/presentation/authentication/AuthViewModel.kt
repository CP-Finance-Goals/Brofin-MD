package com.example.brofin.presentation.authentication

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brofin.data.mapper.toBudgeting
import com.example.brofin.data.mapper.toUserBalance
import com.example.brofin.data.mapper.toUserProfile
import com.example.brofin.data.mapper.toUserProfileEntity
import com.example.brofin.data.remote.dto.GetAllDataResponseDto
import com.example.brofin.domain.repository.BrofinRepository
import com.example.brofin.domain.repository.RemoteDataRepository
import com.example.brofin.domain.repository.datastore.UserPreferencesRepository
import com.example.brofin.presentation.authentication.state.AuthState
import com.example.brofin.utils.ReponseUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

    @HiltViewModel
    class AuthViewModel @Inject constructor(
        private val remoteDataRepository: RemoteDataRepository,
        private val userPreferencesRepository: UserPreferencesRepository,
        private val brofinRepository: BrofinRepository
    ) : ViewModel() {
        private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
        val authState: StateFlow<AuthState> = _authState.asStateFlow()

        fun loginWithEmail(email: String, password: String) {
            viewModelScope.launch {
                _authState.value = AuthState.Loading
                try {
                    val response = remoteDataRepository.login(email, password)

                    response.token?.let { token ->
                        userPreferencesRepository.updateToken(token)
                        val data = remoteDataRepository.getAllData()
                        handleUserData(data)
                        handleBudgetingData(data)
                        handleBudgetingDiaries(data)
                        handleUserBalance(data)

                        _authState.value = AuthState.Success
                        Log.d(TAG, "loginWithEmail: $data")
                    } ?: run {
                        _authState.value = AuthState.Error("Login gagal, silakan coba lagi.")
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "loginWithEmail: ${e.message}")
                    _authState.value = AuthState.Error("Terjadi kesalahan saat login")
                }
            }
        }

        private suspend fun handleUserData(data: GetAllDataResponseDto) {
            data.userProfile.map {
                it.toUserProfile()
            }.forEach { user ->
                brofinRepository.insertNoValidation(user.toUserProfileEntity())  // Menggunakan insertNoValidation
            }
        }

        private suspend fun handleBudgetingData(data: GetAllDataResponseDto) {
            data.budgetings?.mapNotNull {
                it?.toBudgeting()
            }?.forEach { budgeting ->
                brofinRepository.insertNoValidation(budgeting)  // Menggunakan insertNoValidation
            }
        }

        private suspend fun handleBudgetingDiaries(data: GetAllDataResponseDto) {
            data.budgetingDiaries?.mapNotNull {
                it?.toBudgeting()
            }?.forEach { diary ->
                brofinRepository.insertNoValidation(diary)  // Menggunakan insertNoValidation
            }
        }

        private suspend fun handleUserBalance(data: GetAllDataResponseDto) {
            data.userBalance?.mapNotNull {
                it?.toUserBalance()
            }?.forEach { balance ->
                brofinRepository.insertNoValidation(balance)  // Menggunakan insertNoValidation
            }
        }

        fun registerWithEmail(name: String, email: String, password: String) {
            viewModelScope.launch {
                _authState.value = AuthState.Loading
                try {
                    val response = remoteDataRepository.register(email, password)

                    if (response.user != null){
                        _authState.value = AuthState.Success

                    } else {
                        _authState.value = AuthState.Error("Registrasi gagal, silakan coba lagi.")
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "registerWithEmail: ${e.message}")
                    _authState.value = AuthState.Error("Registrasi gagal, silahkan cek internet anda. atau mungkin email sudah terdaftar")
                }
            }
        }

        companion object{
            private const val TAG = "AuthViewModel"
        }

    }
