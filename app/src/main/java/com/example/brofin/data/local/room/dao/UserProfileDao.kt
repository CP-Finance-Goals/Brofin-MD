package com.example.brofin.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.brofin.data.local.room.entity.UserProfileEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserProfileDao {

    @Query("SELECT SUM(savings) FROM user_profile")
    fun getTotalSavings(): Flow<Double?>

    @Query("SELECT * FROM user_profile LIMIT 1")
    suspend fun getUserProfile(): UserProfileEntity?

    @Query("SELECT * FROM user_profile")
    suspend fun getUserProfile2(): UserProfileEntity?

    @Query("SELECT * FROM user_profile LIMIT 1")
    fun getUserProfileFlow(): Flow<UserProfileEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateUserProfile(user: UserProfileEntity)

    @Query("UPDATE user_profile SET savings = :newSavings")
    suspend fun updateSavings(newSavings: Double)

    @Query("DELETE FROM user_profile")
    suspend fun deleteAllUserProfiles()
}
