package com.example.brofin.domain.models.doc

data class BudgetingDiaryDoc(
    val userId: String = "",
    val entryId: String = "",
    val date: Long,
    val description: String,
    val amount: Double,
    val isExpense: Boolean
)
