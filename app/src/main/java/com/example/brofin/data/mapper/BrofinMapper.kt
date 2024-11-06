package com.example.brofin.data.mapper

import com.example.brofin.data.local.room.entity.BudgetingDiaryEntity
import com.example.brofin.data.local.room.entity.FinancialGoalsEntity
import com.example.brofin.data.local.room.entity.UserEntity
import com.example.brofin.domain.models.BudgetingDiary
import com.example.brofin.domain.models.FinancialGoals
import com.example.brofin.domain.models.User

// To Domain
fun UserEntity.toUser() = User(
    userId = userId,
    name = name,
    email = email,
    phoneNumber = phoneNumber,
    createdAt = createdAt,
    currentBalance = currentBalance
)

fun BudgetingDiaryEntity.toBudgetingDiary() = BudgetingDiary(
    entryId = entryId,
    date = date,
    description = description,
    amount = amount,
    isExpense = isExpense
)

fun FinancialGoalsEntity.toFinancialGoals() = FinancialGoals(
    goalId = goalId,
    goalName = goalName,
    targetAmount = targetAmount,
    currentAmount = currentAmount,
    deadline = deadline,
    createdAt = createdAt
)


// To Entity
fun User.toUserEntity() = UserEntity(
    name = name,
    email = email,
    phoneNumber = phoneNumber,
    createdAt = createdAt,
)

fun BudgetingDiary.toBudgetingDiaryEntity() = BudgetingDiaryEntity(
    date = date,
    description = description,
    amount = amount,
    isExpense = isExpense
)

fun FinancialGoals.toFinancialGoalsEntity() = FinancialGoalsEntity(
    goalName = goalName,
    targetAmount = targetAmount,
    currentAmount = currentAmount,
    deadline = deadline,
    createdAt = createdAt
)