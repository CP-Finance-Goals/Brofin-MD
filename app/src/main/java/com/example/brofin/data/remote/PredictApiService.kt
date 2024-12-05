package com.example.brofin.data.remote

import com.example.brofin.data.remote.dto.PredictResponseDto
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface PredictApiService {

    @POST("predict")
    suspend fun predictHouse(@Body request: RequestBody): Response<PredictResponseDto>

}