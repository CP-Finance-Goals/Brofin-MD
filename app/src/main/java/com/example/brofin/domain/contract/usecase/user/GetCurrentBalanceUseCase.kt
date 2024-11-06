package com.example.brofin.domain.contract.usecase.user

import com.example.brofin.domain.models.User
import kotlinx.coroutines.flow.Flow

interface GetCurrentBalanceUseCase {
    suspend operator fun invoke():  Flow<Double?>
}