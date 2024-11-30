package com.example.brofin.presentation.expenses

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brofin.data.local.room.entity.UserBalanceEntity
import com.example.brofin.domain.StateApp
import com.example.brofin.domain.models.BudgetingDiary
import com.example.brofin.domain.repository.AuthRepository
import com.example.brofin.domain.repository.BrofinRepository
import com.example.brofin.domain.repository.RemoteDataRepository
import com.example.brofin.utils.Expense
import com.example.brofin.utils.ReponseUtils
import com.example.brofin.utils.decodeMonthAndYearFromLong
import com.example.brofin.utils.getCurrentMonthAndYearAsLong
import com.example.brofin.utils.getCurrentMonthAndYearInIndonesian
import com.example.brofin.utils.toIndonesianCurrency2
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream
import java.util.Calendar
import java.util.concurrent.TimeoutException
import javax.inject.Inject

@HiltViewModel
class AddExpensesViewModel @Inject constructor(
    private val brofinRepository: BrofinRepository,
    private val remoteDataRepository: RemoteDataRepository
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

    private val lisIdKebutuhan = Expense.budgetAllocations[0].kategori.map {
        it.id
    }

    private val _limitKebutuhan = MutableStateFlow(0.0)
    private val _limitKeinginan = MutableStateFlow(0.0)

    private val lisIdKeinginan = Expense.budgetAllocations[1].kategori.map {
        it.id
    }

    init {
        _date.value = System.currentTimeMillis()
        fetchTotalAmount(lisIdKebutuhan, getCurrentMonthAndYearAsLong())
        fetchTotalAmount(lisIdKeinginan, getCurrentMonthAndYearAsLong())
    }
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

    private suspend fun getBudgetingByMonth(monthAndYear: Long) = brofinRepository.getBudgetingByMonth(monthAndYear)

    private val _totalAmountKebutuhan = MutableStateFlow<Double>(0.0)
    private val _totalAmountKeinginan = MutableStateFlow<Double>(0.0)


    private fun fetchTotalAmount(categoryIds: List<Int>, monthAndYear: Long) {
        viewModelScope.launch {
            brofinRepository.getTotalAmountByCategoryAndMonth(categoryIds, monthAndYear)
                .collect { amount ->
                    if (categoryIds == lisIdKebutuhan) {
                        _totalAmountKebutuhan.value = amount
                    } else {
                        _totalAmountKeinginan.value = amount
                    }
                }
        }
    }

    // Fungsi untuk meresize gambar
    private fun resizeImage(photoUri: Uri, contentResolver: ContentResolver, context: Context, maxWidth: Int, maxHeight: Int): File? {
        try {
            // Membaca gambar dari URI ke dalam Bitmap
            val inputStream = contentResolver.openInputStream(photoUri)
            val originalBitmap = BitmapFactory.decodeStream(inputStream)

            // Menghitung rasio untuk menjaga proporsi
            val aspectRatio = originalBitmap.width.toFloat() / originalBitmap.height.toFloat()
            val newWidth = if (originalBitmap.width > originalBitmap.height) maxWidth else (maxHeight * aspectRatio).toInt()
            val newHeight = if (originalBitmap.height > originalBitmap.width) maxHeight else (newWidth / aspectRatio).toInt()

            // Membuat Bitmap yang diubah ukurannya
            val resizedBitmap = Bitmap.createScaledBitmap(originalBitmap, newWidth, newHeight, false)

            // Simpan Bitmap yang diubah ukurannya ke file sementara di cache directory
            val resizedFile = File(context.cacheDir, "resized_${System.currentTimeMillis()}.jpg")
            val outputStream = FileOutputStream(resizedFile)  // Pastikan menggunakan FileOutputStream yang sesuai
            resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream) // Kompresi 80% kualitas
            outputStream.flush()
            outputStream.close()

            return resizedFile
        } catch (e: Exception) {
            Log.e("Image Resize", "Error resizing image", e)
        }
        return null
    }

    // Fungsi untuk menyiapkan bagian foto menjadi MultipartBody.Part
    private fun preparePhotoPart(photoUri: Uri?, contentResolver: ContentResolver, context: Context): MultipartBody.Part? {
        return if (photoUri != null) {
            try {
                // Resize gambar sebelum diproses
                val resizedFile = resizeImage(photoUri, contentResolver, context, maxWidth = 800, maxHeight = 800) // Sesuaikan ukuran max jika perlu

                resizedFile?.let { file ->
                    // Membuat MultipartBody.Part dari file yang sudah disalin
                    val requestBody = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
                    MultipartBody.Part.createFormData("image", file.name, requestBody)
                }
            } catch (e: Exception) {
                Log.e("File Error", "Failed to process the image", e)
                null
            }
        } else {
            null
        }
    }


    fun insert(
        date: Long,
        photoUri: Uri? = null,
        description: String? = null,
        amount: Double,
        categoryId: Int,
        contentResolver: ContentResolver,
        context: Context
    ) {
        viewModelScope.launch {
            _addState.value = StateApp.Loading

            Log.d("AddExpensesViewModel", "insert: $date")

            val validation = decodeMonthAndYearFromLong(date)
            if (validation != getCurrentMonthAndYearInIndonesian()) {
                Log.d(
                    "AddExpensesViewModel",
                    "insert: $validation ${getCurrentMonthAndYearInIndonesian()}"
                )
                _addState.value = StateApp.Error("Tanggal tidak sesuai")
                return@launch
            }

            if (!isDateValid(date)) {
                _addState.value = StateApp.Error("Tanggal tidak valid")
                return@launch
            }
            val budgeting = getBudgetingByMonth(getCurrentMonthAndYearAsLong())

            _limitKebutuhan.value = budgeting?.essentialNeedsLimit ?: 0.0
            _limitKeinginan.value = budgeting?.wantsLimit ?: 0.0

            if (categoryId in lisIdKebutuhan) {
                if (amount > _limitKebutuhan.value - _totalAmountKebutuhan.value) {
                    _addState.value = StateApp.Error("Uang Alokasi Kebutuhan tidak mencukupi" +
                            " sisa uang kamu untuk kebutuhan = ${(_limitKebutuhan.value - _totalAmountKebutuhan.value).toIndonesianCurrency2()}")
                    return@launch
                }
            } else {
                if (amount > _limitKeinginan.value - _totalAmountKeinginan.value) {
                    _addState.value = StateApp.Error("Uang Alokasi Keinginan tidak mencukupi" +
                            "sisa uang kamu untuk keinginan = ${(_limitKeinginan.value - _totalAmountKeinginan.value).toIndonesianCurrency2()}")
                    return@launch
                }
            }

            val currentBalance = brofinRepository.getCurrentBalance(getCurrentMonthAndYearAsLong())

            if (currentBalance < amount) {
                _addState.value = StateApp.Error("Saldo tidak mencukupi untuk melakukan pengeluaran sebesar ${amount}.")
                return@launch
            }

            val updateBalance = currentBalance - amount
            val userBalance = brofinRepository.getUserBalanceData(getCurrentMonthAndYearAsLong())

            if (userBalance == null) {
                _addState.value = StateApp.Error("Kamu belum memasukan pendapatan bulan ini pada halaman home.")
            }



            try {
                val monthAndYearData = getCurrentMonthAndYearAsLong().toString().toRequestBody("text/plain".toMediaTypeOrNull())
                val dateData = date.toString().toRequestBody("text/plain".toMediaTypeOrNull())
                val descriptionData = description?.toRequestBody("text/plain".toMediaTypeOrNull())
                val amountData = amount.toString().toRequestBody("text/plain".toMediaTypeOrNull())
                val categoryIdData = categoryId.toString().toRequestBody("text/plain".toMediaTypeOrNull())
                val photoData = preparePhotoPart(photoUri, contentResolver, context)

                val response = remoteDataRepository.addBudgetingDiary(
                    monthAndYear = monthAndYearData,
                    date = dateData,
                    description = descriptionData,
                    amount = amountData,
                    categoryId = categoryIdData,
                    photo = photoData
                )

                if (response.message == ReponseUtils.ADD_DIARY_SUCCES){
                    brofinRepository.insertBudgetingDiaryEntry(
                        BudgetingDiary(
                            date = date,
                            description = description,
                            amount = amount,
                            categoryId = categoryId,
                            photoUrl = response.photoUrl,
                            monthAndYear = getCurrentMonthAndYearAsLong(),
                        ),
                    )
                    _addState.value = StateApp.Success(true)
                } else {
                    _addState.value = StateApp.Error("Gagal menambahkan data")
                }

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

