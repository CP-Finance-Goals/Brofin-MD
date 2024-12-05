package com.example.brofin.domain.models

data class UserProfile (
    val id: Int = 0,
    val createdAt: String? = null,
    val photoUrl: String? = null,
    val gender: String? = null,
    val dob: String? = null,
    val name: String? = null,
    val savings: Double? = null,
    val email: String? = null,
)