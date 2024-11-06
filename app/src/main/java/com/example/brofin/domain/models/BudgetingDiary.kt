package com.example.brofin.domain.models


data class BudgetingDiary(
    val entryId: Int = 0,
    val date: Long,
    val description: String,
    val amount: Double,
    val isExpense: Boolean
)