package com.example.brofin.data.repository

import com.example.brofin.data.local.room.dao.BudgetingDiaryDao
import com.example.brofin.data.local.room.dao.FinancialGoalsDao
import com.example.brofin.data.local.room.dao.UserBalanceDao
import com.example.brofin.data.local.room.entity.BudgetingDiaryEntity
import com.example.brofin.data.mapper.toBudgetingDiary
import com.example.brofin.data.mapper.toBudgetingDiaryEntity
import com.example.brofin.data.mapper.toFinancialGoals
import com.example.brofin.data.mapper.toFinancialGoalsEntity
import com.example.brofin.data.mapper.toUser
import com.example.brofin.data.mapper.toUserBalanceEntity
import com.example.brofin.domain.repository.BrofinRepository
import com.example.brofin.domain.models.BudgetingDiary
import com.example.brofin.domain.models.FinancialGoals
import com.example.brofin.domain.models.UserBalance
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class BrofinRepositoryImpl (
    private val userBalanceDao: UserBalanceDao,
    private val financialGoalsDao: FinancialGoalsDao,
    private val budgetingDiaryDao: BudgetingDiaryDao
): BrofinRepository {
    override suspend fun insertUserBalance(userBalance: UserBalance) {
        userBalanceDao.insertOrUpdateUserBalance(userBalance.toUserBalanceEntity())
    }

    override suspend fun updateUserBalance(userBalance: UserBalance) {
        userBalanceDao.insertOrUpdateUserBalance(userBalance.toUserBalanceEntity())
    }

    override fun getUserBalance(userId: String): Flow<Double?> {
        return flow {
            val userBalance = userBalanceDao.getUserBalance(userId)

            userBalance.map {
                it?.currentBalance ?: 0.0
            }.collect { balance ->
                emit(balance)
            }
        }
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

    override suspend fun insertBudgetingDiaryEntry(entry: BudgetingDiary) {
        budgetingDiaryDao.insertBudgetingDiary(entry.toBudgetingDiaryEntity())
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
        return budgetingDiaryDao.getTotalIncome()
    }

    override fun getTotalExpenses(): Flow<Double?> {
        return budgetingDiaryDao.getTotalExpenses()
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
}