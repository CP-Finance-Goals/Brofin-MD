package com.example.brofin.data.remote.service

import com.example.brofin.data.remote.dto.GadgetResponseDto
import com.example.brofin.data.remote.dto.GameResponseDto
import com.example.brofin.data.remote.dto.LuxuryResponseDto
import com.example.brofin.data.remote.dto.MobilResponseDto
import com.example.brofin.data.remote.dto.MotorResponseDto
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RecommendationApiService {

    @POST("recommend")
    suspend fun getRecommendationMotor(
        @Body request: RequestBody
    ): Response<MotorResponseDto>

    @POST("recommend")
    suspend fun getRecommendationMobil(
        @Body request: RequestBody
    ): Response<MobilResponseDto>

    @POST("recommend")
    suspend fun getRecommendationLuxury(
        @Body request: RequestBody
    ): Response<LuxuryResponseDto>

    @POST("recommend")
    suspend fun getRecommendationGame(
        @Body request: RequestBody
    ): Response<GameResponseDto>

    @POST("recommend")
    suspend fun getRecommendationGadget(
        @Body request: RequestBody
    ): Response<GadgetResponseDto>

}