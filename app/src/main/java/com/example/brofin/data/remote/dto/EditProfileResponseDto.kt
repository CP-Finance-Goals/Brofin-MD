package com.example.brofin.data.remote.dto

import com.squareup.moshi.Json

data class EditProfileResponseDto(

	@Json(name="photoUrl")
	val photoUrl: String? = null,

	@Json(name="message")
	val message: String? = null
)
