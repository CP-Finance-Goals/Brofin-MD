package com.example.brofin.data.remote.dto

import com.squareup.moshi.Json

data class AddUserBalanceResponseDto(

	@Json(name="data")
	val data: Data? = null,

	@Json(name="message")
	val message: String? = null
)

data class Data(
	@Json(name="balance")
	val balance: String? = null,

	@Json(name="currentBalance")
	val currentBalance: String? = null,

	@Json(name="monthAndYear")
	val monthAndYear: String? = null
)
