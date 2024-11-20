package com.example.brofin.presentation.main.budget

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brofin.data.local.room.entity.BudgetWithDiaries
import com.example.brofin.domain.repository.AuthRepository
import com.example.brofin.domain.repository.BrofinRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class BudgetViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val brofinRepository: BrofinRepository
): ViewModel() {

    private val userIdFlow = flow {
        val currentUser = authRepository.getCurrentUser()
        emit(currentUser?.uid)
    }.stateIn(viewModelScope, SharingStarted.Lazily, null)

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getBudgetingDiariesByMonthAndYear(monthAndYear: Long): Flow<BudgetWithDiaries?> {
        return userIdFlow.flatMapLatest { userId ->
            if (userId != null) {
                brofinRepository.getBudgetWithDiaries(monthAndYear, userId)
            } else {
                flowOf(null)
            }
        }
    }


}