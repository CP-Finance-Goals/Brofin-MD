package com.example.brofin.domain.contract.usecase.user

import com.example.brofin.domain.models.User

interface InsertUserUseCase {
    suspend operator fun invoke(user: User)
}