package com.example.brofin.domain.usecase.user

import com.example.brofin.contract.repository.BrofinRepository
import com.example.brofin.contract.usecase.user.InsertUserUseCase
import com.example.brofin.domain.models.User
import com.example.brofin.utils.UserAlreadyExistsException
import javax.inject.Inject

class InsertUserUseCaseImpl @Inject constructor(
    private val brofinRepository: BrofinRepository
): InsertUserUseCase {
    override suspend fun invoke(user: User) {
        val userExists = brofinRepository.isUserExists()
        if (userExists) {
            throw UserAlreadyExistsException()
        }
        brofinRepository.insertUser(user)
    }
}