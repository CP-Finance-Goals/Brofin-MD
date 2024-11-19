package com.example.brofin.presentation.navigation

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
    data object Camera: NavScreen("camera")
    data object SetupIncome: NavScreen("setup_income")
    data object DetailBudget: NavScreen("detail_budget/{id}"){
        fun createRoute(id: String) = "detail_budget/$id"
    }
//    data object DetailStory: NavScreen("detail_story/{id}"){
//        fun createRoute(id: String) = "detail_story/$id"
//    }
}