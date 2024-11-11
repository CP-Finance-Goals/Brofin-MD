package com.example.brofin.presentation.navigation

sealed class NavScreen(val route: String) {
    data object Home: NavScreen("home")
    data object Login: NavScreen("login")
    data object Register: NavScreen("register")
    data object Community: NavScreen("community")
    data object Splash: NavScreen("splash")
    data object SetupProfile: NavScreen("setup_profile")
//    data object DetailStory: NavScreen("detail_story/{id}"){
//        fun createRoute(id: String) = "detail_story/$id"
//    }
}