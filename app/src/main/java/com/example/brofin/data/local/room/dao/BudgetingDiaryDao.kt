package com.example.brofin.data.local.room.dao

import androidx.room.ColumnInfo
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

    @Query("DELETE FROM budgeting_diary WHERE id = :id")
    suspend fun deleteBudgetingDiaryById(id: Int)

    @Query("DELETE FROM budgeting_diary")
    suspend fun deleteAllBudgetingDiaries()

    @Query("SELECT * FROM budgeting_diary ORDER BY date DESC")
    fun getAllBudgetingDiaries(): Flow<List<BudgetingDiaryEntity?>>

    @Query("""
        SELECT * 
        FROM budgeting_diary
        ORDER BY date DESC
        LIMIT 3
    """)
    fun getLatestBudgetingDiaries(): Flow<List<BudgetingDiaryEntity>>

    @Query("SELECT SUM(amount) FROM budgeting_diary ")
    fun getTotalIncome(): Flow<Double?>

    @Insert
    suspend fun insertAllDiaries(diaries: List<BudgetingDiaryEntity>)

    @Query("SELECT SUM(amount) FROM budgeting_diary")
    fun getTotalExpenses(): Flow<Double?>

    @Query("""
        SELECT SUM(amount)
        FROM budgeting_diary
        WHERE monthAndYear = :monthAndYear
    """)
    fun getTotalExpensesMonth(monthAndYear: Long): Flow<Double?>

    @Query("SELECT * FROM budgeting_diary WHERE date BETWEEN :startDate AND :endDate ORDER BY date DESC")
    fun getBudgetingDiariesByDateRange(startDate: Long, endDate: Long): Flow<List<BudgetingDiaryEntity?>>

    @Query("""
        SELECT * FROM budgeting_diary
        WHERE 
            (:monthAndYear IS NULL OR strftime('%Y-%m', date / 1000, 'unixepoch') = strftime('%Y-%m', :monthAndYear / 1000, 'unixepoch'))
            AND (:startDate IS NULL OR date >= :startDate)
            AND (:endDate IS NULL OR date <= :endDate)
            AND (:minAmount IS NULL OR amount >= :minAmount)
            AND (:maxAmount IS NULL OR amount <= :maxAmount)
        ORDER BY 
            CASE 
                WHEN :sortBy = 'date' AND :sortOrder = 'asc' THEN date
                WHEN :sortBy = 'date' AND :sortOrder = 'desc' THEN -date
                WHEN :sortBy = 'amount' AND :sortOrder = 'asc' THEN amount
                WHEN :sortBy = 'amount' AND :sortOrder = 'desc' THEN -amount
            END
    """)
    fun filterBudgetingDiaries(
        monthAndYear: Long? = null,
        startDate: Long? = null,
        endDate: Long? = null,
        minAmount: Double? = null,
        maxAmount: Double? = null,
        sortBy: String = "date",
        sortOrder: String = "desc"
    ): Flow<List<BudgetingDiaryEntity?>>

    @Query("""
        SELECT SUM(amount) FROM budgeting_diary 
        WHERE categoryId IN (:categoryIds)
        AND monthAndYear = :monthAndYear
    """)
    fun getTotalAmountByCategoryAndMonth(
        categoryIds: List<Int>,
        monthAndYear: Long
    ): Flow<Double>

}


