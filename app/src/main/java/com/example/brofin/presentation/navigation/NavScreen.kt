package com.example.brofin.presentation.navigation

import android.net.Uri
import com.example.brofin.data.local.room.entity.BudgetingDiaryEntity
import com.example.brofin.data.local.room.entity.BudgetingEntity
import com.example.brofin.domain.models.Budgeting
import com.example.brofin.domain.models.BudgetingDiary

sealed class NavScreen(val route: String) {
    data object Home: NavScreen("home")
    data object Login: NavScreen("login")
    data object Register: NavScreen("register")
    data object Community: NavScreen("community")
    data object Splash: NavScreen("splash")
    data object SetupProfile: NavScreen("setup_profile")
    data object CreateBudgetingDiary: NavScreen("create_budgeting_diary")
    data object FinancialPlan: NavScreen("financial_plan")
    data object AddDiaryBudget: NavScreen("add_diary")


    data object DetailBudgeting : NavScreen("detail_story/{id}/{budgeting}/{diaries}") {
        fun createRoute(id: String, budgeting: Budgeting, diaries: List<BudgetingDiary>): String {
            val budgetingJson = Uri.encode(budgetingAdapter.toJson(budgeting))
            val diariesJson = Uri.encode(diaryListAdapter.toJson(diaries))
            return "detail_story/$id/$budgetingJson/$diariesJson"
        }
    }


    data object Camera: NavScreen("camera")
    data object SetupIncome: NavScreen("setup_income")
    data object DetailBudget: NavScreen("detail_budget/{id}"){
        fun createRoute(id: String) = "detail_budget/$id"
    }
//    data object DetailStory: NavScreen("detail_story/{id}"){
//        fun createRoute(id: String) = "detail_story/$id"
//    }
}