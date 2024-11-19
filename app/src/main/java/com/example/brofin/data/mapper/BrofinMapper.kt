package com.example.brofin.data.mapper

import com.example.brofin.data.local.room.entity.BudgetingDiaryEntity
import com.example.brofin.data.local.room.entity.BudgetingEntity
import com.example.brofin.data.local.room.entity.FinancialGoalsEntity
import com.example.brofin.data.local.room.entity.UserBalanceEntity
import com.example.brofin.domain.models.Budgeting
import com.example.brofin.domain.models.BudgetingDiary
import com.example.brofin.domain.models.FinancialGoals
import com.example.brofin.domain.models.UserBalance

// To Domain
fun UserBalanceEntity.toUser() = UserBalance(
    monthAndYear = monthAndYear,
    userId = userId,
    balance = balance,
    currentBalance = currentBalance,
)

fun BudgetingEntity.toBudgetingDiary() = Budgeting(
    monthAndYear = monthAndYear,
    userId = userId,
    total = total,
    essentialNeedsLimit = essentialNeedsLimit,
    wantsLimit = wantsLimit,
    savingsLimit = savingsLimit,
    isReminder = isReminder
)

fun BudgetingDiaryEntity.toBudgetingDiary() = BudgetingDiary(
    id = id,
    userId = userId,
    date = date,
    description = description,
    amount = amount,
    isExpense = isExpense,
    photoUri = "",
    categoryId = 1,
    monthAndYear = monthAndYear
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
    monthAndYear = monthAndYear,
    balance = balance
)

fun Budgeting.toBudgetingEntity() = BudgetingEntity(
    monthAndYear = monthAndYear,
    userId = userId,
    total = total,
    essentialNeedsLimit = essentialNeedsLimit,
    wantsLimit = wantsLimit,
    savingsLimit = savingsLimit,
    isReminder = isReminder
)

fun BudgetingDiary.toBudgetingDiaryEntity() = BudgetingDiaryEntity(
    userId = userId,
    date = date,
    description = description,
    amount = amount,
    isExpense = isExpense,
    photoUri = photoUri,
    categoryId = categoryId,
    monthAndYear = monthAndYear
)

fun FinancialGoals.toFinancialGoalsEntity() = FinancialGoalsEntity(
    userId = userId,
    photoUri = photoUri,
    goalName = goalName,
    targetAmount = targetAmount,
    deadline = deadline,
    createdAt = createdAt
)
