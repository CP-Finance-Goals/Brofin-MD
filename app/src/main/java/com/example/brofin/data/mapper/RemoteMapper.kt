package com.example.brofin.data.mapper

import com.example.brofin.data.remote.dto.BudgetingDiariesItem
import com.example.brofin.data.remote.dto.BudgetingsItem
import com.example.brofin.data.remote.dto.UserBalanceItem
import com.example.brofin.data.remote.dto.UserProfileItem
import com.example.brofin.domain.models.Budgeting
import com.example.brofin.domain.models.BudgetingDiary
import com.example.brofin.domain.models.UserBalance
import com.example.brofin.domain.models.UserProfile

fun UserProfileItem.toUserProfile(): UserProfile {
    return UserProfile(
        id = userId,
        name = name,
        email = email,
        savings = savings,
        photoUrl = photoUrl,
    )
}

fun UserBalanceItem.toUserBalance(): UserBalance {
    return UserBalance(
        balance = balance,
        currentBalance = currentBalance,
        monthAndYear = monthAndYear,
    )
}

fun BudgetingsItem.toBudgeting(): Budgeting {
    return Budgeting(
        total = total,
        essentialNeedsLimit = essentialNeedsLimit,
        savingsLimit = savingsLimit,
        monthAndYear = monthAndYear,
        wantsLimit = wantsLimit,
    )
}

fun BudgetingDiariesItem.toBudgeting(): BudgetingDiary {
    return BudgetingDiary(
        id = id,
        date = date,
        photoUrl = photoUrl,
        description = description,
        amount = amount,
        categoryId = categoryId,
        monthAndYear = monthAndYear,
    )
}