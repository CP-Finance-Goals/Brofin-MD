package com.example.brofin.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.brofin.presentation.authentication.login.LoginScreen
import com.example.brofin.presentation.authentication.register.RegisterScreen
import com.example.brofin.presentation.main.HomeApp
import com.example.brofin.presentation.main.budget.components.CreateBudgetScreen
import com.example.brofin.presentation.splash.SplashScreen

@Composable
fun Navigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavScreen.Splash.route
    ){
        composable(
            route = NavScreen.Login.route
        ){
            LoginScreen(
                goToRegister = {
                    navController.navigate(NavScreen.Register.route)
                },
                goHome = {
                    navController.navigate(NavScreen.Home.route){
                        popUpTo(NavScreen.Login.route){
                            inclusive = true
                        }
                    }
                },

            )
        }
        composable(
            route = NavScreen.Home.route
        ){
            HomeApp(
                goLogin = {
                    navController.navigate(NavScreen.Login.route){
                        popUpTo(NavScreen.Home.route){
                            inclusive = true
                        }
                    }
                },
                goCreateBudget = {
                    navController.navigate(NavScreen.CreateBudgetingDiary.route)
                }
            )
        }
        composable(
            route = NavScreen.Register.route
        ) {
            RegisterScreen(
                goBack = {
                    navController.popBackStack()
                },
                goHome = {
                    navController.navigate(NavScreen.Home.route) {
                        popUpTo(NavScreen.Login.route) {
                            inclusive = true
                        }
                    }
                },
            )
        }
        composable(
            route = NavScreen.Splash.route
        ){
            SplashScreen(
                goHome = {
                    navController.navigate(NavScreen.Home.route){
                        popUpTo(NavScreen.Splash.route){
                            inclusive = true
                        }
                    }
                },
                goLogin = {
                    navController.navigate(NavScreen.Login.route) {
                        popUpTo(NavScreen.Splash.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(
            route = NavScreen.CreateBudgetingDiary.route
        ){
            CreateBudgetScreen {
            }
        }
    }
}