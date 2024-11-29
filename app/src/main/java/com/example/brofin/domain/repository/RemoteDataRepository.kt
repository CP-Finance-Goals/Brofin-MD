package com.example.brofin.domain.repository

import com.example.brofin.data.remote.dto.LoginResponseDto
import com.example.brofin.data.remote.dto.RegisterResponseDto

interface RemoteDataRepository {

    // register
    suspend fun register(email: String, password: String): RegisterResponseDto

    // Login a user
    suspend fun login(email: String, password: String): LoginResponseDto
}