package com.example.brofin.presentation.authentication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brofin.domain.repository.AuthRepository
import com.example.brofin.domain.repository.BrofinRepository
import com.example.brofin.presentation.authentication.state.AuthState
import com.example.brofin.utils.getCurrentMonthAndYearAsLong
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

    @HiltViewModel
    class AuthViewModel @Inject constructor(
        private val authRepository: AuthRepository,
        private val brofinRepository: BrofinRepository
    ) : ViewModel() {

        private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
        val authState: StateFlow<AuthState> = _authState.asStateFlow()

        private val _userBalanceExist = MutableStateFlow<Boolean?>(null)
        val userBalanceExist = _userBalanceExist.asStateFlow()

        fun loginWithEmail(email: String, password: String) {
            viewModelScope.launch {
                _authState.value = AuthState.Loading
                try {
                    val isSuccess = authRepository.signInWithEmail(email, password).firstOrNull()
                    if (isSuccess == true) { // Pastikan isSuccess bukan null
                        val userId = authRepository.getCurrentUser()?.uid ?: throw IllegalStateException("User id tidak ditemukan")
                        val balanceExists = userBalanceIsExist(userId)
                        _userBalanceExist.value = balanceExists
                        _authState.value = if (balanceExists) {
                            AuthState.Success
                        } else {
                            AuthState.SetupIncome
                        }
                    } else {
                        _authState.value = AuthState.Error("Login gagal")
                    }
                } catch (e: IllegalArgumentException) {
                    _authState.value = AuthState.Error(e.message)
                } catch (e: Exception) {
                    _authState.value = AuthState.Error(e.message ?: "Terjadi kesalahan saat login")
                }
            }
        }

        private suspend fun userBalanceIsExist(userId: String): Boolean {
            return brofinRepository.getUserCurrentBalance(userId, getCurrentMonthAndYearAsLong()).firstOrNull() != null
        }

        fun registerWithEmail(name: String, email: String, password: String) {
            viewModelScope.launch {
                _authState.value = AuthState.Loading
                try {
                    val isSuccess = authRepository.registerWithEmail(name, email, password).firstOrNull()
                    if (isSuccess == true) { // Pastikan isSuccess bukan null
                        val userId = authRepository.getCurrentUser()?.uid ?: throw IllegalStateException("User id tidak ditemukan")
                        val balanceExists = userBalanceIsExist(userId)
                        _userBalanceExist.value = balanceExists
                        _authState.value = if (balanceExists) {
                            AuthState.Success
                        } else {
                            AuthState.SetupIncome
                        }
                    } else {
                        _authState.value = AuthState.Error("Registrasi gagal")
                    }
                } catch (e: IllegalArgumentException) {
                    _authState.value = AuthState.Error(e.message)
                } catch (e: Exception) {
                    _authState.value = AuthState.Error(e.message ?: "Terjadi kesalahan saat registrasi")
                }
            }
        }


        fun loginWithGoogle(idToken: String) {
            viewModelScope.launch {
                _authState.value = AuthState.Loading
                try {
                    val isSuccess = authRepository.signInWithGoogle(idToken).first()
                    _authState.value = if (isSuccess) AuthState.Success else AuthState.Error("Login dengan Google gagal")
                } catch (e: Exception) {
                    _authState.value = AuthState.Error(e.message ?: "Terjadi kesalahan saat login dengan Google")
                }
            }
        }
    }
