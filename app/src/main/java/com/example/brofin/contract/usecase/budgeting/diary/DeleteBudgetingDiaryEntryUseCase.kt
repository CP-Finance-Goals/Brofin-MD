package com.example.brofin.contract.usecase.budgeting.diary

interface DeleteBudgetingDiaryEntryUseCase {
    suspend operator fun invoke(entryId: Int)
}