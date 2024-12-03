package com.example.brofin.data.repository

import android.util.Log
import com.example.brofin.data.remote.ApiService
import com.example.brofin.data.remote.dto.AddDiaryResponseDto
import com.example.brofin.data.remote.dto.AddExpensesResponseDto
import com.example.brofin.data.remote.dto.SetupBudgeingResponseDto
import com.example.brofin.data.remote.dto.AddOrUpateBudgetingResponseDto
import com.example.brofin.data.remote.dto.AddUserBalanceResponseDto
import com.example.brofin.data.remote.dto.EditProfileResponseDto
import com.example.brofin.data.remote.dto.GetAllDataResponseDto
import com.example.brofin.data.remote.dto.LoginResponseDto
import com.example.brofin.data.remote.dto.RegisterResponseDto
import com.example.brofin.data.remote.dto.UpdateBalanceResponseDto
import com.example.brofin.domain.models.Budgeting
import com.example.brofin.domain.repository.RemoteDataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class RemoteDataRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
): RemoteDataRepository {

    override suspend fun register(
        email: String,
        password: String
    ): RegisterResponseDto {
        return withContext(Dispatchers.Default) {
            try {
                apiService.register(email, password)
            } catch (e: Exception){
                throw e
            }
        }
    }


    override suspend fun login(
        email: String,
        password: String
    ): LoginResponseDto {
        return withContext(Dispatchers.Default){
            try {
                apiService.login(email, password)
            } catch (e: Exception) {
                throw e
            }
        }
    }

    override suspend fun editUserProfile(
        username: RequestBody?,
        gender: RequestBody?,
        dob: RequestBody?,
        savings: RequestBody?,
        photo: MultipartBody.Part?
    ): EditProfileResponseDto {
        return withContext(Dispatchers.Default){
            try {
                apiService.editUserProfile(
                    username,
                    gender,
                    dob,
                    savings,
                    photo
                )
            } catch (e: Exception){
                Log.e(TAG, "error when edit UserProfile")
                throw e
            }
        }
    }

    override suspend fun getAllData(): GetAllDataResponseDto {
        return withContext(Dispatchers.Default){
            try {
                apiService.getAllData()
            } catch (e: Exception){
                Log.e(TAG, "Error when get all data")
                throw e
            }
        }
    }

    override suspend fun addBudgetingDiary(
        monthAndYear: RequestBody,
        date: RequestBody,
        description: RequestBody?,
        amount: RequestBody,
        categoryId: RequestBody,
        photo: MultipartBody.Part?
    ): AddDiaryResponseDto {
        return withContext(Dispatchers.Default){
            try {
                apiService.addBudgetingDiary(
                    monthAndYear,
                    date,
                    description,
                    amount,
                    categoryId,
                    photo
                )
            } catch (e:Exception){
                Log.e(TAG, "error when add budgeting diary")
                throw e
            }
        }
    }

    override suspend fun addUserBalance(
        monthAndYear: Long,
        amount: Double,
        currentBalance: Double
    ): AddUserBalanceResponseDto {
        return withContext(Dispatchers.Default){
            try {
                apiService.addBalance(monthAndYear, amount, currentBalance)
            } catch (e: Exception){
                Log.e(TAG, "error when adding userBalance bro")
                throw e
            }
        }
    }

    override suspend fun editbalance(monthAndYear: Long, amount: Double, currentBalance: Double): UpdateBalanceResponseDto {
        return withContext(Dispatchers.Default){
            try {
                apiService.editBalance(monthAndYear, amount, currentBalance)
            } catch (e: Exception){
                Log.e(TAG, "error when editing balance")
                throw e
            }
        }
    }

    override suspend fun addBudgeting(
        monthAndYear: Long,
        total: Double,
        essentialNeedsLimit: Double,
        wantsLimit: Double,
        savingsLimit: Double,
        isReminder: Boolean): AddOrUpateBudgetingResponseDto {
        return withContext(Dispatchers.Default){
            try {
                apiService.addBudgeting(Budgeting(
                    monthAndYear,
                    total,
                    essentialNeedsLimit,
                    wantsLimit,
                    savingsLimit,
                ))
            } catch (e: Exception){
                Log.e(TAG, "error when adding budgeting")
                throw e
            }
        }
    }

    override suspend fun addExpenses(
        monthAndYear: RequestBody,
        date: RequestBody,
        description: RequestBody?,
        amount: RequestBody,
        categoryId: RequestBody,
        total: RequestBody,
        essentialNeeds: RequestBody,
        wants: RequestBody,
        savingsLimit: RequestBody,
        balance: RequestBody,
        currentBalance: RequestBody,
        image: MultipartBody.Part?
    ): AddExpensesResponseDto {
        return withContext(Dispatchers.Default){
            try {
                apiService.addExpenses(
                    monthAndYear,
                    date,
                    description,
                    amount,
                    categoryId,
                    isReminder = "false".toRequestBody("text/plain".toMediaTypeOrNull()),
                    total,
                    essentialNeeds,
                    wants,
                    savingsLimit,
                    balance,
                    currentBalance,
                    image,
                )
            } catch (e: Exception){
                Log.e(TAG, "error when adding expenses")
                throw e
            }
        }
    }

    override suspend fun setupBudgeting(
        monthAndYear: RequestBody,
        total: RequestBody,
        essentialNeedsLimit: RequestBody,
        wantsLimit: RequestBody,
        balance: RequestBody,
        currentBalance: RequestBody,
        isReminder: RequestBody,
        savingsLimit: RequestBody,
        savings: RequestBody,
        dob: RequestBody,
        username: RequestBody,
        image: MultipartBody.Part?
    ): SetupBudgeingResponseDto {
        return withContext(Dispatchers.Default){
            try {
                apiService.setupBudgeting(
                    monthAndYear,
                    total,
                    essentialNeedsLimit,
                    wantsLimit,
                    balance,
                    gender = "tidak diketahui".toRequestBody("text/plain".toMediaTypeOrNull()),
                    currentBalance,
                    isReminder,
                    savingsLimit,
                    savings,
                    dob,
                    username,
                    image
                )
            } catch (e: Exception){
                Log.e(TAG, "error when setup budgeting")
                throw e
            }
        }
    }




    companion object{
        const val TAG = "RemoteDataRepositoryImpl"
    }
}