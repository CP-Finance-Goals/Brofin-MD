package com.example.brofin.presentation.authentication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brofin.domain.repository.AuthRepository
import com.example.brofin.presentation.authentication.state.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
//    private val firestoreRepository: FirestoreRepository
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState = _authState.asStateFlow()

    fun signInWithEmail(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            authRepository.signInWithEmail(email, password)
                .catch { e ->
                    _authState.value = AuthState.Error(e.message ?: "Terjadi kesalahan")
                }
                .collect { isSuccess ->
                    _authState.value = if (isSuccess) AuthState.Success else AuthState.Error("Login gagal")
                }
        }
    }


    fun registerWithEmail(name: String, email: String, password: String) {
        viewModelScope.launch {
            _authState.emit(AuthState.Loading)
            authRepository.registerWithEmail(name = name, email = email,password = password)
                .catch { e ->
                    _authState.emit(AuthState.Error(e.message ?: "Terjadi kesalahan"))
                }
                .collect { isSuccess ->
                    _authState.emit(if (isSuccess) AuthState.Success else AuthState.Error("Registrasi gagal"))
                }
        }
    }
}
