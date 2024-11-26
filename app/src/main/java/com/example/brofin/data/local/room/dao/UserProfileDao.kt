package com.example.brofin.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.brofin.data.local.room.entity.UserProfileEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserProfileDao {

    @Query("SELECT SUM(savings) FROM user_profile WHERE userId = :userId")
    fun getTotalSavings(userId: String): Flow<Double?>

    @Query("SELECT * FROM user_profile WHERE userId = :userId")
    fun getUserProfile(userId: String): Flow<UserProfileEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateUserProfile(user: UserProfileEntity)

    @Query("UPDATE user_profile SET savings = :newSavings WHERE userId = :userId")
    suspend fun updateSavings(userId: String, newSavings: Double)

}