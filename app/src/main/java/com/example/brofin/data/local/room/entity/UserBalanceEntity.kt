package com.example.brofin.data.local.room.entity

import androidx.room.Entity

@Entity(
    tableName = "user_balance",
    primaryKeys = ["monthAndYear", "userId"]
)
data class UserBalanceEntity(
    val monthAndYear: Long,
    val userId: String,
    val balance: Double? = null,
    val currentBalance: Double? = null,
    val savings: Double? = null
)
