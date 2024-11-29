package com.example.brofin.presentation.main.budget

import androidx.lifecycle.ViewModel
import com.example.brofin.data.local.room.entity.BudgetingWithDiaries
import com.example.brofin.domain.repository.BrofinRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class BudgetViewModel @Inject constructor(
    private val brofinRepository: BrofinRepository
): ViewModel() {

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getBudgetingDiariesByMonthAndYear(monthAndYear: Long): Flow<BudgetingWithDiaries?> {
        return brofinRepository.getBudgetWithDiaries(monthAndYear)

    }
}