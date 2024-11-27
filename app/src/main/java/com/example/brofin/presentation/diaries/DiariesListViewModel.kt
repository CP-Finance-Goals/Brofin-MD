package com.example.brofin.presentation.diaries

import androidx.lifecycle.ViewModel
import com.example.brofin.data.local.room.entity.BudgetingDiaryEntity
import com.example.brofin.domain.repository.AuthRepository
import com.example.brofin.domain.repository.BrofinRepository
import com.example.brofin.utils.getCurrentMonthAndYearAsLong
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class DiariesListViewModel @Inject constructor(
    private val brofinRepository: BrofinRepository,
    private val authRepository: AuthRepository
): ViewModel() {



    fun getFilteredDiaries(
        startDate: Long? = null,
        endDate: Long? = null,
        minAmount: Double? = null,
        maxAmount: Double? = null,
        sortBy: String = "date",
        sortOrder: String = "asc"
    ): Flow<List<BudgetingDiaryEntity?>> {
        return brofinRepository.filterBudgetingDiaries(
            userId = authRepository.getCurrentUser()?.uid ?: "",
            monthAndYear = null,
            startDate = startDate,
            endDate = endDate,
            minAmount = minAmount,
            maxAmount = maxAmount,
            sortBy =  sortBy,
            sortOrder = sortOrder
        )
    }

}