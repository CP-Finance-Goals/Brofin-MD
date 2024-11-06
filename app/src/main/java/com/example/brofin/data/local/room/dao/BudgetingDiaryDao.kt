package com.example.brofin.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.brofin.data.local.room.entity.BudgetingDiaryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BudgetingDiaryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBudgetingDiary(entry: BudgetingDiaryEntity)

    @Update
    suspend fun updateBudgetingDiary(entry: BudgetingDiaryEntity)

    @Query("DELETE FROM budgeting_diary WHERE entryId = :id")
    suspend fun deleteBudgetingDiaryById(id: Int)

    @Query("SELECT * FROM budgeting_diary ORDER BY date DESC")
    fun getAllBudgetingDiaries(): Flow<List<BudgetingDiaryEntity?>>

    @Query("SELECT SUM(amount) FROM budgeting_diary WHERE isExpense = 0")
    fun getTotalIncome(): Flow<Double?>

    @Query("SELECT SUM(amount) FROM budgeting_diary WHERE isExpense = 1")
    fun getTotalExpenses(): Flow<Double?>

    @Query("SELECT * FROM budgeting_diary WHERE date BETWEEN :startDate AND :endDate ORDER BY date DESC")
    fun getBudgetingDiariesByDateRange(startDate: Long, endDate: Long): Flow<List<BudgetingDiaryEntity?>>

    @Query("SELECT currentBalance FROM user LIMIT 1")
    fun getCurrentBalance(): Flow<Double?>

}
