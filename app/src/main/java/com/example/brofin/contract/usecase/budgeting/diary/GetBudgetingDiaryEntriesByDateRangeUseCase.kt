package com.example.brofin.contract.usecase.budgeting.diary

import com.example.brofin.domain.models.BudgetingDiary
import kotlinx.coroutines.flow.Flow

interface GetBudgetingDiaryEntriesByDateRangeUseCase {
    suspend operator fun invoke(startDate: Long, endDate: Long): Flow<List<BudgetingDiary?>>
}