package com.example.brofin.data.remote.dto

import com.squareup.moshi.Json

data class GadgetResponseDto(

	@Json(name="GadgetResponseDto")
	val gadgetResponseDto: List<GadgetResponseDtoItem?>? = null
)

data class GadgetResponseDtoItem(

	@Json(name="Brand")
	val brand: String? = null,

	@Json(name="Storage")
	val storage: Double? = null,

	@Json(name="Price")
	val price: Double? = null,

	@Json(name="Memory")
	val memory: Double? = null
)
