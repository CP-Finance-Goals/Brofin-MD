package com.example.brofin.presentation.splash

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brofin.domain.repository.AuthRepository
import com.example.brofin.domain.repository.BrofinRepository
import com.example.brofin.domain.repository.datastore.UserPreferencesRepository
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
    private val brofinRepository: BrofinRepository,
    userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SplashUiState())
    val uiState: StateFlow<SplashUiState> = _uiState.asStateFlow()

    val userPreferencesFlow = userPreferencesRepository.userPreferencesFlow


    init {
        checkUserStatus()
    }

    private fun checkUserStatus() {
        viewModelScope.launch {
            try {
                val balanceExists = userBalanceIsExist()
                if (balanceExists) {
                    _uiState.value = SplashUiState(
                        isUserLoggedIn = true,
                        userBalanceExist = true
                    )
                } else {
                    _uiState.value = SplashUiState(
                        isUserLoggedIn = true,
                        userBalanceExist = false
                    )
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

    private suspend fun userBalanceIsExist(): Boolean {
        return brofinRepository.userBalanceExists(getCurrentMonthAndYearAsLong())
    }
}


data class SplashUiState(
    val isUserLoggedIn: Boolean? = null,
    val userBalanceExist: Boolean? = null,
    val errorMessage: String? = null
)
