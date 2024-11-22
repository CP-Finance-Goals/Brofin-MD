package com.example.brofin.presentation.navigation

import android.net.Uri
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
import com.example.brofin.domain.models.Budgeting
import com.example.brofin.domain.models.BudgetingDiary
import com.example.brofin.presentation.authentication.login.LoginScreen
import com.example.brofin.presentation.authentication.register.RegisterScreen
import com.example.brofin.presentation.camera.CameraScreen
import com.example.brofin.presentation.expenses.AddExpenses
import com.example.brofin.presentation.main.HomeApp
import com.example.brofin.presentation.setup.SetupIncome
import com.example.brofin.presentation.splash.SplashScreen

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
            route = "detail_budgeting",
            arguments = listOf(
                navArgument("budgeting") { type = NavType.ParcelableType(Budgeting::class.java) },
                navArgument("diaries") { type = NavType.ParcelableArrayType(BudgetingDiary::class.java) }
            )
        ) { backStackEntry ->
            val budgeting = backStackEntry.arguments?.getParcelable<Budgeting>("budgeting")
            val diaries = backStackEntry.arguments?.getParcelableArray("diaries")?.toList() as? List<BudgetingDiary>
            DetailAllocationScreen(budgeting, diaries)
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
//            enterTransition = {
//                slideInHorizontally(
//                    initialOffsetX = { 1000 },
//                    animationSpec = tween(durationMillis = 500)
//                ) + fadeIn(animationSpec = tween(durationMillis = 500))
//            },
//            exitTransition = {
//                slideOutHorizontally(
//                    targetOffsetX = { -1000 },
//                    animationSpec = tween(durationMillis = 500)
//                ) + fadeOut(animationSpec = tween(durationMillis = 500))
//            }
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
                }
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
                    navController.popBackStack()
                }
            )
        }
    }
}