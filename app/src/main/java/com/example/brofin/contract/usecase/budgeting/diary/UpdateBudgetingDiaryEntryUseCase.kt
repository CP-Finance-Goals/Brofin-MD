package com.example.brofin.contract.usecase.budgeting.diary

import com.example.brofin.domain.models.BudgetingDiary

interface UpdateBudgetingDiaryEntryUseCase {
    suspend operator fun invoke(entry: BudgetingDiary)
}