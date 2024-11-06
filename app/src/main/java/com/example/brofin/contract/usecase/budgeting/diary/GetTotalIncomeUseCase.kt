package com.example.brofin.contract.usecase.budgeting.diary

import kotlinx.coroutines.flow.Flow

interface GetTotalIncomeUseCase {
    suspend operator fun invoke(): Flow<Double?>
}