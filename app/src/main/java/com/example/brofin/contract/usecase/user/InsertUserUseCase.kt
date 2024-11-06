package com.example.brofin.contract.usecase.user

import com.example.brofin.domain.models.User

interface InsertUserUseCase {
    suspend operator fun invoke(user: User)
}