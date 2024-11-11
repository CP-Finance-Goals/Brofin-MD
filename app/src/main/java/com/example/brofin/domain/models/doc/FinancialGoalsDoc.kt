package com.example.brofin.domain.models.doc

data class FinancialGoalsDoc(
    val userId: String = "",
    val id: Int = 0,
    val photoUrl: String = "",
    val goalName: String,
    val targetAmount: Double,
    val deadline: Long,
    val createdAt: Long = System.currentTimeMillis()
)
