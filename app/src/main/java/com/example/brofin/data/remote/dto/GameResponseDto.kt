package com.example.brofin.data.remote.dto

import com.squareup.moshi.Json

data class GameResponseDto(

	@Json(name="GameResponseDto")
	val gameResponseDto: List<GameResponseDtoItem?>? = null
)

data class GameResponseDtoItem(

	@Json(name="kata_kunci")
	val kataKunci: String? = null,

	@Json(name="harga")
	val harga: Double? = null,

	@Json(name="nama")
	val nama: String? = null,

	@Json(name="tipe_review")
	val tipeReview: String? = null
)
