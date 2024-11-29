package com.example.brofin.domain.models

import kotlinx.serialization.Serializable


@Serializable
data class BudgetingDiary(
    val id: Int = 0,
    val date: Long,
    val photoUri: String? = null,
    val photoUrl: String? = null,
    val description: String? = null,
    val amount: Double,
    val categoryId: Int,
    val monthAndYear: Long,
)