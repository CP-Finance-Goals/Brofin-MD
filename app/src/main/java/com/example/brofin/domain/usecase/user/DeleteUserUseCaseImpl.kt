package com.example.brofin.domain.usecase.user

import com.example.brofin.contract.repository.BrofinRepository
import com.example.brofin.contract.usecase.user.DeleteUserUseCase
import javax.inject.Inject

class DeleteUserUseCaseImpl @Inject constructor(
    private val brofinRepository: BrofinRepository
): DeleteUserUseCase {
    override suspend fun invoke() {
        brofinRepository.deleteUser()
    }
}