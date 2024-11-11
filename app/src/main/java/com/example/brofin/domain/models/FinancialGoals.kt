package com.example.brofin.domain.models

data class FinancialGoals (
    val id: Int = 0,
    val userId: String = "",
    val photoUri: String = "",
    val goalName: String,
    val targetAmount: Double,
    val deadline: Long,
    val createdAt: Long
)