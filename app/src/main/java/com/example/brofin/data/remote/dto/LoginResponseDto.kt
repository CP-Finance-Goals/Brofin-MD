package com.example.brofin.data.remote.dto

import com.squareup.moshi.Json

data class LoginResponseDto(

	@Json(name="message")
	val message: String? = null,

	@Json(name="token")
	val token: String? = null
)
