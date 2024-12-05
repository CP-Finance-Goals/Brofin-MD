package com.example.brofin.data.remote.service

import com.example.brofin.data.remote.dto.GadgetResponseDtoItem
import com.example.brofin.data.remote.dto.GameResponseDtoItem
import com.example.brofin.data.remote.dto.LuxuryResponseDtoItem
import com.example.brofin.data.remote.dto.MobilResponseDtoItem
import com.example.brofin.data.remote.dto.MotorResponseDtoItem
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RecommendationApiService {

    @POST("recommend")
    suspend fun getRecommendationMotor(
        @Body request: RequestBody
    ): Response<List<MotorResponseDtoItem>>

    @POST("recommend")
    suspend fun getRecommendationMobil(
        @Body request: RequestBody
    ): Response<List<MobilResponseDtoItem>>

    @POST("recommend")
    suspend fun getRecommendationLuxury(
        @Body request: RequestBody
    ): Response<List<LuxuryResponseDtoItem>>

    @POST("recommend")
    suspend fun getRecommendationGame(
        @Body request: RequestBody
    ): Response<List<GameResponseDtoItem>>

    @POST("recommend")
    suspend fun getRecommendationGadget(
        @Body request: RequestBody
    ): Response<List<GadgetResponseDtoItem>>

}