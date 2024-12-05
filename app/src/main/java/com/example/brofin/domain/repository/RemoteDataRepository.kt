package com.example.brofin.domain.repository

import com.example.brofin.data.remote.dto.AddDiaryResponseDto
import com.example.brofin.data.remote.dto.AddExpensesResponseDto
import com.example.brofin.data.remote.dto.SetupBudgeingResponseDto
import com.example.brofin.data.remote.dto.AddOrUpateBudgetingResponseDto
import com.example.brofin.data.remote.dto.AddUserBalanceResponseDto
import com.example.brofin.data.remote.dto.EditProfileResponseDto
import com.example.brofin.data.remote.dto.GetAllDataResponseDto
import com.example.brofin.data.remote.dto.LoginResponseDto
import com.example.brofin.data.remote.dto.PredictResponseDto
import com.example.brofin.data.remote.dto.RegisterResponseDto
import com.example.brofin.data.remote.dto.UpdateBalanceResponseDto
import com.example.brofin.domain.models.PredictRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Part

interface RemoteDataRepository {

    // register
    suspend fun register(email: String, password: String): RegisterResponseDto

    // Login a user
    suspend fun login(email: String, password: String): LoginResponseDto

    // edit or create user profile
    suspend fun editUserProfile(username: RequestBody?, gender: RequestBody?, dob: RequestBody?, savings: RequestBody?, photo: MultipartBody.Part?): EditProfileResponseDto

    // get all user data
    suspend fun getAllData(): GetAllDataResponseDto

    // add budgeting diary
    suspend fun addBudgetingDiary(
        monthAndYear: RequestBody,
        date: RequestBody,
        description: RequestBody?,
        amount: RequestBody,
        categoryId: RequestBody,
        photo: MultipartBody.Part?
    ): AddDiaryResponseDto

    // add balance to user account
    suspend fun addUserBalance(monthAndYear: Long, amount: Double, currentBalance: Double): AddUserBalanceResponseDto

    // edit balance
    suspend fun editbalance(monthAndYear: Long, amount: Double, currentBalance: Double): UpdateBalanceResponseDto

    // add budgeting
    suspend fun addBudgeting(
        monthAndYear: Long,
        total: Double,
        essentialNeedsLimit: Double,
        wantsLimit: Double,
        savingsLimit: Double,
        isReminder: Boolean
    ): AddOrUpateBudgetingResponseDto

    // add expenses
    suspend fun addExpenses(
        monthAndYear: RequestBody,
        date: RequestBody,
        description: RequestBody? = null,
        amount: RequestBody,
        categoryId: RequestBody,
        total: RequestBody,
        essentialNeeds: RequestBody,
        wants: RequestBody,
        savingsLimit: RequestBody,
        balance: RequestBody,
        currentBalance: RequestBody,
        image: MultipartBody.Part? = null
    ): AddExpensesResponseDto


 // Setup budgeting
    suspend fun setupBudgeting(
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
        image: MultipartBody.Part? = null
    ): SetupBudgeingResponseDto

    // predict house
    suspend fun predictHouse(
       predictRequest: RequestBody
    ): PredictResponseDto
}