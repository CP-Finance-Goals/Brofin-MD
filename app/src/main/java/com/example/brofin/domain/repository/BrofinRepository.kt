package com.example.brofin.domain.repository

import com.example.brofin.data.local.room.entity.BudgetWithDiaries
import com.example.brofin.data.local.room.entity.BudgetingDiaryEntity
import com.example.brofin.data.local.room.entity.UserProfileEntity
import com.example.brofin.domain.models.Budgeting
import com.example.brofin.domain.models.BudgetingDiary
import com.example.brofin.domain.models.UserBalance
import com.example.brofin.utils.BudgetAllocation
import kotlinx.coroutines.flow.Flow

interface BrofinRepository {
    // Mengambil total amount berdasarkan list categoryId dan monthAndYear
    fun getTotalAmountByCategoryAndMonth(
        userId: String,
        categoryIds: List<Int>,
        monthAndYear: Long
    ): Flow<Double>

    suspend fun getBudgetingByMonth(monthAndYear: Long): Budgeting?


    // User Methods
    suspend fun insertOrUpdateUserBalance(entry: BudgetingDiary,  monthAndYear: Long)
    suspend fun insertUserBalance(userBalance: UserBalance)
    suspend fun updateUserBalance(userBalance: UserBalance)
    fun getUserCurrentBalance(userId: String, monthAndYear: Long): Flow<Double?>
    fun getUserBalance(userId: String, monthAndYear: Long): Flow<Double?>
    suspend fun userBalanceExists(userId: String, monthAndYear: Long): Boolean
    fun getTotalSavings(userId: String): Flow<Double?>

    suspend fun getUserProfile(userId: String): UserProfileEntity?
    suspend fun insertOrUpdateUserProfile(user: UserProfileEntity)

    // Financial Goals Methods
//    suspend fun insertFinancialGoal(goal: FinancialGoals)
//    fun getAllFinancialGoals(): Flow<List<FinancialGoals?>>
//    suspend fun updateFinancialGoal(goal: FinancialGoals)
    suspend fun deleteFinancialGoal(goalId: Int)

    // Budgeting Methods
    suspend fun insertBudget(budget: Budgeting)
    fun getBudgetWithDiaries(monthAndYear: Long, userId: String): Flow<BudgetWithDiaries>
    fun isUserBudgetingExist(monthAndYear: Long, userId: String): Flow<Boolean>

    // Budgeting Diary Methods
    suspend fun insertBudgetingDiaryEntry(entry: BudgetingDiary)
    fun getAllBudgetingDiaryEntries(userId: String): Flow<List<BudgetingDiary?>>
    suspend fun updateBudgetingDiaryEntry(entry: BudgetingDiary)
    suspend fun deleteBudgetingDiaryEntry(entryId: Int)
    suspend fun deleteAllBudgetingDiaryEntries()
    fun filterBudgetingDiaries(
        userId: String,
        monthAndYear: Long?,
        startDate: Long?,
        endDate: Long?,
        minAmount: Double?,
        maxAmount: Double?,
        sortBy: String,
        sortOrder: String
    ): Flow<List<BudgetingDiaryEntity?>>

    fun getTotalExpenses(
        monthAndYear: Long,
        userId: String
    ): Flow<Double?>

    fun getBudgetingDiaryEntriesByDateRange(startDate: Long, endDate: Long): Flow<List<BudgetingDiary?>>

}