package com.example.brofin.presentation.setup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brofin.domain.models.Budgeting
import com.example.brofin.domain.models.UserBalance
import com.example.brofin.domain.repository.AuthRepository
import com.example.brofin.domain.repository.BrofinRepository
import com.example.brofin.utils.getCurrentMonthAndYearAsLong
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SetupIncomeViewModel @Inject constructor(
    private val brofinRepository: BrofinRepository,
) : ViewModel() {

    private fun setupBudgetingMonth(income: Double) {
        viewModelScope.launch {
            try {
                val currentMonthAndYear = getCurrentMonthAndYearAsLong()

                brofinRepository.insertBudget(
                    Budgeting(
                        monthAndYear = currentMonthAndYear,
                        total = income,
                        essentialNeedsLimit = income * 0.5,
                        wantsLimit = income * 0.3,
                        savingsLimit = income * 0.2
                    )
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }

    fun insertIncome(income: Double) {
        viewModelScope.launch {
            try {
                brofinRepository.insertUserBalance(
                    userBalance = UserBalance(
                        monthAndYear = getCurrentMonthAndYearAsLong(),
                        currentBalance = income,
                        balance = income,
                    )
                )

                setupBudgetingMonth(income)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
