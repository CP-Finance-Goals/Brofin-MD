package com.example.brofin.data.repository

import com.example.brofin.data.local.room.dao.BudgetingDiaryDao
import com.example.brofin.data.local.room.dao.FinancialGoalsDao
import com.example.brofin.data.local.room.dao.UserDao
import com.example.brofin.data.mapper.toUserEntity
import com.example.brofin.domain.contract.repository.room.BrofinRepository
import com.example.brofin.domain.models.BudgetingDiary
import com.example.brofin.domain.models.FinancialGoals
import com.example.brofin.domain.models.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BrofinRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
    private val financialGoalsDao: FinancialGoalsDao,
    private val budgetingDiaryDao: BudgetingDiaryDao
): BrofinRepository {

    override suspend fun insertUser(user: User) {
        userDao.insertUser(user = user.toUserEntity())
    }

    override suspend fun getUser(): Flow<User> {
        
    }

    override suspend fun updateUser(user: User) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteUser() {
        TODO("Not yet implemented")
    }

    override suspend fun insertFinancialGoal(goal: FinancialGoals) {
        TODO("Not yet implemented")
    }

    override suspend fun getAllFinancialGoals(): Flow<List<FinancialGoals>> {
        TODO("Not yet implemented")
    }

    override suspend fun updateFinancialGoal(goal: FinancialGoals) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteFinancialGoal(goalId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun insertBudgetingDiaryEntry(entry: BudgetingDiary) {
        TODO("Not yet implemented")
    }

    override suspend fun getAllBudgetingDiaryEntries(): Flow<List<BudgetingDiary>> {
        TODO("Not yet implemented")
    }

    override suspend fun updateBudgetingDiaryEntry(entry: BudgetingDiary) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteBudgetingDiaryEntry(entryId: Int) {
        TODO("Not yet implemented")
    }

}