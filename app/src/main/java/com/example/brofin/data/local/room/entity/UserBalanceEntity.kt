package com.example.brofin.data.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_balance")
data class UserBalanceEntity(
    @PrimaryKey
    val userId: String,
    val currentBalance: Double = 0.0,
    val lastUpdated: Long = System.currentTimeMillis()
)

