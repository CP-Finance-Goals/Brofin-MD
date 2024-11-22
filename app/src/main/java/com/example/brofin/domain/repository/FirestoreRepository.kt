package com.example.brofin.domain.repository

import com.example.brofin.domain.models.Budgeting
import com.example.brofin.domain.models.BudgetingDiary
import com.example.brofin.domain.models.UserBalance


interface FirestoreRepository {

    // BudgetingDiaryEntity
    suspend fun addBudgetingDiary(diary: BudgetingDiary): Result<Void?>
    suspend fun getBudgetingDiaries(userId: String, monthAndYear: Long): Result<List<BudgetingDiary>>
    suspend fun updateBudgetingDiary(id: Int, updates: Map<String, Any>): Result<Void?>
    suspend fun deleteBudgetingDiary(id: Int): Result<Void?>

    // UserBalanceEntity
    suspend fun addUserBalance(userBalance: UserBalance): Result<Void?>
    suspend fun getUserBalance(userId: String, monthAndYear: Long): Result<UserBalance?>
    suspend fun updateUserBalance(userId: String, monthAndYear: Long, updates: Map<String, Any>): Result<Void?>
    suspend fun deleteUserBalance(userId: String, monthAndYear: Long): Result<Void?>

    // BudgetingEntity
    suspend fun addBudgeting(budget: Budgeting): Result<Void?>
    suspend fun getBudgeting(userId: String, monthAndYear: Long): Result<Budgeting?>
    suspend fun updateBudgeting(monthAndYear: Long, userId: String, updates: Map<String, Any>): Result<Void?>
    suspend fun deleteBudgeting(monthAndYear: Long, userId: String): Result<Void?>
}
