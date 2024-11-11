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

    @Query("SELECT * FROM user_balance WHERE userId = :userId")
    fun getUserBalance(userId: String): Flow<UserBalanceEntity?>

}
