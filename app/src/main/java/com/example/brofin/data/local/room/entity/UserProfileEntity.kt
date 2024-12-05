package com.example.brofin.data.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profile")
data class UserProfileEntity(
    @PrimaryKey
    val id: Int = 0,
    val createdAt: String? = null,
    val photoUrl: String? = null,
    val gender: String? = null,
    val dob: String? = null,
    val name: String? = null,
    val savings: Double? = null,
    val email: String? = null,
)
