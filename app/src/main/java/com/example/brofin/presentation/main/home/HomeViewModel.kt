package com.example.brofin.presentation.main.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brofin.domain.models.Budgeting
import com.example.brofin.domain.models.UserBalance
import com.example.brofin.domain.repository.AuthRepository
import com.example.brofin.domain.repository.BrofinRepository
import com.example.brofin.utils.getCurrentMonthAndYearAsLong
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val brofinRepository: BrofinRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val userIdFlow = flow {
        val currentUser = authRepository.getCurrentUser()
        emit(currentUser?.uid) // Emit userId jika ada
    }.stateIn(viewModelScope, SharingStarted.Lazily, null)


    @OptIn(ExperimentalCoroutinesApi::class)
    val budgetingUserIsExist = userIdFlow.flatMapLatest { userId ->
        if (userId != null) {
            brofinRepository.isUserBudgetingExist(getCurrentMonthAndYearAsLong(), userId)
                .onStart { emit(false) }
                .catch { emit(false) }
        } else {
            flowOf(false)
        }
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    val userBalance: Flow<Double?> = userIdFlow.flatMapLatest { userId ->
        if (userId != null) {
            brofinRepository.getUserCurrentBalance(userId, getCurrentMonthAndYearAsLong())
                .onStart { emit(0.0) }
                .catch { emit(0.0) }
        } else {
            flowOf(0.0)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val budgetingDiaries = userIdFlow.flatMapLatest { userId ->
        if (userId != null) {
            brofinRepository.getAllBudgetingDiaryEntries(userId)
                .onStart { emit(emptyList()) }
                .catch { emit(emptyList()) }
        } else {
            flowOf(emptyList())
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val totalIncome = userIdFlow.flatMapLatest { userId ->
        if (userId != null) {
            brofinRepository.getUserBalance(userId, getCurrentMonthAndYearAsLong())
                .onStart { emit(0.0) }
                .catch { emit(0.0) }
        } else {
            flowOf(0.0)
        }
    }

    val totalExpenses = brofinRepository.getTotalExpenses(
        monthAndYear = getCurrentMonthAndYearAsLong(),
        userId = authRepository.getCurrentUser()?.uid ?: ""
    )
        .onStart { emit(0.0) }
        .catch {
            Log.e(TAG, "Error fetching total expenses", it)
            emit(0.0)
        }

    val totalSavings = brofinRepository.getTotalSavings(
        userId = authRepository.getCurrentUser()?.uid ?: ""
    )

    fun insertUserBalance(userBalance: UserBalance) {
        viewModelScope.launch {
            Log.d(TAG, "Insert User Balance: $userBalance")
            val data = userBalance.copy(
                userId = authRepository.getCurrentUser()?.uid ?: throw IllegalArgumentException("User not found"),
            )
            try {
                brofinRepository.insertUserBalance(
                    userBalance = data,
                )
                insertBudgeting(data.balance!!)
            }catch (e: Exception) {
                Log.e(TAG, "Error on saving user balance data", e)
            }
        }
    }

    private fun insertBudgeting(income: Double) {
        viewModelScope.launch {
            try {
                brofinRepository.insertBudget(
                    budget = Budgeting(
                        monthAndYear = getCurrentMonthAndYearAsLong(),
                        userId = authRepository.getCurrentUser()?.uid ?: throw IllegalArgumentException("User not found"),
                        total = income,
                        essentialNeedsLimit = income * 0.5,
                        wantsLimit = income * 0.3,
                        savingsLimit = income * 0.2,
                        isReminder = false
                    )
                )
            } catch (e: IllegalArgumentException){
                Log.e(TAG, e.message ?: "Error on input data")
            } catch (e: Exception) {
                Log.e(TAG, "Error on saving budgeting data", e)
            }
        }
    }

    companion object {
        private const val TAG = "HomeViewModel"
    }
}
