package com.example.brofin.data.remote.dto

import com.squareup.moshi.Json

data class GetAllDataResponseDto(

	@Json(name="userBalance")
	val userBalance: List<UserBalanceItem?>? = null,

	@Json(name="budgetings")
	val budgetings: List<BudgetingsItem?>? = null,

	@Json(name="userProfile")
	val userProfile: List<UserProfileItem>,

	@Json(name="budgeting_diaries")
	val budgetingDiaries: List<BudgetingDiariesItem?>? = null
)

data class BudgetingsItem(

	@Json(name="total")
	val total: Double,

	@Json(name="isReminder")
	val isReminder: Boolean,

	@Json(name="essentialNeedsLimit")
	val essentialNeedsLimit: Double,

	@Json(name="savingsLimit")
	val savingsLimit: Double,

	@Json(name="monthAndYear")
	val monthAndYear: Long,

	@Json(name="wantsLimit")
	val wantsLimit: Double
)

data class UserBalanceItem(

	@Json(name="balance")
	val balance: Double? = null,

	@Json(name="currentBalance")
	val currentBalance: Double? = null,

	@Json(name="monthAndYear")
	val monthAndYear: Long
)

data class CreatedAt(

	@Json(name="_nanoseconds")
	val nanoseconds: Int? = null,

	@Json(name="_seconds")
	val seconds: Int? = null
)

data class UserProfileItem(

	@Json(name="createdAt")
	val createdAt: CreatedAt? = null,

	@Json(name="photoUrl")
	val photoUrl: String? = null,

	@Json(name="gender")
	val gender: String? = null,

	@Json(name="dob")
	val dob: String? = null,

	@Json(name="name")
	val name: String? = null,

	@Json(name="savings")
	val savings: Double? = null,

	@Json(name="userId")
	val userId: Int,

	@Json(name="email")
	val email: String? = null,

	@Json(name="username")
	val username: String? = null
)

	data class BudgetingDiariesItem(

		@Json(name="date")
		val date: Long,

		@Json(name="photoUrl")
		val photoUrl: String? = null,

		@Json(name="amount")
		val amount: Double,

		@Json(name="monthAndYear")
		val monthAndYear: Long,

		@Json(name="description")
		val description: String? = null,

		@Json(name="id")
		val id: Long,

		@Json(name="categoryId")
		val categoryId: Int
	)
