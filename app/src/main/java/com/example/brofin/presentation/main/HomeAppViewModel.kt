package com.example.brofin.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brofin.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeAppViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {

    private val _isUserLoggedIn = authRepository.userExists()
    val isUserLoggedIn = _isUserLoggedIn

    fun logout() {
       viewModelScope.launch{
           authRepository.logout()
       }
    }
}