package com.example.brofin.presentation.authentication

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brofin.data.repository.FirestoreRepositoryImpl
import com.example.brofin.domain.repository.AuthRepository
import com.example.brofin.domain.repository.FirestoreRepository
import com.example.brofin.presentation.authentication.state.AuthState
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val firestoreRepository: FirestoreRepository
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


    fun registerWithEmail(email: String, password: String) {
        viewModelScope.launch {
            _authState.emit(AuthState.Loading)
            authRepository.registerWithEmail(email, password)
                .catch { e ->
                    _authState.emit(AuthState.Error(e.message ?: "Terjadi kesalahan"))
                }
                .collect { isSuccess ->
                    _authState.emit(if (isSuccess) AuthState.Success else AuthState.Error("Registrasi gagal"))
                }
        }
    }
}
