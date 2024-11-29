package com.example.brofin.presentation.authentication

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brofin.domain.repository.RemoteDataRepository
import com.example.brofin.domain.repository.datastore.UserPreferencesRepository
import com.example.brofin.presentation.authentication.state.AuthState
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
) : ViewModel() {
    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    fun loginWithEmail(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                val response = remoteDataRepository.login(email, password)

                if (response.token != null) {
                    userPreferencesRepository.updateToken(response.token)
                    _authState.value = AuthState.Success
                } else {
                    _authState.value = AuthState.Error("Login gagal, silakan coba lagi.")
                }
            } catch (e: IllegalArgumentException) {
                Log.e(TAG, "loginWithEmail: ${e.message}")
                _authState.value = AuthState.Error("Data tidak valid")
            } catch (e: Exception) {
                Log.e(TAG, "loginWithEmail: ${e.message}")
                _authState.value = AuthState.Error("Terjadi kesalahan saat login")
            }
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
            } catch (e: IllegalArgumentException) {
                Log.e(TAG, "registerWithEmail: ${e.message}")
                _authState.value = AuthState.Error("Data tidak valid")
            } catch (e: Exception) {
                Log.e(TAG, "registerWithEmail: ${e.message}")
                _authState.value = AuthState.Error("Terjadi kesalahan saat registrasi")
            }
        }
    }

    companion object{
        private const val TAG = "AuthViewModel"
    }

}
