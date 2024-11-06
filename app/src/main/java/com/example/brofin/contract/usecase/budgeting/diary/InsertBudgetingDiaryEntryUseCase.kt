package com.example.brofin.contract.usecase.budgeting.diary

import com.example.brofin.domain.models.BudgetingDiary

interface InsertBudgetingDiaryEntryUseCase {
    suspend operator fun invoke(entry: BudgetingDiary)
}