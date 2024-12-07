package com.example.brofin.domain.models

data class Predict (
    val id: String,
    val datePredict: Long = System.currentTimeMillis(),
    val sukuBunga: Int? = null,
    val predictedPrice: String? = null,
    val tenor: Int? = null,
    val maxAffordablePrice: String? = null,
    val adjustedPrice: String? = null,
    val cicilanBulanan: String? = null,
    val dp: String? = null,
    val jumlahKamarTidur : Int? = null,
    val jumlahKamarMandi : Int? = null,
    val ukurantanah : Int? = null,
    val ukuranbangunan : Int? = null,
    val dayaListrik : Int? = null,
    val jumlahLantai : Int? = null,
    val jumlahKamarPembantu : Int? = null,
    val tahunTarget : Int? = null,
)