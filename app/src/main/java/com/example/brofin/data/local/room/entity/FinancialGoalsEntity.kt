package com.example.brofin.data.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "financial_goals")
data class FinancialGoalsEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val photoUri: String = "",
    val userId: String = "",
    val goalName: String,
    val targetAmount: Double,
    val deadline: Long,
    val createdAt: Long
)