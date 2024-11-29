package com.example.brofin.data.remote.dto

import com.squareup.moshi.Json

data class RegisterResponseDto(

	@Json(name="message")
	val message: String? = null,

	@Json(name="user")
	val user: User? = null
)

data class User(

	@Json(name="id")
	val id: String? = null,

	@Json(name="email")
	val email: String? = null
)
