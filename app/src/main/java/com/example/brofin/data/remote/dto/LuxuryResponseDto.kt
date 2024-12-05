	package com.example.brofin.data.remote.dto

import com.squareup.moshi.Json

data class LuxuryResponseDto(

	@Json(name="LuxuryResponseDto")
	val luxuryResponseDto: List<LuxuryResponseDtoItem?>? = null
)

data class LuxuryResponseDtoItem(

	@Json(name="Brand")
	val brand: String? = null,

	@Json(name="price")
	val price: Any? = null,

	@Json(name="item group")
	val itemGroup: String? = null
)
