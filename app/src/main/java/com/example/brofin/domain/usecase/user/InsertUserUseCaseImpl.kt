package com.example.brofin.domain.usecase.user

import com.example.brofin.contract.repository.BrofinRepository
import com.example.brofin.contract.usecase.user.InsertUserUseCase
import com.example.brofin.domain.models.User
import javax.inject.Inject

class InsertUserUseCaseImpl @Inject constructor(
    private val brofinRepository: BrofinRepository
): InsertUserUseCase {
    override suspend fun invoke(user: User) {
        brofinRepository.insertUser(user)
    }
}