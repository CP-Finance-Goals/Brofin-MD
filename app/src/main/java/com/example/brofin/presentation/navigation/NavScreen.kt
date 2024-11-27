package com.example.brofin.presentation.navigation

import android.net.Uri
import com.example.brofin.domain.models.BudgetingDiary
import com.example.brofin.utils.BudgetAllocation
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

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
    data object ListBudgetingDiary: NavScreen("list_budgeting_diary")
    data object AddFinancialPlan: NavScreen("add_financial_plan")

//
//    data object DetailBudgeting : NavScreen("detail_story/{id}/{budgeting}/{diaries}") {
//        fun createRoute(id: String, budgeting: Budgeting, diaries: List<BudgetingDiary>): String {
//            val budgetingJson = Uri.encode(budgetingAdapter.toJson(budgeting))
//            val diariesJson = Uri.encode(diaryListAdapter.toJson(diaries))
//            return "detail_story/$id/$budgetingJson/$diariesJson"
//        }
//    }


    data object Camera: NavScreen("camera")
    data object SetupIncome: NavScreen("setup_income")
    object DetailBudget : NavScreen("detail_budget?budgetDiary={budgetDiary}&allocation={allocation}&limit={limit}") {
        fun createRoute(budgetingDiary: List<BudgetingDiary>, budgeting: BudgetAllocation, limit: Double): String {
            val diaryJson = Uri.encode(Json.encodeToString(budgetingDiary))
            val budgetJson = Uri.encode(Json.encodeToString(budgeting))
            val limit =  Uri.encode(limit.toString())
            return "detail_budget?budgetDiary=$diaryJson&allocation=$budgetJson&limit=$limit"
        }
    }


//    data object DetailStory: NavScreen("detail_story/{id}"){
//        fun createRoute(id: String) = "detail_story/$id"
//    }
}