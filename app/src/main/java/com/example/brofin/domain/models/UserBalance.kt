package com.example.brofin.domain.models

data class UserBalance (
    val monthAndYear: Long,
    val userId: String = "",
    val balance: Double?,
    val currentBalance: Double?,
    val savings: Double?
)