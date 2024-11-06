package com.example.brofin.presentation.test

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brofin.contract.usecase.user.DeleteUserUseCase
import com.example.brofin.contract.usecase.user.GetCurrentBalanceUseCase
import com.example.brofin.contract.usecase.user.GetUserUseCase
import com.example.brofin.contract.usecase.user.InsertUserUseCase
import com.example.brofin.contract.usecase.user.UpdateUserUseCase
import com.example.brofin.domain.models.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val insertUserUseCase: InsertUserUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
    private val getCurrentBalanceUseCase: GetCurrentBalanceUseCase,
    private val updateUserUseCase: UpdateUserUseCase
): ViewModel() {


    private val _userState = MutableStateFlow<User?>(null)
    val userState: StateFlow<User?> = _userState.asStateFlow()



    init {
        getUser()
    }

    private fun getUser() {
        viewModelScope.launch {
            getUserUseCase().collect { user ->
                _userState.value = user
            }
        }
    }

    fun insertUser(user: User) {
        viewModelScope.launch {
            insertUserUseCase(user)
        }
    }

    fun deleteUser() {
        viewModelScope.launch {
            deleteUserUseCase()
        }
    }

    fun updateUser(user: User) {
        viewModelScope.launch {
            updateUserUseCase(user)
        }
    }


}