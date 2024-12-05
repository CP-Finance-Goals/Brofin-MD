package com.example.brofin.data.remote.dto

import com.squareup.moshi.Json

data class MotorResponseDto(

	@Json(name="MotorResponseDto")
	val motorResponseDto: List<MotorResponseDtoItem?>? = null
)

data class MotorResponseDtoItem(

	@Json(name="harga")
	val harga: Double? = null,

	@Json(name="nama")
	val nama: String? = null,

	@Json(name="transmisi")
	val transmisi: String? = null,

	@Json(name="bahan_bakar")
	val bahanBakar: String? = null
)
