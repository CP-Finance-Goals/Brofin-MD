package com.example.brofin.domain.repository

import com.example.brofin.domain.models.doc.BudgetingDiaryDoc
import com.example.brofin.domain.models.doc.FinancialGoalsDoc
import com.example.brofin.domain.models.doc.UserBalanceDoc
import kotlinx.coroutines.flow.Flow


interface FirestoreRepository {

    // Financial Goals
    suspend fun saveFinancialGoal(goal: FinancialGoalsDoc)

    fun getFinancialGoalsList(userId: String): Flow<List<FinancialGoalsDoc>>

    suspend fun updateFinancialGoal(goal: FinancialGoalsDoc)

    suspend fun deleteFinancialGoal(id: String)

    suspend fun deleteAllFinancialGoals(userId: String)


    // Budgeting Diary
    suspend fun saveBudgetingDiary(diary: BudgetingDiaryDoc)

    fun getBudgetingDiaryList(userId: String): Flow<List<BudgetingDiaryDoc>>

    suspend fun updateBudgetingDiary(entry: BudgetingDiaryDoc)

    suspend fun deleteBudgetingDiary(entryId: String)

    suspend fun deleteAllBudgetingDiary(userId: String)


    // User
    suspend fun saveUser(user: UserBalanceDoc): Boolean

    suspend fun userExists(userId: String): Boolean

    fun getUser(userId: String): Flow<UserBalanceDoc?>

    suspend fun updateUser(user: UserBalanceDoc)

    suspend fun deleteUser(userId: String)



}
