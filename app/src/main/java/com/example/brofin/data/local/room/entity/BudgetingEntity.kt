package com.example.brofin.data.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "budgeting")
data class BudgetingEntity (
    @PrimaryKey
    val monthAndYear: Long,
    val userId: String,
    val total: Double,
    val essentialNeedsLimit: Double, // limit 50%
    val wantsLimit: Double, // limit 30%
    val savingsLimit: Double, // limit 20%
    val isReminder: Boolean = false // reminder for budgeting
)