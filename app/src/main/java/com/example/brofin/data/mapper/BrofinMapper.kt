package com.example.brofin.data.mapper

import com.example.brofin.data.local.room.entity.BudgetingDiaryEntity
import com.example.brofin.data.local.room.entity.BudgetingEntity
import com.example.brofin.data.local.room.entity.PredictEntity
import com.example.brofin.data.local.room.entity.UserBalanceEntity
import com.example.brofin.data.local.room.entity.UserProfileEntity
import com.example.brofin.domain.models.Budgeting
import com.example.brofin.domain.models.BudgetingDiary
import com.example.brofin.domain.models.Predict
import com.example.brofin.domain.models.PredictResponse
import com.example.brofin.domain.models.UserBalance
import com.example.brofin.domain.models.UserProfile

// To Domain
fun UserBalanceEntity.toUser() = UserBalance(
    monthAndYear = monthAndYear,
    balance = balance,
    currentBalance = currentBalance,
)

fun PredictEntity.toPredictResponse() = PredictResponse(
    id = id,
    sukuBunga = sukuBunga,
    predictedPrice = predictedPrice,
    dp = dp,
    datePredict = datePredict,
    tenor = tenor,
    maxAffordablePrice = maxAffordablePrice,
    adjustedPrice = adjustedPrice,
    cicilanBulanan = cicilanBulanan
)

fun PredictEntity.toPredict() = Predict(
    id = id,
    sukuBunga = sukuBunga,
    predictedPrice = predictedPrice,
    dp = dp,
    datePredict = datePredict,
    tenor = tenor,
    kota = kota,
    maxAffordablePrice = maxAffordablePrice,
    adjustedPrice = adjustedPrice,
    cicilanBulanan = cicilanBulanan,
    jumlahLantai = jumlahLantai,
    jumlahKamarMandi = jumlahKamarMandi,
    jumlahKamarTidur = jumlahKamarTidur,
    jumlahKamarPembantu = jumlahKamarPembantu,
    ukuranbangunan = ukuranbangunan,
    dayaListrik = dayaListrik,
    ukurantanah = ukurantanah,
    tahunTarget = tahunTarget
)

fun UserProfileEntity.toUserProfile() = UserProfile(
    id = id,
    createdAt = createdAt,
    photoUrl = photoUrl,
    gender = gender,
    dob = dob,
    name = name,
    savings = savings,
    email = email
)

fun BudgetingEntity.toBudgetingDiary() = Budgeting(
    monthAndYear = monthAndYear,
    total = total,
    essentialNeedsLimit = essentialNeedsLimit,
    wantsLimit = wantsLimit,
    savingsLimit = savingsLimit,
    isReminder = isReminder
)

fun BudgetingDiaryEntity.toBudgetingDiary() = BudgetingDiary(
    id = id,
    date = date,
    description = description,
    photoUrl = photoUrl,
    amount = amount,
    categoryId = categoryId,
    monthAndYear = monthAndYear
)

// To Entity
fun UserBalance.toUserBalanceEntity() = UserBalanceEntity(
    currentBalance = currentBalance,
    monthAndYear = monthAndYear,
    balance = balance,
)

fun Predict.toPredictEntity() = PredictEntity(
    id = id,
    sukuBunga = sukuBunga,
    predictedPrice = predictedPrice,
    dp = dp,
    datePredict = datePredict,
    tenor = tenor,
    maxAffordablePrice = maxAffordablePrice,
    kota = kota,
    adjustedPrice = adjustedPrice,
    cicilanBulanan = cicilanBulanan,
    jumlahLantai = jumlahLantai,
    jumlahKamarMandi = jumlahKamarMandi,
    jumlahKamarTidur = jumlahKamarTidur,
    jumlahKamarPembantu = jumlahKamarPembantu,
    ukuranbangunan = ukuranbangunan,
    dayaListrik = dayaListrik,
    ukurantanah = ukurantanah,
    tahunTarget = tahunTarget
)

fun PredictResponse.toPredictEntity() = PredictEntity(
    id = id,
    sukuBunga = sukuBunga,
    predictedPrice = predictedPrice,
    datePredict = datePredict,
    dp = dp,
    maxAffordablePrice = maxAffordablePrice,
    adjustedPrice = adjustedPrice,
    cicilanBulanan = cicilanBulanan,
    tenor = tenor
)

fun UserProfile.toUserProfileEntity() = UserProfileEntity(
    id = id,
    createdAt = createdAt,
    photoUrl = photoUrl,
    gender = gender,
    dob = dob,
    name = name,
    savings = savings,
    email = email
)

fun Budgeting.toBudgetingEntity() = BudgetingEntity(
    monthAndYear = monthAndYear,
    total = total,
    essentialNeedsLimit = essentialNeedsLimit,
    wantsLimit = wantsLimit,
    savingsLimit = savingsLimit,
    isReminder = isReminder
)

fun BudgetingDiary.toBudgetingDiaryEntity() = BudgetingDiaryEntity(
    id = id,
    date = date,
    description = description,
    photoUrl = photoUrl,
    amount = amount,
    categoryId = categoryId,
    monthAndYear = monthAndYear
)


