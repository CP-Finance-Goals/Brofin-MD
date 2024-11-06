package com.example.brofin.domain.contract.usecase.budgeting.diary

import kotlinx.coroutines.flow.Flow

interface GetTotalIncomeUseCase {
    suspend operator fun invoke(): Flow<Double?>
}