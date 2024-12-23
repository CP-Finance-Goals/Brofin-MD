package com.example.brofin.domain.repository

import com.example.brofin.data.local.room.entity.BudgetingWithDiaries
import com.example.brofin.data.local.room.entity.BudgetingDiaryEntity
import com.example.brofin.data.local.room.entity.PredictEntity
import com.example.brofin.data.local.room.entity.UserBalanceEntity
import com.example.brofin.data.local.room.entity.UserProfileEntity
import com.example.brofin.domain.models.Budgeting
import com.example.brofin.domain.models.BudgetingDiary
import com.example.brofin.domain.models.Predict
import com.example.brofin.domain.models.PredictResponse
import com.example.brofin.domain.models.UserBalance
import com.example.brofin.domain.models.UserProfile
import kotlinx.coroutines.flow.Flow

interface BrofinRepository {

    fun getTotalAmountByCategoryAndMonth(
        categoryIds: List<Int>,
        monthAndYear: Long
    ): Flow<Double>

    suspend fun getUserFlow(): Flow<UserProfile?>

    suspend fun getUserBalanceData(monthAndYear: Long): UserBalanceEntity?

    suspend fun getCurrentBalance(monthAndYear: Long): Double

    suspend fun getBudgetingByMonth(monthAndYear: Long): Budgeting?

    fun getTotalSavings(): Flow<Double?>

    suspend fun insertBudget(budget: Budgeting)

    fun getBudgetWithDiaries(monthAndYear: Long): Flow<BudgetingWithDiaries>

    fun isUserBudgetingExist(monthAndYear: Long): Flow<Boolean>

    suspend fun insertBudgetingDiaryEntry(entry: BudgetingDiary)

    fun getAllBudgetingDiaryEntries(): Flow<List<BudgetingDiary?>>

    suspend fun updateBudgetingDiaryEntry(entry: BudgetingDiary)

    suspend fun deleteBudgetingDiaryEntry(entryId: Int)

    suspend fun deleteAllBudgetingDiaryEntries()

    fun filterBudgetingDiaries(
        monthAndYear: Long? = null,
        startDate: Long? = null,
        endDate: Long? = null,
        minAmount: Double? = null,
        maxAmount: Double? = null,
        sortBy: String = "date",
        sortOrder: String = "desc"
    ): Flow<List<BudgetingDiaryEntity?>>

    fun getTotalExpenses(monthAndYear: Long): Flow<Double?>

    fun getBudgetingDiaryEntriesByDateRange(startDate: Long, endDate: Long): Flow<List<BudgetingDiary?>>

    suspend fun insertOrUpdateUserBalance(entry: BudgetingDiary, monthAndYear: Long)

    suspend fun insertUserBalance(userBalance: UserBalance)

    suspend fun updateUserBalance(userBalance: UserBalance)

    fun getUserCurrentBalance(monthAndYear: Long): Flow<Double?>

    fun getUserBalance(monthAndYear: Long): Flow<Double?>

    suspend fun userBalanceExists(monthAndYear: Long): Boolean

    suspend fun getUserProfile(): UserProfileEntity?

    suspend fun insertOrUpdateUserProfile(user: UserProfileEntity)

    suspend fun logout(): Boolean

    suspend fun insertNoValidation(budgetingDiary: BudgetingDiary)

    suspend fun insertNoValidation(userBalance: UserBalance)

    suspend fun insertNoValidation(userProfile: UserProfileEntity)

    suspend fun insertNoValidation(budgeting: Budgeting)

    fun getAllPredict(): Flow<List<Predict>?>

    suspend fun insertPredict(predict: PredictEntity)

    suspend fun deletePredict(predict: PredictEntity)

    suspend fun getById(id: String): PredictResponse?
}
