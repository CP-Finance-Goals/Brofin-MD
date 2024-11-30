package com.example.brofin.data.remote

import com.example.brofin.data.remote.dto.AddOrUpateBudgetingResponseDto
import com.example.brofin.data.remote.dto.AddDiaryResponseDto
import com.example.brofin.data.remote.dto.AddUserBalanceResponseDto
import com.example.brofin.data.remote.dto.EditProfileResponseDto
import com.example.brofin.data.remote.dto.GetAllDataResponseDto
import com.example.brofin.data.remote.dto.LoginResponseDto
import com.example.brofin.data.remote.dto.RegisterResponseDto
import com.example.brofin.data.remote.dto.UpdateBalanceResponseDto
import com.example.brofin.domain.models.Budgeting
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part

interface ApiService {

    // login
    @FormUrlEncoded
    @POST("auth/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponseDto

    // Register
    @FormUrlEncoded
    @POST("auth/register")
    suspend fun register(
        @Field("email") email: String,
        @Field("password") password: String
    ): RegisterResponseDto

    // Get all data
    @GET("user/getAll")
    suspend fun getAllData(): GetAllDataResponseDto

    // Edit user profile
    @Multipart
    @PUT("user/details")
    suspend fun editUserProfile(
        @Part("username") username: RequestBody?,
        @Part("gender") gender: RequestBody?,
        @Part("dob") address: RequestBody?,
        @Part("savings") savings: RequestBody?,
        @Part image: MultipartBody.Part? = null // Hapus nama parameter "image" di sini
    ): EditProfileResponseDto


    // Add budgeting diary
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

    // Add Balance
    @FormUrlEncoded
    @POST("user/balances")
    suspend fun addBalance(
        @Field("monthAndYear") monthAndYear: Long,
        @Field("balance") balance: Double,
        @Field("currentBalance") currentBalance: Double
    ): AddUserBalanceResponseDto

    // edit Balance
    @FormUrlEncoded
    @PUT("user/balances")
    suspend fun editBalance(
        @Field("monthAndYear") monthAndYear: Long,
        @Field("balance") balance: Double,
        @Field("currentBalance") currentBalance: Double
    ): UpdateBalanceResponseDto

    // add bugeting
    @POST("user/budgetings")
    suspend fun addBudgeting(
        @Body budgetRequest: Budgeting
    ): AddOrUpateBudgetingResponseDto

    // edit bugeting
    @PUT("user/budgeting")
    suspend fun editBugeting(
        @Body bugetRequet: Budgeting
    ): AddOrUpateBudgetingResponseDto
}
