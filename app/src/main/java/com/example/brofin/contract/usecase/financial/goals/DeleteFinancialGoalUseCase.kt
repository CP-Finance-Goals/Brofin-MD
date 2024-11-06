package com.example.brofin.contract.usecase.financial.goals

interface DeleteFinancialGoalUseCase {
    suspend operator fun invoke(goalId: Int)
}