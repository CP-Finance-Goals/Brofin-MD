package com.example.brofin.presentation.main.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brofin.data.mapper.toUserProfileEntity
import com.example.brofin.domain.models.Budgeting
import com.example.brofin.domain.models.UserBalance
import com.example.brofin.domain.models.UserProfile
import com.example.brofin.domain.repository.AuthRepository
import com.example.brofin.domain.repository.BrofinRepository
import com.example.brofin.domain.repository.datastore.UserPreferencesRepository
import com.example.brofin.utils.getCurrentMonthAndYearAsLong
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val brofinRepository: BrofinRepository,
) : ViewModel() {

    @OptIn(ExperimentalCoroutinesApi::class)
    val budgetingUserIsExist =  brofinRepository.isUserBudgetingExist(getCurrentMonthAndYearAsLong())
        .onStart { emit(false) }
        .catch { emit(false) }

    @OptIn(ExperimentalCoroutinesApi::class)
    val userBalance: Flow<Double?> =  brofinRepository.getUserCurrentBalance(getCurrentMonthAndYearAsLong())
        .onStart { emit(0.0) }
        .catch { emit(0.0) }

    @OptIn(ExperimentalCoroutinesApi::class)
    val budgetingDiaries =  brofinRepository.getAllBudgetingDiaryEntries()
        .onStart { emit(emptyList()) }
        .catch { emit(emptyList()) }

    @OptIn(ExperimentalCoroutinesApi::class)
    val totalIncome = brofinRepository.getUserBalance(getCurrentMonthAndYearAsLong())
        .onStart {emit(0.0)}
        .catch {
            Log.e(TAG, "Error fetching total income", it)
            emit(0.0)
        }


    val totalExpenses = brofinRepository.getTotalExpenses(getCurrentMonthAndYearAsLong())
        .onStart { emit(0.0) }
        .catch {
            Log.e(TAG, "Error fetching total expenses", it)
            emit(0.0)
        }

    val totalSavings = brofinRepository.getTotalSavings()

    fun insertUserBalance(userBalance: UserBalance) {
        viewModelScope.launch {
            Log.d(TAG, "Insert User Balance: $userBalance")
            try {
                brofinRepository.insertUserBalance(userBalance)
                insertBudgeting(userBalance.balance!!)
            }catch (e: Exception) {
                Log.e(TAG, "Error on saving user balance data", e)
            }
        }
    }

    private fun updateProfileSavings(newSavings: Double) {
        viewModelScope.launch {
            try {
                val userNow = brofinRepository.getUserProfile()

                if (userNow != null) {
                    val currentSavings = userNow.savings
                    val updatedSavings = currentSavings?.plus(newSavings)
                    val updatedUser = userNow.copy(savings = updatedSavings)
                    brofinRepository.insertOrUpdateUserProfile(updatedUser)
                } else {
                    Log.e(TAG, "User profile not found")
                }
            } catch (e: Exception) {
                Log.e("Profile Update", "Error updating profile: ${e.message}")
            }
        }
    }

    private fun insertBudgeting(income: Double) {
        viewModelScope.launch {
            try {
                brofinRepository.insertBudget(
                    budget = Budgeting(
                        monthAndYear = getCurrentMonthAndYearAsLong(),
                        total = income,
                        essentialNeedsLimit = income * 0.5,
                        wantsLimit = income * 0.3,
                        savingsLimit = income * 0.2,
                        isReminder = false
                    )
                )

                updateProfileSavings(income * 0.2)
            } catch (e: IllegalArgumentException){
                Log.e(TAG, e.message ?: "Error on input data")
            } catch (e: Exception) {
                Log.e(TAG, "Error on saving budgeting data", e)
            }
        }
    }

    companion object {
        private const val TAG = "HomeViewModel"
    }
}
