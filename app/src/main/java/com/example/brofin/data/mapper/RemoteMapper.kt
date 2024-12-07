package com.example.brofin.data.mapper

import com.example.brofin.data.remote.dto.BudgetingDiariesItem
import com.example.brofin.data.remote.dto.BudgetingsItem
import com.example.brofin.data.remote.dto.GadgetResponseDtoItem
import com.example.brofin.data.remote.dto.GameResponseDtoItem
import com.example.brofin.data.remote.dto.LuxuryResponseDtoItem
import com.example.brofin.data.remote.dto.MobilResponseDtoItem
import com.example.brofin.data.remote.dto.MotorResponseDtoItem
import com.example.brofin.data.remote.dto.PredictResponseDto
import com.example.brofin.data.remote.dto.UserBalanceItem
import com.example.brofin.data.remote.dto.UserProfileItem
import com.example.brofin.domain.models.Budgeting
import com.example.brofin.domain.models.BudgetingDiary
import com.example.brofin.domain.models.GadgetRecommendation
import com.example.brofin.domain.models.GameRecommendation
import com.example.brofin.domain.models.LuxuryRecommendation
import com.example.brofin.domain.models.MobilRecommendation
import com.example.brofin.domain.models.MotorRecommendation
import com.example.brofin.domain.models.PredictResponse
import com.example.brofin.domain.models.UserBalance
import com.example.brofin.domain.models.UserProfile

fun UserProfileItem.toUserProfile(): UserProfile {
    return UserProfile(
        id = userId,
        createdAt = createdAt.toString(),
        photoUrl = photoUrl,
        gender = gender,
        dob = dob,
        name = username,
        savings = savings,
        email = email
    )
}

fun UserBalanceItem.toUserBalance(): UserBalance {
    return UserBalance(
        balance = balance,
        currentBalance = currentBalance,
        monthAndYear = monthAndYear,
    )
}

fun BudgetingsItem.toBudgeting(): Budgeting {
    return Budgeting(
        total = total,
        essentialNeedsLimit = essentialNeedsLimit,
        savingsLimit = savingsLimit,
        monthAndYear = monthAndYear,
        wantsLimit = wantsLimit,
    )
}

fun BudgetingDiariesItem.toBudgeting(): BudgetingDiary {
    return BudgetingDiary(
        id = id,
        date = date,
        photoUrl = photoUrl,
        description = description,
        amount = amount,
        categoryId = categoryId,
        monthAndYear = monthAndYear,
    )
}

fun PredictResponseDto.toPredictResponse(): PredictResponse {
    return PredictResponse(
        sukuBunga = sukuBunga,
        predictedPrice = predictedPrice,
        tenor = tenor,
        maxAffordablePrice = maxAffordablePrice,
        adjustedPrice = adjustedPrice,
        cicilanBulanan = cicilanBulanan,
        dp = dp,
    )
}


// Recommendation

fun GadgetResponseDtoItem.toGadgetRecommendation(): GadgetRecommendation  = GadgetRecommendation(
    brand = brand,
    storage = storage,
    price = price,
    memory = memory
)

fun MobilResponseDtoItem.toMobilRecommendation(): MobilRecommendation = MobilRecommendation(
    brand = brand,
    price = price,
    tipeBbm = tipeBbm,
    transmisi = transmisi
)

fun GameResponseDtoItem.toGameRecommendation(): GameRecommendation = GameRecommendation(
    kataKunci = kataKunci,
    harga = harga,
    nama = nama,
    tipeReview = tipeReview
)

fun LuxuryResponseDtoItem.toLuxuryRecommendation(): LuxuryRecommendation = LuxuryRecommendation(
    brand = brand,
    price = price,
    itemGroup = itemGroup,
)

fun MotorResponseDtoItem.toMotorRecommendation(): MotorRecommendation = MotorRecommendation(
    harga = harga,
    nama = nama,
    transmisi = transmisi,
    bahanBakar = bahanBakar
)