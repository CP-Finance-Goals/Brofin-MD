package com.example.brofin.presentation.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    val userBalance: Flow<Double?> = userIdFlow.flatMapLatest { userId ->
        if (userId != null) {
            brofinRepository.getUserCurrentBalance(userId, getCurrentMonthAndYearAsLong())
                .onStart { emit(0.0) }
                .catch { emit(0.0) }
        } else {
            flowOf(0.0)
        }
    }

    // Flow lainnya
    val budgetingDiaries = brofinRepository.getAllBudgetingDiaryEntries()
        .onStart { emit(emptyList()) }
        .catch { emit(emptyList()) }

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

    val totalExpenses = brofinRepository.getTotalExpenses()
        .onStart { emit(0.0) }
        .catch { emit(0.0) }
}
