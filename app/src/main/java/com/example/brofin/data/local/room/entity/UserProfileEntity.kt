package com.example.brofin.data.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "user_profile")
data class UserProfileEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userId: String,
    val name: String?,
    val email: String?,
    val photoUrl: String?,
    val savings: Double?,
)