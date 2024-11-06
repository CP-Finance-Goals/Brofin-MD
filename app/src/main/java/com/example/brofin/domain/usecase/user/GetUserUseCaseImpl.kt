package com.example.brofin.domain.usecase.user

import com.example.brofin.contract.repository.BrofinRepository
import com.example.brofin.contract.usecase.user.GetUserUseCase
import com.example.brofin.domain.models.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserUseCaseImpl @Inject constructor(
    private val brofinRepository: BrofinRepository
): GetUserUseCase {
    override suspend fun invoke(): Flow<User?> = brofinRepository.getUser()
}