package com.example.brofin.domain.repository

import com.example.brofin.domain.models.BudgetingDiary
import com.example.brofin.domain.models.FinancialGoals
import com.example.brofin.domain.models.User
import kotlinx.coroutines.flow.Flow

interface BrofinRepository {

    // User Methods
    suspend fun insertUser(user: User)
    suspend fun getUser(): Flow<User?>
    suspend fun updateUser(user: User)
    suspend fun deleteUser()
    suspend fun getCurrentBalance(): Flow<Double?>
    suspend fun isUserExists(): Boolean

    // Financial Goals Methods
    suspend fun insertFinancialGoal(goal: FinancialGoals)
    suspend fun getAllFinancialGoals(): Flow<List<FinancialGoals?>>
    suspend fun updateFinancialGoal(goal: FinancialGoals)
    suspend fun deleteFinancialGoal(goalId: Int)

    // Budgeting Diary Methods
    suspend fun insertBudgetingDiaryEntry(entry: BudgetingDiary)
    suspend fun getAllBudgetingDiaryEntries(): Flow<List<BudgetingDiary?>>
    suspend fun updateBudgetingDiaryEntry(entry: BudgetingDiary)
    suspend fun deleteBudgetingDiaryEntry(entryId: Int)

    suspend fun getTotalIncome(): Flow<Double?>
    suspend fun getTotalExpenses(): Flow<Double?>
    suspend fun getBudgetingDiaryEntriesByDateRange(startDate: Long, endDate: Long): Flow<List<BudgetingDiary?>> // Flow to get entries by date range

}