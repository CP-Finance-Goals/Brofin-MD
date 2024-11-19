package com.example.brofin.data.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "budgeting")
data class BudgetingEntity (
    @PrimaryKey
    val monthAndYear: Long,
    val userId: String,
    val total: Double,
    val essentialNeedsLimit: Double, // Limit for essential needs (50%)
    val wantsLimit: Double, // Limit for wants (30%)
    val savingsLimit: Double, // Limit for savings (20%)
    val isReminder: Boolean = false // reminder jika limit sudah mencapai 80%
)