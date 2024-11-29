package com.example.brofin.data.local.room.entity

import androidx.room.Entity

@Entity(
    tableName = "user_balance",
    primaryKeys = ["monthAndYear"]
)
data class UserBalanceEntity(
    val monthAndYear: Long,
    val balance: Double? = null,
    val currentBalance: Double? = null,
)
