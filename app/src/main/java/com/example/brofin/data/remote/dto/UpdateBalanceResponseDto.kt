package com.example.brofin.data.remote.dto

import com.squareup.moshi.Json

data class UpdateBalanceResponseDto(

	@Json(name="data")
	val data: Data? = null,

	@Json(name="message")
	val message: String? = null
)


