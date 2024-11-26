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
import java.util.concurrent.TimeoutException
import javax.inject.Inject

@HiltViewModel
class AddExpensesViewModel @Inject constructor(
    private val brofinRepository: BrofinRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _addState = MutableStateFlow<StateApp<Boolean>>(StateApp.Idle)
    val addState = _addState.asStateFlow()

    private val _amount = MutableStateFlow("")
    val amount = _amount.asStateFlow()
    private val _description = MutableStateFlow("")
    val description = _description.asStateFlow()
    private val _selectedCategory = MutableStateFlow("Pilih Kategori")
    val selectedCategory = _selectedCategory.asStateFlow()
    private val _date = MutableStateFlow(System.currentTimeMillis())
    val date = _date.asStateFlow()
    private val _idCategory = MutableStateFlow(0)
    val idCategory = _idCategory.asStateFlow()

    fun setCategoryId(id: Int) {
        _idCategory.value = id
    }

    fun setAmount(amount: String) {
        _amount.value = amount
    }

    fun setDescription(description: String) {
        _description.value = description
    }

    fun setCategory(category: String) {
        _selectedCategory.value = category
    }


    fun setDate(date: Long) {
        _date.value = date
    }

    fun reset() {
        _amount.value = ""
        _description.value = ""
        _selectedCategory.value = "Pilih Kategori"
        _date.value = System.currentTimeMillis()
    }

    fun resetState() {
        _addState.value = StateApp.Idle
    }

    private fun isDateValid(date: Long): Boolean {
        val todayStart = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis

        val todayEnd = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 59)
            set(Calendar.MILLISECOND, 999)
        }.timeInMillis

        val yesterdayStart = Calendar.getInstance().apply {
            timeInMillis = todayStart
            add(Calendar.DAY_OF_YEAR, -1)
        }.timeInMillis

        return date in yesterdayStart..todayEnd
    }


    fun insert(
        date: Long,
        photoUri: String? = null,
        description: String? = null,
        amount: Double,
        categoryId: Int
    ) {
        viewModelScope.launch {
            _addState.value = StateApp.Loading

            val userId = authRepository.getCurrentUser()?.uid
            if (userId == null) {
                _addState.value = StateApp.Error("Kamu Belum Login")
                return@launch
            }

            Log.d("AddExpensesViewModel", "insert: $date")

            val validation = decodeMonthAndYearFromLong(date)
            if (validation != getCurrentMonthAndYearInIndonesian()) {
                Log.d("AddExpensesViewModel", "insert: $validation ${getCurrentMonthAndYearInIndonesian()}")
                _addState.value = StateApp.Error("Tanggal tidak sesuai")
                return@launch
            }

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
                        monthAndYear = getCurrentMonthAndYearAsLong(),
                    ),
                )

                _addState.value = StateApp.Success(true)
            } catch (e: IllegalArgumentException) {
                Log.e("AddExpensesViewModel", "Insert Error: IllegalArgumentException", e)
                _addState.value = StateApp.Error(e.message ?: "Saldo tidak mencukupi")
            } catch (e: TimeoutException) {
                Log.e("AddExpensesViewModel", "Insert Error: TimeoutException", e)
                _addState.value = StateApp.Error("Waktu habis, silakan coba lagi")
            } catch (e: Exception) {
                Log.e("AddExpensesViewModel", "Insert Error: General Exception", e)
                _addState.value = StateApp.Error("Gagal menambahkan data: ${e.message}")
            }
        }
    }

}
