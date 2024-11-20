package com.example.brofin.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.brofin.data.local.room.entity.UserBalanceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserBalanceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateUserBalance(user: UserBalanceEntity)

    @Query("SELECT * FROM user_balance WHERE userId = :userId AND monthAndYear = :monthAndYear")
    fun getUserBalance(userId: String, monthAndYear: Long): Flow<UserBalanceEntity?>

    @Query("SELECT * FROM user_balance WHERE monthAndYear = :monthAndYear AND userId = :userId")
    suspend fun getUserBalanceByMonthAndYear(monthAndYear: Long, userId: String): UserBalanceEntity?

    @Query("SELECT EXISTS(SELECT 1 FROM user_balance WHERE userId = :userId AND monthAndYear = :monthAndYear)")
    suspend fun userBalanceExists(userId: String, monthAndYear: Long): Boolean {
        return getUserBalanceByMonthAndYear(monthAndYear, userId) != null
    }

    @Query("SELECT SUM(savings) FROM user_balance WHERE userId = :userId")
    fun getTotalSavings(userId: String): Flow<Double?>

    @Query("SELECT currentBalance FROM user_balance WHERE userId = :userId AND monthAndYear = :monthAndYear")
    suspend fun getCurrentBalance(userId: String, monthAndYear: Long): Double?

    @Query("UPDATE user_balance SET currentBalance = :newBalance WHERE userId = :userId AND monthAndYear = :monthAndYear")
    suspend fun updateBalance(userId: String, monthAndYear: Long, newBalance: Double)
}
