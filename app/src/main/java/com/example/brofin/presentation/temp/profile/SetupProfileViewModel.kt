package com.example.brofin.presentation.temp.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brofin.domain.repository.AuthRepository
import com.example.brofin.presentation.authentication.state.AuthState
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SetupProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
): ViewModel() {

    private val _firebaseUser = MutableStateFlow<FirebaseUser?>(null)
    private val _name = MutableStateFlow("")
    val name = _name.asStateFlow()
    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()
    private val _profilePicture = MutableStateFlow<String?>(null)
    val profilePicture = _profilePicture.asStateFlow()

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authStateFlow = _authState.asStateFlow()

    fun getCurrentUser(userExist: (Boolean) -> Unit) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val data = authRepository.getCurrentUser()
            if (data != null) {
                _firebaseUser.value = data
                _name.value = data.displayName ?: ""
                _email.value = data.email ?: ""
                _profilePicture.value = data.photoUrl?.toString()
                _authState.value = AuthState.Success
                userExist(true)
            } else {
                _authState.value = AuthState.Error("User not found")
                userExist(false)
            }
        }
    }

//    fun saveUser(phoneNumber: String, name: String, isSuccess: (Boolean) -> Unit) {
//        viewModelScope.launch {
//            val user = UserBalanceDoc(
//                userId = _firebaseUser.value?.uid ?: "",
//                name = name,
//                email = _email.value,
//                phoneNumber = phoneNumber,
//                profilePicture = _profilePicture.value,
//                createdAt = System.currentTimeMillis()
//            )
//            val result = firestoreRepository.saveUser(user)
//            if (result) {
//                isSuccess(true)
//            } else {
//                isSuccess(false)
//            }
//        }
//    }
}