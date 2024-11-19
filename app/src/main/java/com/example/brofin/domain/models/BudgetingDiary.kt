package com.example.brofin.domain.models


data class BudgetingDiary(
    val id: Int = 0,
    val userId: String,
    val date: Long,
    val photoUri: String? = null,
    val description: String? = null,
    val amount: Double,
    val categoryId: Int,
    val isExpense: Boolean = true,
    val monthAndYear: Long,
)