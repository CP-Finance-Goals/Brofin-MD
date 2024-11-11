package com.example.brofin.domain.models


data class BudgetingDiary(
    val id: Int = 0,
    val userId: String = "",
    val date: Long,
    val description: String,
    val amount: Double,
    val isExpense: Boolean
)