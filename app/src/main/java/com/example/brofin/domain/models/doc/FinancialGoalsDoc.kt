package com.example.brofin.domain.models.doc

data class FinancialGoalsDoc(
    val userId: String = "",
    val goalId: String = "",
    val goalName: String,
    val targetAmount: Double,
    val currentAmount: Double = 0.0,
    val deadline: Long,
    val createdAt: Long
)
