package com.example.brofin.domain.models

data class PredictResponse (
    val sukuBunga: Int? = null,
    val predictedPrice: String? = null,
    val tenor: Int? = null,
    val maxAffordablePrice: String? = null,
    val adjustedPrice: String? = null,
    val cicilanBulanan: String? = null,
    val dp: String? = null
)