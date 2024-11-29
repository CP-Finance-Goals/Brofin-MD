package com.example.brofin.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.brofin.data.local.room.entity.BudgetingWithDiaries
import com.example.brofin.data.local.room.entity.BudgetingEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BudgetingDao {

    @Transaction
    @Query("SELECT * FROM budgeting WHERE monthAndYear = :monthAndYear AND userId = :userId")
    fun getBudgetWithDiaries(monthAndYear: Long, userId: String): Flow<BudgetingWithDiaries>

//    @Transaction
//    @Query("SELECT * FROM budgeting WHERE userId = :userId")
//    fun getAllBudgetsWithDiaries(userId: String): Flow<BudgetWithDiaries>

    @Query("SELECT * FROM budgeting WHERE monthAndYear = :monthAndYear LIMIT 1")
    suspend fun getBudgetingByMonth(monthAndYear: Long): BudgetingEntity?

    @Query("""
        SELECT SUM(savingsLimit)
        FROM budgeting
        WHERE userId = :userId
    """)
    fun getTotalSavings(userId: String): Flow<Double?>

    @Query("""
        SELECT EXISTS(
            SELECT 1 
            FROM budgeting 
            WHERE monthAndYear = :monthAndYear AND userId = :userId
        )
    """)
    fun isUserBudgetingExist(monthAndYear: Long, userId: String): Flow<Boolean>

    @Insert
    suspend fun insertBudget(budget: BudgetingEntity)
}
