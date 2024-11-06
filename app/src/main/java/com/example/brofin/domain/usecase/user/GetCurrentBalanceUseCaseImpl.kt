package com.example.brofin.domain.usecase.user

import com.example.brofin.contract.repository.BrofinRepository
import com.example.brofin.contract.usecase.user.GetCurrentBalanceUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrentBalanceUseCaseImpl @Inject constructor(
    private val brofinRepository: BrofinRepository
): GetCurrentBalanceUseCase {
    override suspend fun invoke(): Flow<Double?> = brofinRepository.getCurrentBalance()
}