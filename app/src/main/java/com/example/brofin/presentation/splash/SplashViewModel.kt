package com.example.brofin.presentation.splash

import androidx.lifecycle.ViewModel
import com.example.brofin.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class SplashViewModel @Inject constructor(
    authRepository: AuthRepository
) : ViewModel() {

    private val _isUserLoggedIn = authRepository.userExists()
    val isUserLoggedIn = _isUserLoggedIn

}