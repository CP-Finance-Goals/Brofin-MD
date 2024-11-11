package com.example.brofin.data.mapper

import com.example.brofin.data.local.room.entity.BudgetingDiaryEntity
import com.example.brofin.data.local.room.entity.FinancialGoalsEntity
import com.example.brofin.data.local.room.entity.UserBalanceEntity
import com.example.brofin.domain.models.BudgetingDiary
import com.example.brofin.domain.models.FinancialGoals
import com.example.brofin.domain.models.UserBalance

// To Domain
fun UserBalanceEntity.toUser() = UserBalance(
    userId = userId,
    currentBalance = currentBalance,
    lastUpdated = lastUpdated
)

fun BudgetingDiaryEntity.toBudgetingDiary() = BudgetingDiary(
    id = id,
    userId = userId,
    date = date,
    description = description,
    amount = amount,
    isExpense = isExpense
)

fun FinancialGoalsEntity.toFinancialGoals() = FinancialGoals(
    id = id,
    userId = userId,
    photoUri = photoUri,
    goalName = goalName,
    targetAmount = targetAmount,
    deadline = deadline,
    createdAt = createdAt
)


// To Entity
fun UserBalance.toUserBalanceEntity() = UserBalanceEntity(
    userId = userId,
    currentBalance = currentBalance,
    lastUpdated = lastUpdated
)

fun BudgetingDiary.toBudgetingDiaryEntity() = BudgetingDiaryEntity(
    userId = userId,
    date = date,
    description = description,
    amount = amount,
    isExpense = isExpense
)

fun FinancialGoals.toFinancialGoalsEntity() = FinancialGoalsEntity(
    userId = userId,
    photoUri = photoUri,
    goalName = goalName,
    targetAmount = targetAmount,
    deadline = deadline,
    createdAt = createdAt
)
