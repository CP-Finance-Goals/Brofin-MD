package com.example.brofin.presentation.main.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brofin.domain.StateApp
import com.example.brofin.domain.models.Budgeting
import com.example.brofin.domain.models.UserBalance
import com.example.brofin.domain.repository.BrofinRepository
import com.example.brofin.domain.repository.RemoteDataRepository
import com.example.brofin.domain.repository.datastore.UserPreferencesRepository
import com.example.brofin.utils.ReponseUtils
import com.example.brofin.utils.getCurrentMonthAndYearAsLong
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val brofinRepository: BrofinRepository,
    private val remoteDataRepository: RemoteDataRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val _state  = MutableStateFlow<StateApp<Boolean>>(StateApp.Idle)
    val state: Flow<StateApp<Boolean>> = _state.asStateFlow()

    val budgetingUserIsExist =  brofinRepository.isUserBudgetingExist(getCurrentMonthAndYearAsLong())
        .onStart { emit(false) }
        .catch { emit(false) }

    val userBalance: Flow<Double?> =  brofinRepository.getUserCurrentBalance(getCurrentMonthAndYearAsLong())
        .onStart { emit(0.0) }
        .catch { emit(0.0) }

    val budgetingDiaries =  brofinRepository.getAllBudgetingDiaryEntries()
        .onStart { emit(emptyList()) }
        .catch { emit(emptyList()) }

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
        _state.value = StateApp.Loading
        viewModelScope.launch {
            Log.d(TAG, "Insert User Balance: $userBalance")
            try {
                val response = remoteDataRepository.addUserBalance(
                    amount = userBalance.balance!!,
                    monthAndYear = userBalance.monthAndYear,
                    currentBalance = userBalance.currentBalance!!
                )

                // Jika response data valid, lanjutkan ke insert budgeting dan update profile savings
                if (response.data != null) {
                    brofinRepository.insertUserBalance(userBalance)
                    val isBudgetingSuccess = insertBudgeting(userBalance.balance) // Insert budgeting
                    if (isBudgetingSuccess) {
                        updateProfileSavings(userBalance.balance * 0.2) // Update profile savings
                    }
                    _state.value = StateApp.Success(true)
                } else if (response.message == ReponseUtils.TOKEN_EXPIRED) {
                    userPreferencesRepository.updateToken(null)
                    _state.value = StateApp.Error("Token kadaluarsa, silakan login kembali")
                    brofinRepository.logout()
                } else {
                    Log.e(TAG, "Error on saving user balance data")
                    _state.value = StateApp.Error("Error ketika menyimpan data uang pengguna")
                }

            } catch (e: Exception) {
                Log.e(TAG, "Error on saving user balance data", e)
                _state.value = StateApp.Error("Error ketika menyimpan data uang pengguna")
            }
        }
    }

    fun inserBudgetingWithNewAPI(userBalance: UserBalance) {
        _state.value = StateApp.Loading
        viewModelScope.launch {
            Log.d(TAG, "Insert User Balance: $userBalance")
            try {

                val userData = brofinRepository.getUserProfile()

                if (userData == null) {
                    _state.value = StateApp.Error("Profil pengguna tidak ditemukan.")
                    return@launch
                }

                val currentSavings = userData.savings ?: 0.0
                val updatedsavings = currentSavings.plus((userBalance.balance!!.times(0.2)))

                val response = remoteDataRepository.setupBudgeting(
                    monthAndYear = userBalance.monthAndYear.toString().toRequestBody("text/plain".toMediaTypeOrNull()),
                    total = userBalance.balance.toString().toRequestBody("text/plain".toMediaTypeOrNull()),
                    essentialNeedsLimit = (userBalance.balance.times(0.5)).toString().toRequestBody("text/plain".toMediaTypeOrNull()),
                    wantsLimit = (userBalance.balance.times(0.3)).toString().toRequestBody("text/plain".toMediaTypeOrNull()),
                    balance = userBalance.balance.toString().toRequestBody("text/plain".toMediaTypeOrNull()),
                    currentBalance = userBalance.currentBalance.toString().toRequestBody("text/plain".toMediaTypeOrNull()),
                    isReminder = "false".toRequestBody("text/plain".toMediaTypeOrNull()),
                    savingsLimit = (userBalance.balance.times(0.2)).toString().toRequestBody("text/plain".toMediaTypeOrNull()),
                    savings = updatedsavings.toString().toRequestBody("text/plain".toMediaTypeOrNull()),
                    dob = "2000-01-01".toRequestBody("text/plain".toMediaTypeOrNull()),
                    username = userData.name?.toRequestBody("text/plain".toMediaTypeOrNull()) ?: " ".toRequestBody("text/plain".toMediaTypeOrNull()),
                    image = preparePhotoPart(null)
                )

                if ( response.message == ReponseUtils.ADD_USER_BALANCE_SUCCESS) {
                    brofinRepository.insertUserBalance(userBalance)
                    brofinRepository.insertOrUpdateUserProfile(userData.copy(savings = updatedsavings))
                    brofinRepository.insertBudget(
                        Budgeting(
                            monthAndYear = userBalance.monthAndYear,
                            total = userBalance.balance,
                            essentialNeedsLimit = userBalance.balance.times(0.5),
                            wantsLimit = userBalance.balance.times(0.3),
                            savingsLimit = userBalance.balance.times(0.2),
                        )
                    )
                    _state.value = StateApp.Success(true)
                } else if (response.message == ReponseUtils.TOKEN_EXPIRED) {
                    userPreferencesRepository.updateToken(null)
                    _state.value = StateApp.Error("Token kadaluarsa, silakan login kembali")
                    brofinRepository.logout()
                } else {
                    Log.e(TAG, "Error on saving user balance data")
                    _state.value = StateApp.Error("Error ketika menyimpan data uang pengguna")
                }

            } catch (e: Exception) {
                Log.e(TAG, "Error on saving user balance data", e)
                _state.value = StateApp.Error("Error ketika menyimpan data uang pengguna")
            }
        }

    }

    private fun preparePhotoPart(photoFile: File?): MultipartBody.Part? {
        return photoFile?.let {
            val requestFile = it.asRequestBody("image/*".toMediaTypeOrNull()) // Set the MIME type for the image
            MultipartBody.Part.createFormData("photo", it.name, requestFile)
        }
    }


    // Update user profile savings
    private suspend fun updateProfileSavings(newSavings: Double) {
        try {
            val userNow = brofinRepository.getUserProfile()

            if (userNow != null) {
                val currentSavings = userNow.savings ?: 0.0
                val updatedSavings = currentSavings + newSavings

                val updatedUser = userNow.copy(savings = updatedSavings)
                val savingsRequestBody = updatedSavings.toString().toRequestBody("text/plain".toMediaTypeOrNull())
                val name = userNow.name?.toRequestBody("text/plain".toMediaTypeOrNull()) ?: " ".toRequestBody("text/plain".toMediaTypeOrNull())
                val gender = "Anonim".toRequestBody("text/plain".toMediaTypeOrNull())
                val dob = "2000-01-01".toRequestBody("text/plain".toMediaTypeOrNull())
                val photoPart = preparePhotoPart(null)

                val response = remoteDataRepository.editUserProfile(
                    username = name,
                    gender = gender,
                    dob = dob   ,
                    savings = savingsRequestBody,
                    photo = photoPart
                )

                if (response.message == ReponseUtils.UPDATED_USER_PROFILE_SUCCESS) {
                    brofinRepository.insertOrUpdateUserProfile(updatedUser)
                    Log.d(TAG, "User profile savings updated successfully.")
                } else {
                    _state.value = StateApp.Error("Gagal memperbarui tabungan profil pengguna.")
                    Log.e(TAG, "Failed to update user profile savings.")
                }
            } else {

                _state.value = StateApp.Error("Profil pengguna tidak ditemukan.")
                Log.e(TAG, "User profile not found")
            }
        } catch (e: Exception) {
            _state.value = StateApp.Error("Error ketika memperbarui profil: ${e.message}")
            Log.e("Profile Update", "Error updating profile: ${e.message}")
        }
    }


    // Insert budgeting data
    private suspend fun insertBudgeting(income: Double): Boolean {
        return try {
            val response = remoteDataRepository.addBudgeting(
                monthAndYear = getCurrentMonthAndYearAsLong(),
                total = income,
                essentialNeedsLimit = income * 0.5,
                wantsLimit = income * 0.3,
                savingsLimit = income * 0.2,
                isReminder = false
            )

            if (response.msg == ReponseUtils.ADD_BUDGETING_SUCCESS) {
                brofinRepository.insertBudget(
                    Budgeting(
                        monthAndYear = getCurrentMonthAndYearAsLong(),
                        total = income,
                        essentialNeedsLimit = income * 0.5,
                        wantsLimit = income * 0.3,
                        savingsLimit = income * 0.2,
                        isReminder = false
                    )
                )
                Log.d(TAG, "Budgeting data inserted successfully.")
                true
            } else {
                _state.value = StateApp.Error("Gagal menyimpan data budgeting.")
                Log.e(TAG, "Failed to insert budgeting data.")
                false
            }
        } catch (e: Exception) {
            _state.value = StateApp.Error("Error ketika menyimpan data budgeting: ${e.message}")
            Log.e(TAG, "Error on saving budgeting data", e)
            false
        }
    }


    companion object {
        private const val TAG = "HomeViewModel"
    }
}
