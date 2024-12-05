package com.example.brofin.domain.models

import com.squareup.moshi.Json

data class PredictRequest(
    @Json(name = "city") val city: String,
    @Json(name = "bedrooms") val bedrooms: Int,
    @Json(name = "bathrooms") val bathrooms: Int,
    @Json(name = "land_size_m2") val landSizeM2: Int,
    @Json(name = "building_size_m2") val buildingSizeM2: Int,
    @Json(name = "electricity") val electricity: Int,
    @Json(name = "maid_bedrooms") val maidBedrooms: Int,
    @Json(name = "floors") val floors: Int,
    @Json(name = "target_years") val targetYears: Int
)
