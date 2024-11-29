package com.example.brofin.presentation.main

import androidx.lifecycle.ViewModel
import com.example.brofin.domain.repository.AuthRepository
import com.example.brofin.domain.repository.datastore.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class HomeAppViewModel @Inject constructor(
    userPreferencesRepository: UserPreferencesRepository
): ViewModel() {

    private val _isUserLoggedIn = userPreferencesRepository.userPreferencesFlow.map { it.token != null }
    val isUserLoggedIn = _isUserLoggedIn

}