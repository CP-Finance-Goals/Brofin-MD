package com.example.brofin.domain.models

import kotlinx.serialization.Serializable


@Serializable
data class BudgetingDiary(
    val id: Long = 0,
    val date: Long,
    val photoUrl: String? = null,
    val description: String? = null,
    val amount: Double,
    val categoryId: Int,
    val monthAndYear: Long,
)