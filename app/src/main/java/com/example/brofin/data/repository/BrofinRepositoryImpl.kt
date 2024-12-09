package com.example.brofin.data.repository

import android.util.Log
import com.example.brofin.data.local.room.dao.BudgetingDao
import com.example.brofin.data.local.room.dao.BudgetingDiaryDao
import com.example.brofin.data.local.room.dao.PredictDao
import com.example.brofin.data.local.room.dao.UserBalanceDao
import com.example.brofin.data.local.room.dao.UserProfileDao
import com.example.brofin.data.local.room.entity.BudgetingWithDiaries
import com.example.brofin.data.local.room.entity.BudgetingDiaryEntity
import com.example.brofin.data.local.room.entity.PredictEntity
import com.example.brofin.data.local.room.entity.UserBalanceEntity
import com.example.brofin.data.local.room.entity.UserProfileEntity
import com.example.brofin.data.mapper.toBudgetingDiary
import com.example.brofin.data.mapper.toBudgetingDiaryEntity
import com.example.brofin.data.mapper.toBudgetingEntity
import com.example.brofin.data.mapper.toPredict
import com.example.brofin.data.mapper.toPredictResponse
import com.example.brofin.data.mapper.toUserBalanceEntity
import com.example.brofin.data.mapper.toUserProfile
import com.example.brofin.domain.models.Budgeting
import com.example.brofin.domain.models.BudgetingDiary
import com.example.brofin.domain.models.Predict
import com.example.brofin.domain.models.PredictResponse
import com.example.brofin.domain.models.UserBalance
import com.example.brofin.domain.models.UserProfile
import com.example.brofin.domain.repository.BrofinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

