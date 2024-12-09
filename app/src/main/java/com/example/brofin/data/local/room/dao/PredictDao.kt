package com.example.brofin.data.local.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.brofin.data.local.room.entity.PredictEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PredictDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPredict(predict: PredictEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPredicts(predicts: List<PredictEntity>)

    @Update
    suspend fun updatePredict(predict: PredictEntity)

    @Delete
    suspend fun deletePredict(predict: PredictEntity)

    @Query("SELECT * FROM predictTable")
    fun getAllPredict(): Flow<List<PredictEntity>?>

    @Query("SELECT * FROM predictTable WHERE id = :id")
    suspend fun getPredictById(id: String): PredictEntity?

    @Query("SELECT COUNT(*) FROM predictTable")
    suspend fun getPredictCount(): Int

    @Query("DELETE FROM predictTable")
    suspend fun deleteAllPredicts()
}
