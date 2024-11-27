package com.example.brofin.domain.models

data class UserProfile (
    val id: Int = 0,
    val userId: String,
    val name: String?,
    val email: String?,
    val photoUrl: String?,
    val savings: Double,
)