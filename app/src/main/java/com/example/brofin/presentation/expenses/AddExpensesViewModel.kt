package com.example.brofin.presentation.expenses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brofin.domain.StateApp
import com.example.brofin.domain.models.BudgetingDiary
import com.example.brofin.domain.repository.AuthRepository
import com.example.brofin.domain.repository.BrofinRepository
import com.example.brofin.utils.PaymentMethode
import com.example.brofin.utils.getCurrentMonthAndYearAsLong
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddExpensesViewModel @Inject constructor(
    private val brofinRepository: BrofinRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _addState = MutableStateFlow<StateApp<Boolean>>(StateApp.Idle)
    val addState = _addState.asStateFlow()

    fun insert(
        date: Long,
        photoUri: String? = null,
        description: String? = null,
        amount: Double,
        categoryId: Int,
        isExpense: Boolean = true,
    ) {
        viewModelScope.launch {
            _addState.value = StateApp.Loading

            val userId = authRepository.getCurrentUser()?.uid
            if (userId == null) {
                _addState.value = StateApp.Error("Kamu Belum Login")
                return@launch
            }

            try {
                brofinRepository.insertBudgetingDiaryEntry(
                    BudgetingDiary(
                        userId = userId,
                        date = date,
                        photoUri = photoUri,
                        description = description,
                        amount = amount,
                        categoryId = categoryId,
                        isExpense = isExpense,
                        monthAndYear = getCurrentMonthAndYearAsLong()
                    ),
                )

                _addState.value = StateApp.Success(true) // Sukses menambahkan data
            } catch (e: IllegalArgumentException) {
                // Jika terjadi kesalahan seperti saldo kurang
                _addState.value = StateApp.Error(e.message ?: "Saldo tidak mencukupi")
            } catch (e: Exception) {
                // Jika ada error lain
                _addState.value = StateApp.Error("Gagal menambahkan data: ${e.message}")
            }
        }
    }
}
