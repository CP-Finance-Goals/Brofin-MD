package com.example.brofin.domain.models

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
data class Budgeting (
    val monthAndYear: Long,
    val userId: String,
    val total: Double,
    val essentialNeedsLimit: Double, // Limit for essential needs (50%)
    val wantsLimit: Double, // Limit for wants (30%)
    val savingsLimit: Double, // Limit for savings (20%)
    val isReminder: Boolean = false // reminder jika limit sudah mencapai 80%
): Parcelable
