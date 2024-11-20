package com.example.brofin.data.local.room.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "budgeting_diary",
    foreignKeys = [
        ForeignKey(
            entity = BudgetingEntity::class,
            parentColumns = ["monthAndYear"],
            childColumns = ["monthAndYear"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["monthAndYear"])]
)

data class BudgetingDiaryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: String,
    val monthAndYear: Long,
    val date: Long,
    val photoUri: String? = null,
    val description: String? = null,
    val amount: Double,
    val categoryId: Int,
    val isExpense: Boolean = true
)
