package com.example.brofin.data.remote.service

import com.example.brofin.data.remote.dto.AddDiaryResponseDto
import com.example.brofin.data.remote.dto.AddExpensesResponseDto
import com.example.brofin.data.remote.dto.AddOrUpateBudgetingResponseDto
import com.example.brofin.data.remote.dto.AddUserBalanceResponseDto
import com.example.brofin.data.remote.dto.EditProfileResponseDto
import com.example.brofin.data.remote.dto.GetAllDataResponseDto
import com.example.brofin.data.remote.dto.LoginResponseDto
import com.example.brofin.data.remote.dto.RegisterResponseDto
import com.example.brofin.data.remote.dto.SetupBudgeingResponseDto
import com.example.brofin.data.remote.dto.UpdateBalanceResponseDto
import com.example.brofin.domain.models.Budgeting
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part

interface ApiService {

    @FormUrlEncoded
    @POST("auth/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<LoginResponseDto>

    @FormUrlEncoded
    @POST("auth/register")
    suspend fun register(
        @Field("name") username: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<RegisterResponseDto>

    @GET("user/getAll")
    suspend fun getAllData(): GetAllDataResponseDto

    @Multipart
    @PUT("user/details")
    suspend fun editUserProfile(
        @Part("username") username: RequestBody?,
        @Part("gender") gender: RequestBody?,
        @Part("dob") address: RequestBody?,
        @Part("savings") savings: RequestBody?,
        @Part image: MultipartBody.Part? = null
    ): EditProfileResponseDto


    @Multipart
    @POST("user/diaries")
    suspend fun addBudgetingDiary(
        @Part("monthAndYear") monthAndYear: RequestBody,
        @Part("date") date: RequestBody,
        @Part("description") description: RequestBody? = null,
        @Part("amount") amount: RequestBody,
        @Part("categoryId") categoryId: RequestBody,
        @Part photo: MultipartBody.Part? = null
    ): AddDiaryResponseDto

    @FormUrlEncoded
    @POST("user/balances")
    suspend fun addBalance(
        @Field("monthAndYear") monthAndYear: Long,
        @Field("balance") balance: Double,
        @Field("currentBalance") currentBalance: Double
    ): AddUserBalanceResponseDto

    @FormUrlEncoded
    @PUT("user/balances")
    suspend fun editBalance(
        @Field("monthAndYear") monthAndYear: Long,
        @Field("balance") balance: Double,
        @Field("currentBalance") currentBalance: Double
    ): UpdateBalanceResponseDto

    @POST("user/budgetings")
    suspend fun addBudgeting(
        @Body budgetRequest: Budgeting
    ): AddOrUpateBudgetingResponseDto

    @PUT("user/budgeting")
    suspend fun editBugeting(
        @Body bugetRequet: Budgeting
    ): AddOrUpateBudgetingResponseDto

    @Multipart
    @POST("user/expenses")
    suspend fun setupBudgeting(
        @Part("monthAndYear") monthAndYear: RequestBody,
        @Part("total") total: RequestBody,
        @Part("essentialNeedsLimit") essentialNeeds: RequestBody,
        @Part("wantsLimit") wants: RequestBody,
        @Part("balance") balance: RequestBody,
        @Part("gender") gender: RequestBody,
        @Part("currentBalance") currentBalance: RequestBody,
        @Part("isReminder") isReminder: RequestBody,
        @Part("savingsLimit") savingsLimit: RequestBody,
        @Part("savings") savings: RequestBody,
        @Part("dob") dob: RequestBody,
        @Part("username") username: RequestBody,
        @Part image: MultipartBody.Part?
    ): SetupBudgeingResponseDto

    @Multipart
    @PUT("user/expenses")
    suspend fun addExpenses(
        @Part("monthAndYear") monthAndYear: RequestBody,
        @Part("date") date: RequestBody,
        @Part("description") description: RequestBody? = null,
        @Part("amount") amount: RequestBody,
        @Part("categoryId") categoryId: RequestBody,
        @Part("isReminder") isReminder: RequestBody,
        @Part("total") total: RequestBody,
        @Part("essentialNeedsLimit") essentialNeeds: RequestBody,
        @Part("wantsLimit") wants: RequestBody,
        @Part("savingsLimit") savingsLimit: RequestBody,
        @Part("balance") balance: RequestBody,
        @Part("currentBalance") currentBalance: RequestBody,
        @Part image: MultipartBody.Part? = null
    ): AddExpensesResponseDto

}
