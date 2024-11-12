package com.example.brofin.presentation.main.budgeting.diary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brofin.data.local.room.entity.BudgetingDiaryEntity
import com.example.brofin.domain.models.BudgetingDiary
import com.example.brofin.domain.repository.AuthRepository
import com.example.brofin.domain.repository.BrofinRepository
import com.example.brofin.utils.generateSampleBudgetingData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BudgetingDiaryViewModel @Inject constructor(
    private val brofinRepository: BrofinRepository,
    private val authRepository: AuthRepository
): ViewModel() {

    private fun insert(entry: BudgetingDiary) {
        viewModelScope.launch {
            brofinRepository.insertBudgetingDiaryEntry(entry)
        }
    }

    private fun getUID(): String {
        return authRepository.getCurrentUser()?.uid ?: ""
    }

    fun sampleInsert() {
        viewModelScope.launch {
            val sample = generateSampleBudgetingData(getUID(), 10)
            sample.forEach {
                insert(it)
            }
        }
    }


    fun deleteAll() {
        viewModelScope.launch {
            brofinRepository.deleteAllBudgetingDiaryEntries()
        }
    }

    fun getFilteredDiaries(
        startDate: Long? = null,
        endDate: Long? = null,
        isExpense: Boolean? = null,
        minAmount: Double? = null,
        maxAmount: Double? = null
    ): Flow<List<BudgetingDiaryEntity?>> {
        return brofinRepository.filterBudgetingDiaries(startDate, endDate, isExpense, minAmount, maxAmount)
    }
}