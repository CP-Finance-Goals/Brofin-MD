package com.example.brofin.domain.usecase.user

import com.example.brofin.contract.repository.BrofinRepository
import com.example.brofin.contract.usecase.user.UpdateUserUseCase
import com.example.brofin.domain.models.User
import javax.inject.Inject

class UpdateUserUseCaseImpl @Inject constructor(
    private val brofinRepository: BrofinRepository
): UpdateUserUseCase {
    override suspend fun invoke(user: User) {
        brofinRepository.updateUser(user)
    }
}