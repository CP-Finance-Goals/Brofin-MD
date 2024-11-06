package com.example.brofin.domain.contract.usecase.budgeting.diary

interface DeleteBudgetingDiaryEntryUseCase {
    suspend operator fun invoke(entryId: Int)
}