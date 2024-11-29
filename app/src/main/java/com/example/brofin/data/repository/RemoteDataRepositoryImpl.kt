package com.example.brofin.data.repository

import com.example.brofin.data.remote.ApiService
import com.example.brofin.data.remote.dto.LoginResponseDto
import com.example.brofin.data.remote.dto.RegisterResponseDto
import com.example.brofin.domain.repository.RemoteDataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteDataRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
): RemoteDataRepository {

    override suspend fun register(
        email: String,
        password: String
    ): RegisterResponseDto {
        return withContext(Dispatchers.Default) {
            try {
                apiService.register(email, password)
            } catch (e: Exception){
                throw e
            }
        }
    }


    override suspend fun login(
        email: String,
        password: String
    ): LoginResponseDto {
        return withContext(Dispatchers.Default){
            try {
                apiService.login(email, password)
            } catch (e: Exception) {
                throw e
            }
        }
    }
}