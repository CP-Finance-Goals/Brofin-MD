package com.example.brofin.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.brofin.data.local.room.entity.FinancialGoalsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FinancialGoalsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGoal(financialGoals: FinancialGoalsEntity)

    @Update
    suspend fun updateGoal(financialGoals: FinancialGoalsEntity)

    @Query("SELECT * FROM financial_goals")
    suspend fun getAllGoals(): Flow<List<FinancialGoalsEntity?>>

    @Query("DELETE FROM financial_goals WHERE goalId = :goalId")
    suspend fun deleteGoalById(goalId: Int)
}