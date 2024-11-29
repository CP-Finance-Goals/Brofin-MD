package com.example.brofin.domain.models

data class UserBalance (
    val monthAndYear: Long,
    val balance: Double?,
    val currentBalance: Double?,
)