package com.example.brofin.data.repository

import android.util.Log
import com.example.brofin.data.local.room.dao.BudgetingDao
import com.example.brofin.data.local.room.dao.BudgetingDiaryDao
import com.example.brofin.data.local.room.dao.FinancialGoalsDao
import com.example.brofin.data.local.room.dao.UserBalanceDao
import com.example.brofin.data.local.room.entity.BudgetWithDiaries
import com.example.brofin.data.local.room.entity.BudgetingDiaryEntity
import com.example.brofin.data.local.room.entity.BudgetingEntity
import com.example.brofin.data.local.room.entity.UserBalanceEntity
import com.example.brofin.data.mapper.toBudgetingDiary
import com.example.brofin.data.mapper.toBudgetingDiaryEntity
import com.example.brofin.data.mapper.toBudgetingEntity
import com.example.brofin.data.mapper.toFinancialGoals
import com.example.brofin.data.mapper.toFinancialGoalsEntity
import com.example.brofin.data.mapper.toUserBalanceEntity
import com.example.brofin.domain.models.Budgeting
import com.example.brofin.domain.models.BudgetingDiary
import com.example.brofin.domain.models.FinancialGoals
import com.example.brofin.domain.models.UserBalance
import com.example.brofin.domain.repository.BrofinRepository
import com.example.brofin.utils.Expense.categoryExpensesLists
import com.example.brofin.utils.getCurrentMonthAndYearAsLong
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.Calendar
import java.util.Locale
import kotlin.random.Random

