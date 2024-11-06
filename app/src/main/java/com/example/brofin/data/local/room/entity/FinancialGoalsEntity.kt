package com.example.brofin.data.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "financial_goals")
data class FinancialGoalsEntity(
    @PrimaryKey(autoGenerate = true) val goalId: Int = 0,
    val goalName: String,
    val targetAmount: Double,
    val currentAmount: Double = 0.0,
    val deadline: Long?,
    val createdAt: Long
)