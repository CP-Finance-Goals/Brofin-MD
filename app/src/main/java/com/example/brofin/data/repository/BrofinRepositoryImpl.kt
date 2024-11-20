package com.example.brofin.data.repository

import android.util.Log
import com.example.brofin.data.local.room.dao.BudgetingDao
import com.example.brofin.data.local.room.dao.BudgetingDiaryDao
import com.example.brofin.data.local.room.dao.FinancialGoalsDao
import com.example.brofin.data.local.room.dao.UserBalanceDao
import com.example.brofin.data.local.room.entity.BudgetWithDiaries
import com.example.brofin.data.local.room.entity.BudgetingDiaryEntity
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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class BrofinRepositoryImpl (
    private val userBalanceDao: UserBalanceDao,
    private val financialGoalsDao: FinancialGoalsDao,
    private val budgetingDiaryDao: BudgetingDiaryDao,
    private val budgetingDao: BudgetingDao
): BrofinRepository {

    // fungsi ini untuk menyimpan data user balance ke dalam database
    override suspend fun insertUserBalance(userBalance: UserBalance) {
        userBalanceDao.insertOrUpdateUserBalance(userBalance.toUserBalanceEntity())
    }

    // fungsi ini untuk menyimpan data budgeting diary ke dalam database
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

    // fungsi ini untuk menyimpan data user balance ke dalam database fungsinya sama dengan fungsi insertUserBalance
    override suspend fun updateUserBalance(userBalance: UserBalance) {
        userBalanceDao.insertOrUpdateUserBalance(userBalance.toUserBalanceEntity())
    }

    // fungsi ini untuk mendapatkan data user balance berdasarkan userId dan monthAndYear
    override fun getUserCurrentBalance(userId: String, monthAndYear: Long): Flow<Double?> {
        return userBalanceDao.getUserBalance(userId, monthAndYear).map { it?.currentBalance }
    }

    // fungsi ini untuk mendapatkan data user balance berdasarkan userId dan monthAndYear
    override fun getUserBalance(userId: String, monthAndYear: Long): Flow<Double?> {
        return userBalanceDao.getUserBalance(userId, monthAndYear).map { it?.balance }
    }

    // fungsi ini untuk mengecek apakah data user balance sudah ada atau belum pada bulan dan tahun tertentu
    override suspend fun userBalanceExists(userId: String, monthAndYear: Long): Boolean {
        return userBalanceDao.userBalanceExists(userId, monthAndYear)
    }

    // fungsi ini untuk mendapatkan total tabungan dari user berdasarkan userId
    override fun getTotalSavings(userId: String): Flow<Double?> {
        return budgetingDao.getTotalSavings(userId)
    }

    // fungsi ini dipakai untuk menyimpan data financial goal ke dalam database
    override suspend fun insertFinancialGoal(goal: FinancialGoals) {
        financialGoalsDao.insertGoal(goal.toFinancialGoalsEntity())
    }

    // fungsi ini untuk mendapatkan semua data financial goal yang ada
    override fun getAllFinancialGoals(): Flow<List<FinancialGoals?>> = financialGoalsDao.getAllGoals().map { goals ->
        goals.map { it?.toFinancialGoals() }
    }

    // fungsi ini untuk mengupdate data financial goal
    override suspend fun updateFinancialGoal(goal: FinancialGoals) {
        financialGoalsDao.updateGoal(goal.toFinancialGoalsEntity())
    }

    // fungsi ini untuk menghapus data financial goal berdasarkan goalId
    override suspend fun deleteFinancialGoal(goalId: Int) {
        financialGoalsDao.deleteGoalById(goalId)
    }

    // fungsi ini untuk menyimpan data budgeting ke dalam database
    override suspend fun insertBudget(budget: Budgeting) {
        budgetingDao.insertBudget(budget.toBudgetingEntity())
    }

    // fungsi ini untuk mendapatkan data budgeting dengan diaries berdasarkan monthAndYear dan userId
    override fun getBudgetWithDiaries(monthAndYear: Long, userId: String): Flow<BudgetWithDiaries> {
        return budgetingDao.getBudgetWithDiaries(monthAndYear, userId)
    }

    override fun isUserBudgetingExist(
        monthAndYear: Long,
        userId: String
    ): Flow<Boolean> {
        return budgetingDao.isUserBudgetingExist(monthAndYear, userId)
    }

    // fungsi ini untuk menyimpan data budgeting diary ke dalam database
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

    // fungsi ini untuk mendapatkan semua data budgeting diary yang ada
    override fun getAllBudgetingDiaryEntries(): Flow<List<BudgetingDiary?>> = budgetingDiaryDao.getAllBudgetingDiaries().map { entries ->
        entries.map { it?.toBudgetingDiary()}
    }

    // fungsi ini untuk mengupdate data budgeting diary
    override suspend fun updateBudgetingDiaryEntry(entry: BudgetingDiary) {
        budgetingDiaryDao.updateBudgetingDiary(entry.toBudgetingDiaryEntity())
    }

    // fungsi ini untuk menghapus data budgeting diary berdasarkan entryId
    override suspend fun deleteBudgetingDiaryEntry(entryId: Int) {
        budgetingDiaryDao.deleteBudgetingDiaryById(entryId)
    }

    // fungsi ini untuk menghapus semua data budgeting diary
    override suspend fun deleteAllBudgetingDiaryEntries() {
        budgetingDiaryDao.deleteAllBudgetingDiaries()
    }

    // fungsi ini untuk mendapatkan data budgeting diary berdasarkan tanggal
    override fun getTotalExpenses(
        monthAndYear: Long,
        userId: String
    ): Flow<Double?> {
        return budgetingDiaryDao.getTotalExpensesMonth( monthAndYear, userId)
    }

    // fungsi ini untuk mendapatkan data budgeting diary berdasarkan tanggal
    override fun getBudgetingDiaryEntriesByDateRange(
        startDate: Long,
        endDate: Long
    ): Flow<List<BudgetingDiary?>> {
        return budgetingDiaryDao.getBudgetingDiariesByDateRange(startDate, endDate).map { entries ->
            entries.map { it?.toBudgetingDiary()}
        }
    }

    override fun filterBudgetingDiaries(
        userId: String,
        monthAndYear: Long?,
        startDate: Long?,
        endDate: Long?,
        isExpense: Boolean?,
        minAmount: Double?,
        maxAmount: Double?,
        sortBy: String,
        sortOrder: String
    ): Flow<List<BudgetingDiaryEntity?>> {
        return budgetingDiaryDao.filterBudgetingDiaries(
            userId = userId,
            monthAndYear = monthAndYear,
            startDate = startDate,
            endDate = endDate,
            isExpense = isExpense,
            minAmount = minAmount,
            maxAmount = maxAmount,
            sortBy = sortBy,
            sortOrder = sortOrder
        )
    }


    companion object{
        const val TAG = "BrofinRepositoryImpl"
    }
}