class BrofinRepositoryImpl(
    private val userBalanceDao: UserBalanceDao,
    private val predictDao: PredictDao,
    private val budgetingDiaryDao: BudgetingDiaryDao,
    private val budgetingDao: BudgetingDao,
    private val userProfileDao: UserProfileDao,
): BrofinRepository {

    override suspend fun insertUserBalance(userBalance: UserBalance) {
        userBalanceDao.insertOrUpdateUserBalance(userBalance.toUserBalanceEntity())
    }

    override suspend fun getBudgetingByMonth(monthAndYear: Long): Budgeting? {
        return budgetingDao.getBudgetingByMonth(monthAndYear)?.toBudgetingDiary()
    }

    override suspend fun insertOrUpdateUserBalance(entry: BudgetingDiary, monthAndYear: Long) {
        try {
            val currentBalance = userBalanceDao.getCurrentBalance(monthAndYear) ?: 0.0
            val updatedBalance = currentBalance - entry.amount
            val existingBalance = userBalanceDao.getUserBalanceByMonthAndYear(monthAndYear).firstOrNull()
            if (existingBalance != null) {
                userBalanceDao.updateBalance(monthAndYear, updatedBalance)
            } else {
                userBalanceDao.insertOrUpdateUserBalance(
                    UserBalanceEntity(
                        monthAndYear = entry.monthAndYear,
                        currentBalance = updatedBalance
                    )
                )
            }
            budgetingDiaryDao.insertBudgetingDiary(entry.toBudgetingDiaryEntity())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun updateUserBalance(userBalance: UserBalance) {
        userBalanceDao.insertOrUpdateUserBalance(userBalance.toUserBalanceEntity())
    }

    override fun getUserCurrentBalance(monthAndYear: Long): Flow<Double?> {
        return userBalanceDao.getUserBalanceByMonthAndYear(monthAndYear).map { it?.currentBalance }
    }

    override fun getTotalAmountByCategoryAndMonth(
        categoryIds: List<Int>,
        monthAndYear: Long
    ): Flow<Double> {
        return budgetingDiaryDao.getTotalAmountByCategoryAndMonth(categoryIds, monthAndYear)
    }

    override suspend fun getUserFlow(): Flow<UserProfile?> {
        return userProfileDao.getUserProfileFlow().map {
            it?.toUserProfile()
        }
    }

    override suspend fun getUserBalanceData(monthAndYear: Long): UserBalanceEntity? {
        return userBalanceDao.getUserBalanceData(monthAndYear)
    }

    override suspend fun getCurrentBalance(monthAndYear: Long): Double {
        return userBalanceDao.getCurrentBalance(monthAndYear) ?: 0.0
    }

    override fun getUserBalance(monthAndYear: Long): Flow<Double?> {
        return userBalanceDao.getUserBalanceByMonthAndYear(monthAndYear).map { it?.balance }
    }

    override suspend fun userBalanceExists(monthAndYear: Long): Boolean {
        return userBalanceDao.userBalanceExists(monthAndYear)
    }

    override fun getTotalSavings(): Flow<Double?> {
        return userProfileDao.getTotalSavings()
    }

    override suspend fun getUserProfile(): UserProfileEntity? {
        return userProfileDao.getUserProfile()
    }

    override suspend fun insertOrUpdateUserProfile(user: UserProfileEntity) {
        userProfileDao.insertOrUpdateUserProfile(user)
    }

    override suspend fun logout(): Boolean {
        try {
            userProfileDao.deleteAllUserProfiles()
            userBalanceDao.deleteAllUserBalances()
            budgetingDiaryDao.deleteAllBudgetingDiaries()
            budgetingDao.deleteAllBudgeting()
            return true
        } catch (e: Exception) {
            Log.e(TAG, "Error saat logout", e)
            return false
        }
    }

    override suspend fun insertNoValidation(budgetingDiary: BudgetingDiary) {
        budgetingDiaryDao.insertBudgetingDiary(budgetingDiary.toBudgetingDiaryEntity())
    }

    override suspend fun insertNoValidation(userBalance: UserBalance) {
        userBalanceDao.insertOrUpdateUserBalance(userBalance.toUserBalanceEntity())
    }

    override suspend fun insertNoValidation(userProfile: UserProfileEntity) {
        userProfileDao.insertOrUpdateUserProfile(userProfile)
    }

    override suspend fun insertNoValidation(budgeting: Budgeting) {
        budgetingDao.insertBudget(budgeting.toBudgetingEntity())
    }

    override fun getAllPredict(): Flow<List<Predict>?> {
        try {
           return predictDao.getAllPredict().map { listEntity ->
               listEntity?.map {
                     it.toPredict()
               }
           }
        } catch (e: Exception){
            Log.e(TAG, "Error when get all predict $e")
            throw e
        }
    }

    override suspend fun insertPredict(predict: PredictEntity) {
        try {
            predictDao.insertPredict(predict)
        }catch (e: Exception){
            Log.e(TAG, "Error when insert Predict $e")
        }
    }

    override suspend fun deletePredict(predict: PredictEntity) {
        try {
            predictDao.deletePredict(predict)
        } catch (e: Exception){
            Log.e(TAG, "Error when delete product $e")
        }
    }

    override suspend fun getById(id: String): PredictResponse? {
        try {
            return predictDao.getPredictById(id)?.toPredictResponse()
        } catch (e: Exception){
            Log.e(TAG, "Error when getByid $e")
            return null
        }
    }

    override suspend fun insertBudget(budget: Budgeting) {
        budgetingDao.insertBudget(budget.toBudgetingEntity())
    }

    override fun getBudgetWithDiaries(monthAndYear: Long): Flow<BudgetingWithDiaries> {
        return budgetingDao.getBudgetWithDiaries(monthAndYear)
    }

    override fun isUserBudgetingExist(monthAndYear: Long): Flow<Boolean> {
        return budgetingDao.isUserBudgetingExist(monthAndYear)
    }

    override suspend fun insertBudgetingDiaryEntry(entry: BudgetingDiary) {
        try {
            val currentBalance = userBalanceDao.getCurrentBalance(entry.monthAndYear) ?: 0.0
            if (currentBalance < entry.amount) {
                throw IllegalArgumentException("Saldo tidak mencukupi untuk melakukan pengeluaran sebesar ${entry.amount}.")
            }

            val updatedBalance = currentBalance - entry.amount

            val existingBalance = userBalanceDao.getUserBalanceByMonthAndYear(entry.monthAndYear).firstOrNull()
            if (existingBalance != null) {
                userBalanceDao.updateBalance(entry.monthAndYear, updatedBalance)
            } else {
                userBalanceDao.insertOrUpdateUserBalance(
                    UserBalanceEntity(
                        monthAndYear = entry.monthAndYear,
                        balance = currentBalance,
                        currentBalance = updatedBalance
                    )
                )
            }

            budgetingDiaryDao.insertBudgetingDiary(entry.toBudgetingDiaryEntity())
        } catch (e: IllegalArgumentException) {
            Log.e(TAG, e.message ?: "Error pada input data")
            throw e
        } catch (e: Exception) {
            Log.e(TAG, "Error saat menyimpan data budgeting diary", e)
            throw e
        }
    }

    override fun getAllBudgetingDiaryEntries(): Flow<List<BudgetingDiary?>> {
        return budgetingDiaryDao.getLatestBudgetingDiaries().map { entries ->
            entries.map { it.toBudgetingDiary() }
        }
    }

    override suspend fun updateBudgetingDiaryEntry(entry: BudgetingDiary) {
        budgetingDiaryDao.updateBudgetingDiary(entry.toBudgetingDiaryEntity())
    }

    override suspend fun deleteBudgetingDiaryEntry(entryId: Int) {
        budgetingDiaryDao.deleteBudgetingDiaryById(entryId)
    }

    override suspend fun deleteAllBudgetingDiaryEntries() {
        budgetingDiaryDao.deleteAllBudgetingDiaries()
    }

    override fun getBudgetingDiaryEntriesByDateRange(
        startDate: Long,
        endDate: Long
    ): Flow<List<BudgetingDiary?>> {
        return budgetingDiaryDao.getBudgetingDiariesByDateRange(startDate, endDate).map { entries ->
            entries.map { it?.toBudgetingDiary() }
        }
    }

    override fun filterBudgetingDiaries(
        monthAndYear: Long?,
        startDate: Long?,
        endDate: Long?,
        minAmount: Double?,
        maxAmount: Double?,
        sortBy: String,
        sortOrder: String
    ): Flow<List<BudgetingDiaryEntity?>> {
        return budgetingDiaryDao.filterBudgetingDiaries(
            monthAndYear = monthAndYear,
            startDate = startDate,
            endDate = endDate,
            minAmount = minAmount,
            maxAmount = maxAmount,
            sortBy = sortBy,
            sortOrder = sortOrder
        )
    }

    override fun getTotalExpenses(monthAndYear: Long): Flow<Double?> {
        return budgetingDiaryDao.getTotalExpensesMonth(monthAndYear)
    }

    companion object {
        const val TAG = "BrofinRepositoryImpl"
    }
}
