package com.example.brofin.presentation.test

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brofin.contract.usecase.user.DeleteUserUseCase
import com.example.brofin.contract.usecase.user.GetCurrentBalanceUseCase
import com.example.brofin.contract.usecase.user.GetUserUseCase
import com.example.brofin.contract.usecase.user.InsertUserUseCase
import com.example.brofin.contract.usecase.user.UpdateUserUseCase
import com.example.brofin.domain.models.User
import com.example.brofin.utils.UserAlreadyExistsException
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

    private val _userInsertStatus = MutableStateFlow(Result.success(Unit))
    val userInsertStatus: StateFlow<Result<Unit>> = _userInsertStatus.asStateFlow()

    private val _userUpdateStatus = MutableStateFlow(Result.success(Unit))
    val userUpdateStatus: StateFlow<Result<Unit>> = _userUpdateStatus.asStateFlow()

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
            try {
                insertUserUseCase(user)
                _userInsertStatus.value = Result.success(Unit)
            } catch (e: UserAlreadyExistsException) {
                _userInsertStatus.value = Result.failure(e)
            } catch (e: Exception) {
                _userInsertStatus.value = Result.failure(e)
            }
        }
    }

    fun deleteUser() {
        viewModelScope.launch {
            deleteUserUseCase()
        }
    }

    fun updateUser(name: String? = null, email: String? = null, phoneNumber: String? = null, balance: Double? = null) {
        viewModelScope.launch {
            val currentUser = _userState.value

            if (currentUser != null) {
                val updatedUser = currentUser.copy(
                    name = name ?: currentUser.name,
                    email = email ?: currentUser.email,
                    phoneNumber = phoneNumber ?: currentUser.phoneNumber,
                    currentBalance = balance ?: currentUser.currentBalance
                )

                try {
                    updateUserUseCase(updatedUser)
                    _userUpdateStatus.value = Result.success(Unit)
                } catch (e: Exception) {
                    _userUpdateStatus.value = Result.failure(e)
                }
            }
        }
    }
}