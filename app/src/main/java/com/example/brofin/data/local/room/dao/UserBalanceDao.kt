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

    @Query("SELECT * FROM user_balance WHERE monthAndYear = :monthAndYear")
    fun getUserBalanceByMonthAndYear(monthAndYear: Long): Flow<UserBalanceEntity?>

    @Query("SELECT * FROM user_balance WHERE monthAndYear = :monthAndYear")
    suspend fun getUserBalanceByMonthAndYearOnce(monthAndYear: Long): UserBalanceEntity?

    @Query("SELECT EXISTS(SELECT 1 FROM user_balance WHERE monthAndYear = :monthAndYear)")
    suspend fun userBalanceExists(monthAndYear: Long): Boolean {
        return getUserBalanceByMonthAndYearOnce(monthAndYear) != null
    }

    @Query("SELECT currentBalance FROM user_balance WHERE monthAndYear = :monthAndYear")
    suspend fun getCurrentBalance(monthAndYear: Long): Double?

    @Query("UPDATE user_balance SET currentBalance = :newBalance WHERE monthAndYear = :monthAndYear")
    suspend fun updateBalance(monthAndYear: Long, newBalance: Double)
}

