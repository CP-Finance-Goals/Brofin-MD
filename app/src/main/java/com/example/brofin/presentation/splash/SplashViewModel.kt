package com.example.brofin.presentation.splash

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brofin.domain.repository.AuthRepository
import com.example.brofin.domain.repository.BrofinRepository
import com.example.brofin.utils.getCurrentMonthAndYearAsLong
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val brofinRepository: BrofinRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SplashUiState())
    val uiState: StateFlow<SplashUiState> = _uiState.asStateFlow()

    init {
        checkUserStatus()
    }

    private fun checkUserStatus() {
        viewModelScope.launch {
            try {
                val isLoggedIn = authRepository.userExists().firstOrNull() == true

                if (isLoggedIn) {
                    val userId = authRepository.getCurrentUser()?.uid ?: throw IllegalStateException("User ID tidak ditemukan")
                    val balanceExists = userBalanceIsExist(userId)

                    _uiState.value = SplashUiState(isUserLoggedIn = true, userBalanceExist = balanceExists)
                } else {
                    _uiState.value = SplashUiState(isUserLoggedIn = false, userBalanceExist = false)
                }
            } catch (e: Exception) {
                Log.e("SplashViewModel", "Error in checkUserStatus", e)
                _uiState.value = SplashUiState(
                    isUserLoggedIn = false,
                    userBalanceExist = null,
                    errorMessage = e.message
                )
            }
        }
    }

    private suspend fun userBalanceIsExist(userId: String): Boolean {
        return brofinRepository.userBalanceExists(userId, getCurrentMonthAndYearAsLong())
    }
}


data class SplashUiState(
    val isUserLoggedIn: Boolean? = null,
    val userBalanceExist: Boolean? = null,
    val errorMessage: String? = null
)
