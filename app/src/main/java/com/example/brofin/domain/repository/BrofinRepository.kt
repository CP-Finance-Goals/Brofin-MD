package com.example.brofin.domain.repository

import com.example.brofin.data.local.room.entity.BudgetWithDiaries
import com.example.brofin.data.local.room.entity.BudgetingDiaryEntity
import com.example.brofin.domain.models.Budgeting
import com.example.brofin.domain.models.BudgetingDiary
import com.example.brofin.domain.models.FinancialGoals
import com.example.brofin.domain.models.UserBalance
import kotlinx.coroutines.flow.Flow

interface BrofinRepository {

    // User Methods
    suspend fun insertOrUpdateUserBalance(entry: BudgetingDiary,  monthAndYear: Long)
    suspend fun insertUserBalance(userBalance: UserBalance)
    suspend fun updateUserBalance(userBalance: UserBalance)
    fun getUserCurrentBalance(userId: String, monthAndYear: Long): Flow<Double?>
    fun getUserBalance(userId: String, monthAndYear: Long): Flow<Double?>
    suspend fun userBalanceExists(userId: String, monthAndYear: Long): Boolean

    // Financial Goals Methods
    suspend fun insertFinancialGoal(goal: FinancialGoals)
    fun getAllFinancialGoals(): Flow<List<FinancialGoals?>>
    suspend fun updateFinancialGoal(goal: FinancialGoals)
    suspend fun deleteFinancialGoal(goalId: Int)

    // Budgeting Methods
    suspend fun insertBudget(budget: Budgeting)
    fun getBudgetWithDiaries(monthAndYear: Long, userId: String): Flow<BudgetWithDiaries>
    fun getAllBudgetsWithDiaries(userId: String): Flow<List<BudgetWithDiaries>>


    // Budgeting Diary Methods
    suspend fun insertBudgetingDiaryEntry(entry: BudgetingDiary)
    fun getAllBudgetingDiaryEntries(): Flow<List<BudgetingDiary?>>
    suspend fun updateBudgetingDiaryEntry(entry: BudgetingDiary)
    suspend fun deleteBudgetingDiaryEntry(entryId: Int)
    suspend fun deleteAllBudgetingDiaryEntries()
    fun filterBudgetingDiaries(
        startDate: Long? = null,
        endDate: Long? = null,
        isExpense: Boolean? = null,
        minAmount: Double? = null,
        maxAmount: Double? = null
    ): Flow<List<BudgetingDiaryEntity?>>

    fun getTotalIncome(): Flow<Double?>
    fun getTotalExpenses(): Flow<Double?>
    fun getBudgetingDiaryEntriesByDateRange(startDate: Long, endDate: Long): Flow<List<BudgetingDiary?>> // Flow to get entries by date range

}