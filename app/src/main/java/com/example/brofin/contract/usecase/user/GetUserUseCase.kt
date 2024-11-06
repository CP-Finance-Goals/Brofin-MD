package com.example.brofin.contract.usecase.user

import com.example.brofin.domain.models.User
import kotlinx.coroutines.flow.Flow

interface GetUserUseCase {
    suspend operator fun invoke(): Flow<User?>
}