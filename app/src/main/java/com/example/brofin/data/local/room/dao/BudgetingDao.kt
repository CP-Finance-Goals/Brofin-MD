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
    @Query("SELECT * FROM budgeting WHERE monthAndYear = :monthAndYear")
    fun getBudgetWithDiaries(monthAndYear: Long): Flow<BudgetingWithDiaries>

    @Query("SELECT * FROM budgeting WHERE monthAndYear = :monthAndYear LIMIT 1")
    suspend fun getBudgetingByMonth(monthAndYear: Long): BudgetingEntity?

    @Query("""
        SELECT SUM(savingsLimit)
        FROM budgeting
    """)
    fun getTotalSavings(): Flow<Double?>

    @Query("""
        SELECT EXISTS(
            SELECT 1 
            FROM budgeting 
            WHERE monthAndYear = :monthAndYear
        )
    """)
    fun isUserBudgetingExist(monthAndYear: Long): Flow<Boolean>

    @Insert
    suspend fun insertBudget(budget: BudgetingEntity)

    @Query("DELETE FROM budgeting")
    suspend fun deleteAllBudgeting()
}
