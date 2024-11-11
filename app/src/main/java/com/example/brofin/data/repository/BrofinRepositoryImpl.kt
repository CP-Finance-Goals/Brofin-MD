package com.example.brofin.data.repository

import com.example.brofin.data.local.room.dao.BudgetingDiaryDao
import com.example.brofin.data.local.room.dao.FinancialGoalsDao
import com.example.brofin.data.local.room.dao.UserDao
import com.example.brofin.data.mapper.toBudgetingDiary
import com.example.brofin.data.mapper.toBudgetingDiaryEntity
import com.example.brofin.data.mapper.toFinancialGoals
import com.example.brofin.data.mapper.toFinancialGoalsEntity
import com.example.brofin.data.mapper.toUser
import com.example.brofin.data.mapper.toUserEntity
import com.example.brofin.domain.repository.BrofinRepository
import com.example.brofin.domain.models.BudgetingDiary
import com.example.brofin.domain.models.FinancialGoals
import com.example.brofin.domain.models.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BrofinRepositoryImpl (
    private val userDao: UserDao,
    private val financialGoalsDao: FinancialGoalsDao,
    private val budgetingDiaryDao: BudgetingDiaryDao
): BrofinRepository {

    override suspend fun insertUser(user: User) {
        userDao.insertUser(user = user.toUserEntity())
    }

    override suspend fun getUser(): Flow<User?> = userDao.getUser().map { userEntity ->
        userEntity?.toUser()
    }

    override suspend fun updateUser(user: User) {
        userDao.updateUser(user = user.toUserEntity())
    }

    override suspend fun deleteUser() {
        userDao.deleteUser()
    }

    override suspend fun getCurrentBalance(): Flow<Double?> = budgetingDiaryDao.getCurrentBalance()

    override suspend fun isUserExists(): Boolean = userDao.isUserExists()

    override suspend fun insertFinancialGoal(goal: FinancialGoals) {
        financialGoalsDao.insertGoal(goal.toFinancialGoalsEntity())
    }

    override suspend fun getAllFinancialGoals(): Flow<List<FinancialGoals?>> = financialGoalsDao.getAllGoals().map { goals ->
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

    override suspend fun getAllBudgetingDiaryEntries(): Flow<List<BudgetingDiary?>> = budgetingDiaryDao.getAllBudgetingDiaries().map { entries ->
        entries.map { it?.toBudgetingDiary()}
    }

    override suspend fun updateBudgetingDiaryEntry(entry: BudgetingDiary) {
        budgetingDiaryDao.updateBudgetingDiary(entry.toBudgetingDiaryEntity())
    }

    override suspend fun deleteBudgetingDiaryEntry(entryId: Int) {
        budgetingDiaryDao.deleteBudgetingDiaryById(entryId)
    }

    override suspend fun getTotalIncome(): Flow<Double?> {
        return budgetingDiaryDao.getTotalIncome()
    }

    override suspend fun getTotalExpenses(): Flow<Double?> {
        return budgetingDiaryDao.getTotalExpenses()
    }

    override suspend fun getBudgetingDiaryEntriesByDateRange(
        startDate: Long,
        endDate: Long
    ): Flow<List<BudgetingDiary?>> {
        return budgetingDiaryDao.getBudgetingDiariesByDateRange(startDate, endDate).map { entries ->
            entries.map { it?.toBudgetingDiary()}
        }
    }
}