package com.example.brofin.presentation.expenses

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brofin.domain.StateApp
import com.example.brofin.domain.models.BudgetingDiary
import com.example.brofin.domain.repository.AuthRepository
import com.example.brofin.domain.repository.BrofinRepository
import com.example.brofin.utils.decodeMonthAndYearFromLong
import com.example.brofin.utils.getCurrentMonthAndYearAsLong
import com.example.brofin.utils.getCurrentMonthAndYearInIndonesian
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class AddExpensesViewModel @Inject constructor(
    private val brofinRepository: BrofinRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _addState = MutableStateFlow<StateApp<Boolean>>(StateApp.Idle)
    val addState = _addState.asStateFlow()

    fun resetState() {
        _addState.value = StateApp.Idle
    }

    private fun isDateValid(date: Long): Boolean {
        // Awal hari ini
        val todayStart = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis

        // Akhir hari ini
        val todayEnd = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 59)
            set(Calendar.MILLISECOND, 999)
        }.timeInMillis

        // Awal hari sebelumnya
        val yesterdayStart = Calendar.getInstance().apply {
            timeInMillis = todayStart
            add(Calendar.DAY_OF_YEAR, -1)
        }.timeInMillis

        // Valid jika tanggal berada di antara hari sebelumnya dan akhir hari ini
        return date in yesterdayStart..todayEnd
    }


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
            Log.d("AddExpensesViewModel", "insert: $date")

//            hanya di bulan yang sama yang bisa menambahkan
            val validation = decodeMonthAndYearFromLong(date)
            if (validation != getCurrentMonthAndYearInIndonesian()){
                Log.d("AddExpensesViewModel", "insert: $validation ${getCurrentMonthAndYearInIndonesian()}")
                _addState.value = StateApp.Error("Tanggal tidak sesuai")
                return@launch
            }

            // hanya bisa menambahkan data pada hari ini atau hari sebelumnya
            if (!isDateValid(date)) {
                _addState.value = StateApp.Error("Tanggal tidak valid")
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
                        monthAndYear = getCurrentMonthAndYearAsLong(),
                    ),
                )

                _addState.value = StateApp.Success(true)
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
