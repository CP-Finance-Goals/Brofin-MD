package com.example.brofin.domain.models

data class FinancialGoals (
    val goalId: Int = 0,
    val goalName: String,
    val targetAmount: Double,
    val currentAmount: Double = 0.0,
    val deadline: Long?,
    val createdAt: Long
)