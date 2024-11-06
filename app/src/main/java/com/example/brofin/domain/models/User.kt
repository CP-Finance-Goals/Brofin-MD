package com.example.brofin.domain.models

data class User (
    val userId: Int = 0,
    val name: String,
    val email: String,
    val phoneNumber: String?,
    val createdAt: Long,
    val currentBalance: Double = 0.0
)