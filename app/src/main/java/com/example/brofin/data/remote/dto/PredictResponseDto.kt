package com.example.brofin.data.remote.dto

import com.squareup.moshi.Json

data class PredictResponseDto(

	@Json(name="suku_bunga")
	val sukuBunga: Int? = null,

	@Json(name="predicted_price")
	val predictedPrice: String? = null,

	@Json(name="tenor")
	val tenor: Int? = null,

	@Json(name="max_affordable_price")
	val maxAffordablePrice: String? = null,

	@Json(name="adjusted_price")
	val adjustedPrice: String? = null,

	@Json(name="cicilan_bulanan")
	val cicilanBulanan: String? = null,

	@Json(name="dp")
	val dp: String? = null
)