class BrofinRepositoryImpl (
    private val userBalanceDao: UserBalanceDao,
    private val financialGoalsDao: FinancialGoalsDao,
    private val budgetingDiaryDao: BudgetingDiaryDao,
    private val budgetingDao: BudgetingDao
): BrofinRepository {

    override suspend fun insertUserBalance(userBalance: UserBalance) {
        userBalanceDao.insertOrUpdateUserBalance(userBalance.toUserBalanceEntity())
    }

    override suspend fun insertOrUpdateUserBalance(entry: BudgetingDiary, monthAndYear: Long) {
        try {
            val currentBalance = userBalanceDao.getCurrentBalance(entry.userId, monthAndYear) ?: 0.0
            val updatedBalance = if (entry.isExpense) {
                currentBalance - entry.amount
            } else {
                currentBalance + entry.amount
            }
            val existingBalance = userBalanceDao.getUserBalance(entry.userId, monthAndYear).firstOrNull()
            if (existingBalance != null) {
                userBalanceDao.updateBalance(entry.userId, monthAndYear, updatedBalance)
            } else {
                userBalanceDao.insertOrUpdateUserBalance(UserBalanceEntity(monthAndYear = entry.monthAndYear, userId = entry.userId, currentBalance = updatedBalance))
            }
                budgetingDiaryDao.insertBudgetingDiary(entry.toBudgetingDiaryEntity())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    
    override suspend fun updateUserBalance(userBalance: UserBalance) {
        userBalanceDao.insertOrUpdateUserBalance(userBalance.toUserBalanceEntity())
    }
    
    override fun getUserCurrentBalance(userId: String, monthAndYear: Long): Flow<Double?> {
        return userBalanceDao.getUserBalance(userId, monthAndYear).map { it?.currentBalance }
    }
    
    override fun getUserBalance(userId: String, monthAndYear: Long): Flow<Double?> {
        return userBalanceDao.getUserBalance(userId, monthAndYear).map { it?.balance }
    }

    override suspend fun userBalanceExists(userId: String, monthAndYear: Long): Boolean {
        return userBalanceDao.userBalanceExists(userId, monthAndYear)
    }
    
    override suspend fun insertFinancialGoal(goal: FinancialGoals) {
        financialGoalsDao.insertGoal(goal.toFinancialGoalsEntity())
    }
    
    override fun getAllFinancialGoals(): Flow<List<FinancialGoals?>> = financialGoalsDao.getAllGoals().map { goals ->
        goals.map { it?.toFinancialGoals() }
    }
    
    override suspend fun updateFinancialGoal(goal: FinancialGoals) {
        financialGoalsDao.updateGoal(goal.toFinancialGoalsEntity())
    }
    
    override suspend fun deleteFinancialGoal(goalId: Int) {
        financialGoalsDao.deleteGoalById(goalId)
    }
    
    // Budgeting Methods
    override suspend fun insertBudget(budget: Budgeting) {
        budgetingDao.insertBudget(budget.toBudgetingEntity())
    }
    
    override fun getBudgetWithDiaries(monthAndYear: Long, userId: String): Flow<BudgetWithDiaries> {
        return budgetingDao.getBudgetWithDiaries(monthAndYear, userId)
    }
    
    override fun getAllBudgetsWithDiaries(userId: String): Flow<List<BudgetWithDiaries>> {
        return flow {
        // TODO: Implement this method
        }
    }

    // Budgeting Diary Methods
    override suspend fun insertBudgetingDiaryEntry(entry: BudgetingDiary) {
        try {
            val currentBalance = userBalanceDao.getCurrentBalance(entry.userId, entry.monthAndYear) ?: 0.0
            if (entry.isExpense && currentBalance < entry.amount) {
                throw IllegalArgumentException("Saldo tidak mencukupi untuk melakukan pengeluaran sebesar ${entry.amount}.")
            }
            val updatedBalance = if (entry.isExpense) {
                currentBalance - entry.amount
            } else {
                currentBalance + entry.amount
            }
            // Perbarui saldo pengguna
            val existingBalance = userBalanceDao.getUserBalance(entry.userId, entry.monthAndYear).firstOrNull()
            if (existingBalance != null) {
                userBalanceDao.updateBalance(entry.userId, entry.monthAndYear, updatedBalance)
            } else {
                userBalanceDao.insertOrUpdateUserBalance(
                    UserBalanceEntity(
                        monthAndYear = entry.monthAndYear,
                        userId = entry.userId,
                        balance = currentBalance,
                        currentBalance = updatedBalance
                    )
                )
            }
            // Simpan budgeting diary
            budgetingDiaryDao.insertBudgetingDiary(entry.toBudgetingDiaryEntity())
        } catch (e: IllegalArgumentException) {
            Log.e(TAG, e.message ?: "Error pada input data")
            throw e
        } catch (e: Exception) {
            Log.e(TAG, "Error saat menyimpan data budgeting diary", e)
            throw e
        }
    }

    override fun getAllBudgetingDiaryEntries(): Flow<List<BudgetingDiary?>> = budgetingDiaryDao.getAllBudgetingDiaries().map { entries ->
        entries.map { it?.toBudgetingDiary()}
    }
    
    override suspend fun updateBudgetingDiaryEntry(entry: BudgetingDiary) {
        budgetingDiaryDao.updateBudgetingDiary(entry.toBudgetingDiaryEntity())
    }
    
    override suspend fun deleteBudgetingDiaryEntry(entryId: Int) {
        budgetingDiaryDao.deleteBudgetingDiaryById(entryId)
    }
    
    override suspend fun deleteAllBudgetingDiaryEntries() {
        budgetingDiaryDao.deleteAllBudgetingDiaries()
    }
    
    override fun getTotalIncome(): Flow<Double?> {
        return budgetingDiaryDao.getTotalIncomeMonth()
    }
    
    override fun getTotalExpenses(): Flow<Double?> {
        return budgetingDiaryDao.getTotalExpensesMonth()
    }
    
    override fun getBudgetingDiaryEntriesByDateRange(
        startDate: Long,
        endDate: Long
    ): Flow<List<BudgetingDiary?>> {
        return budgetingDiaryDao.getBudgetingDiariesByDateRange(startDate, endDate).map { entries ->
            entries.map { it?.toBudgetingDiary()}
        }
    }
    
    override fun filterBudgetingDiaries(
        startDate: Long?,
        endDate: Long?,
        isExpense: Boolean?,
        minAmount: Double?,
        maxAmount: Double?
    ): Flow<List<BudgetingDiaryEntity?>> {
        return budgetingDiaryDao.filterBudgetingDiaries(
            startDate = startDate,
            endDate = endDate,
            isExpense = isExpense,
            minAmount = minAmount,
            maxAmount = maxAmount
        )
    }
    
    companion object{
        const val TAG = "BrofinRepositoryImpl"
    }
}