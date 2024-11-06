package com.example.brofin.domain.contract.repository.room

import com.example.brofin.domain.models.BudgetingDiary
import com.example.brofin.domain.models.FinancialGoals
import com.example.brofin.domain.models.User
import kotlinx.coroutines.flow.Flow

interface BrofinRepository {

    // User Methods
    suspend fun insertUser(user: User)
    suspend fun getUser(): Flow<User>
    suspend fun updateUser(user: User)
    suspend fun deleteUser()

    // Financial Goals Methods
    suspend fun insertFinancialGoal(goal: FinancialGoals)
    suspend fun getAllFinancialGoals(): Flow<List<FinancialGoals>> // Menggunakan Flow untuk mendapatkan daftar tujuan keuangan
    suspend fun updateFinancialGoal(goal: FinancialGoals)
    suspend fun deleteFinancialGoal(goalId: Int)

    // Budgeting Diary Methods
    suspend fun insertBudgetingDiaryEntry(entry: BudgetingDiary)
    suspend fun getAllBudgetingDiaryEntries(): Flow<List<BudgetingDiary>> // Menggunakan Flow untuk mendapatkan diary keuangan
    suspend fun updateBudgetingDiaryEntry(entry: BudgetingDiary)
    suspend fun deleteBudgetingDiaryEntry(entryId: Int)
}