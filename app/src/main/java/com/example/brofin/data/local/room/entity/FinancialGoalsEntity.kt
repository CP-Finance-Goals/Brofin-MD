package com.example.brofin.data.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "financial_goals")
data class FinancialGoalsEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val date: Long,
    val description: String?,
    val targetAmount: Double,
    val createdAt: Long,
    val prediction: String?,
)
