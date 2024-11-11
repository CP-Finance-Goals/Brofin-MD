package com.example.brofin.domain.models.doc

data class UserBalanceDoc(
    val userId: String = "",
    val currentBalance: Double = 0.0,
    val savingsGoal: Double = 0.0,
    val lastUpdated: Long = System.currentTimeMillis()
)
