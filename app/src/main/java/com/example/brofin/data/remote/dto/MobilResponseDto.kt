package com.example.brofin.data.remote.dto

import com.squareup.moshi.Json

data class MobilResponseDto(

	@Json(name="MobilResponseDto")
	val mobilResponseDto: List<MobilResponseDtoItem?>? = null
)

data class MobilResponseDtoItem(

	@Json(name="Brand")
	val brand: String? = null,

	@Json(name="price")
	val price: Double? = null,

	@Json(name="tipe_bbm")
	val tipeBbm: String? = null,

	@Json(name="transmisi")
	val transmisi: String? = null
)
