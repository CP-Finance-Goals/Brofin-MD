package com.example.brofin.domain.contract.usecase.financial.goals

interface DeleteFinancialGoalUseCase {
    suspend operator fun invoke(goalId: Int)
}