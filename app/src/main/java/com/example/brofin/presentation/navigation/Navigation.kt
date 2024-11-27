package com.example.brofin.presentation.navigation

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.brofin.domain.models.BudgetingDiary
import com.example.brofin.presentation.authentication.login.LoginScreen
import com.example.brofin.presentation.authentication.register.RegisterScreen
import com.example.brofin.presentation.camera.CameraScreen
import com.example.brofin.presentation.detail.DetailBudgetAllocation
import com.example.brofin.presentation.diaries.DiariesList
import com.example.brofin.presentation.expenses.AddExpenses
import com.example.brofin.presentation.main.HomeApp
import com.example.brofin.presentation.main.financials.CreateFinancialGoal
import com.example.brofin.presentation.splash.SplashScreen
import com.example.brofin.utils.BudgetAllocation
import kotlinx.serialization.json.Json

@Composable
fun Navigation() {

    val navController = rememberNavController()

    var photoUri by remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        photoUri = uri
    }

    NavHost(
        navController = navController,
        startDestination = NavScreen.Splash.route
    ){
        composable(
            route = NavScreen.ListBudgetingDiary.route
        ){
            DiariesList()
        }
        composable(
            route = NavScreen.DetailBudget.route,
            arguments = listOf(
                navArgument(NavArgument.BUDGETDIARY) { type = NavType.StringType },
                navArgument(NavArgument.ALLOCATION) { type = NavType.StringType },
                navArgument(NavArgument.LIMIT) { type = NavType.StringType }
            ),
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { 1000 },
                    animationSpec = tween(durationMillis = 500)
                ) + fadeIn(animationSpec = tween(durationMillis = 500))
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -1000 },
                    animationSpec = tween(durationMillis = 500)
                ) + fadeOut(animationSpec = tween(durationMillis = 500))
            }
        ) { backStackEntry ->

            val budgetingDiaryJson = backStackEntry.arguments?.getString(NavArgument.BUDGETDIARY)
            val budgetingJson = backStackEntry.arguments?.getString(NavArgument.ALLOCATION)
            val limit = backStackEntry.arguments?.getString(NavArgument.LIMIT)

            val budgetingDiary = try {
                budgetingDiaryJson?.let { Json.decodeFromString<List<BudgetingDiary>>(it) } ?: emptyList()
            } catch (e: Exception) {
                Log.e("Navigation", "Error decoding JSON: ${e.message}")
                emptyList()
            }

            val budgeting = try {
                budgetingJson?.let { Json.decodeFromString<BudgetAllocation>(it) }
            } catch (e: Exception) {
                Log.e("Navigation", "Error decoding JSON: ${e.message}")
                null
            }

            if (budgetingDiary.isEmpty() || budgeting == null || budgeting.kategori.isEmpty()) {
                navController.navigate(NavScreen.Home.route) {
                    popUpTo(NavScreen.Home.route) {
                        inclusive = true
                    }
                }
            } else {
                DetailBudgetAllocation(
                    limit = limit?.toDouble() ?: 0.0,
                    allocation = budgeting,
                    diaries = budgetingDiary,
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }
        }


        composable(
            route = NavScreen.Login.route,
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { 1000 },
                    animationSpec = tween(durationMillis = 500)
                ) + fadeIn(animationSpec = tween(durationMillis = 500))
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -1000 },
                    animationSpec = tween(durationMillis = 500)
                ) + fadeOut(animationSpec = tween(durationMillis = 500))
            }
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
                }
            )
        }
        composable(
            route = NavScreen.Home.route,
        ){
            HomeApp(
                goLogin = {
                    navController.navigate(NavScreen.Login.route){
                        popUpTo(NavScreen.Home.route){
                            inclusive = true
                        }
                    }
                },
                goCreateDiary = {
                    navController.navigate(NavScreen.AddDiaryBudget.route)
                },
                goDetail = { diaries, allocation, limit ->
                    navController.navigate(NavScreen.DetailBudget.createRoute(diaries, allocation, limit))
                },
                goList = {
                    navController.navigate(NavScreen.ListBudgetingDiary.route)
                },
            )
        }
        composable(
            route = NavScreen.Register.route,
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { 1000 },
                    animationSpec = tween(durationMillis = 500)
                ) + fadeIn(animationSpec = tween(durationMillis = 500))
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -1000 },
                    animationSpec = tween(durationMillis = 500)
                ) + fadeOut(animationSpec = tween(durationMillis = 500))
            }
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
            route = NavScreen.Splash.route,
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { 1000 },
                    animationSpec = tween(durationMillis = 500)
                ) + fadeIn(animationSpec = tween(durationMillis = 500))
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -1000 },
                    animationSpec = tween(durationMillis = 500)
                ) + fadeOut(animationSpec = tween(durationMillis = 500))
            }
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
                },
            )
        }
        composable(
            route = NavScreen.Camera.route,
            enterTransition = {
                slideInVertically(
                    initialOffsetY = { 1000 },
                    animationSpec = tween(durationMillis = 500)
                ) + fadeIn(animationSpec = tween(durationMillis = 500))
            },
            exitTransition = {
                slideOutVertically(
                    targetOffsetY = { -1000 },
                    animationSpec = tween(durationMillis = 500)
                ) + fadeOut(animationSpec = tween(durationMillis = 500))
            }
        ){
            CameraScreen(
                currentBackstackEntry = { uri ->
                    navController.previousBackStackEntry?.savedStateHandle?.set(NavArgument.PHOTOURI, uri)
                },
                goBack = {
                    navController.popBackStack()
                },

            )
        }
        composable(
            route = NavScreen.AddDiaryBudget.route,
            enterTransition = {
                slideInVertically(
                    initialOffsetY = { 1000 },
                    animationSpec = tween(durationMillis = 500)
                ) + fadeIn(animationSpec = tween(durationMillis = 500))
            },
            exitTransition = {
                slideOutVertically(
                    targetOffsetY = { -1000 },
                    animationSpec = tween(durationMillis = 500)
                ) + fadeOut(animationSpec = tween(durationMillis = 500))
            }
        ){
            AddExpenses(
                photoUri = photoUri,
                onBackClick = {
                    navController.popBackStack()
                },
                currentBackStack = {
                    navController.currentBackStackEntry?.savedStateHandle?.getLiveData<Uri?>(NavArgument.PHOTOURI)?.observeForever { uri ->
                        photoUri = uri
                    }
                },
                goGallery = {
                    launcher.launch("image/*")
                },
                goCamera = {
                    navController.navigate(NavScreen.Camera.route)
                },
                goback = {
                    photoUri = null
                    navController.popBackStack()
                },
                backHandler = {
                    photoUri = null
                }
            )
        }
    }
}