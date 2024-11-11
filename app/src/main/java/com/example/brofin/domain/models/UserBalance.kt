package com.example.brofin.domain.models

data class UserBalance (
    val userId: String = "",
    val currentBalance: Double = 0.0,
    val lastUpdated: Long = System.currentTimeMillis()
)