package com.example.brofin.data.mapper

import com.example.brofin.data.local.room.entity.BudgetingDiaryEntity
import com.example.brofin.data.local.room.entity.BudgetingEntity
import com.example.brofin.data.local.room.entity.UserBalanceEntity
import com.example.brofin.data.local.room.entity.UserProfileEntity
import com.example.brofin.domain.models.Budgeting
import com.example.brofin.domain.models.BudgetingDiary
import com.example.brofin.domain.models.UserBalance
import com.example.brofin.domain.models.UserProfile

// To Domain
fun UserBalanceEntity.toUser() = UserBalance(
    monthAndYear = monthAndYear,
    balance = balance,
    currentBalance = currentBalance,
)

fun UserProfileEntity.toUserProfile() = UserProfile(
    id = id,
    name = name,
    email = email,
    photoUrl = photoUrl,
    savings = savings
)

fun BudgetingEntity.toBudgetingDiary() = Budgeting(
    monthAndYear = monthAndYear,
    total = total,
    essentialNeedsLimit = essentialNeedsLimit,
    wantsLimit = wantsLimit,
    savingsLimit = savingsLimit,
    isReminder = isReminder
)

fun BudgetingDiaryEntity.toBudgetingDiary() = BudgetingDiary(
    id = id,
    date = date,
    description = description,
    photoUrl = photoUrl,
    amount = amount,
    categoryId = categoryId,
    monthAndYear = monthAndYear
)

//fun FinancialGoalsEntity.toFinancialGoals() = FinancialGoals(
//    id = id,
//    userId = userId,
//    photoUri = photoUri,
//    goalName = goalName,
//    targetAmount = targetAmount,
//    deadline = deadline,
//    createdAt = createdAt
//)


// To Entity
fun UserBalance.toUserBalanceEntity() = UserBalanceEntity(
    currentBalance = currentBalance,
    monthAndYear = monthAndYear,
    balance = balance,
)

fun UserProfile.toUserProfileEntity() = UserProfileEntity(
    id = id,
    name = name,
    email = email,
    photoUrl = photoUrl,
    savings = savings
)

fun Budgeting.toBudgetingEntity() = BudgetingEntity(
    monthAndYear = monthAndYear,
    total = total,
    essentialNeedsLimit = essentialNeedsLimit,
    wantsLimit = wantsLimit,
    savingsLimit = savingsLimit,
    isReminder = isReminder
)

fun BudgetingDiary.toBudgetingDiaryEntity() = BudgetingDiaryEntity(
    id = id,
    date = date,
    description = description,
    photoUrl = photoUrl,
    amount = amount,
    categoryId = categoryId,
    monthAndYear = monthAndYear
)

//fun FinancialGoals.toFinancialGoalsEntity() = FinancialGoalsEntity(
//    userId = userId,
//    photoUri = photoUri,
//    goalName = goalName,
//    targetAmount = targetAmount,
//    deadline = deadline,
//    createdAt = createdAt
//)
