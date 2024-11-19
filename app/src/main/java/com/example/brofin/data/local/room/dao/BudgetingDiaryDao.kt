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

    @Query("DELETE FROM budgeting_diary WHERE id = :id")
    suspend fun deleteBudgetingDiaryById(id: Int)

    @Query("DELETE FROM budgeting_diary")
    suspend fun deleteAllBudgetingDiaries()

    // query ini untuk      mengurutkan berdasarkan yang terbaru dan terlama
    @Query("SELECT * FROM budgeting_diary ORDER BY date DESC")
    fun getAllBudgetingDiaries(): Flow<List<BudgetingDiaryEntity?>>

    // query ini digunakan untuk mengambil total income
    @Query("SELECT SUM(amount) FROM budgeting_diary WHERE isExpense = 0")
    fun getTotalIncome(): Flow<Double?>

    // query ini digunakan untuk mengambil total income berdasarkan bulan dan tahun sekarang
    @Insert
    suspend fun insertAllDiaries(diaries: List<BudgetingDiaryEntity>)

    // query ini digunakan untuk mengambil total income berdasarkan bulan dan tahun sekarang
    @Query("""
    SELECT SUM(amount) 
    FROM budgeting_diary 
    WHERE isExpense = 0 
    AND strftime('%Y-%m', date / 1000, 'unixepoch') = strftime('%Y-%m', 'now')
""")
    fun getTotalIncomeMonth(): Flow<Double?>


    // query ini digunakan untuk mengambil total expenses
    @Query("SELECT SUM(amount) FROM budgeting_diary WHERE isExpense = 1")
    fun getTotalExpenses(): Flow<Double?>


    // query ini digunakan untuk mengambil total expenses berdasarkan bulan dan tahun sekarang
    @Query("""
    SELECT SUM(amount) 
    FROM budgeting_diary 
    WHERE isExpense = 1
    AND strftime('%Y-%m', date / 1000, 'unixepoch') = strftime('%Y-%m', 'now')
    """)
    fun getTotalExpensesMonth(): Flow<Double?>


    // query ini digunakan untuk mengambil data budgeting diary berdasarkan tanggal awal dan akhir
    @Query("SELECT * FROM budgeting_diary WHERE date BETWEEN :startDate AND :endDate ORDER BY date DESC")
    fun getBudgetingDiariesByDateRange(startDate: Long, endDate: Long): Flow<List<BudgetingDiaryEntity?>>


    // query ini digunakan untuk mengambil data budgeting diary berdasarkan tanggal, apakah expense atau income, dan jumlah amount
    @Query("""
    SELECT * FROM budgeting_diary 
    WHERE 
        (:startDate IS NULL OR date >= :startDate) 
        AND (:endDate IS NULL OR date <= :endDate) 
        AND (:isExpense IS NULL OR isExpense = :isExpense) 
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
        startDate: Long? = null,
        endDate: Long? = null,
        isExpense: Boolean? = null,
        minAmount: Double? = null,
        maxAmount: Double? = null,
        sortBy: String = "date",
        sortOrder: String = "desc"
    ): Flow<List<BudgetingDiaryEntity?>>

}
