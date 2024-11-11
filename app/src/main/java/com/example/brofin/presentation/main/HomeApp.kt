package com.example.brofin.presentation.main

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.brofin.presentation.main.budgeting.diary.BudgetingDiaryScreen
import com.example.brofin.presentation.main.components.AppBar
import com.example.brofin.presentation.main.home.HomeScreen
import com.example.brofin.presentation.main.navigation.home.BottomNavigationBar
import com.example.brofin.presentation.main.navigation.home.bottomNavItems
import kotlinx.coroutines.launch

@Composable
fun HomeApp(viewmodel: HomeAppViewModel= hiltViewModel(), goLogin: () -> Unit) {

    val state = viewmodel.isUserLoggedIn.collectAsState(true)

    LaunchedEffect(state.value) {
        if (!state.value) {
            goLogin()
        }
    }

    val pagerState = rememberPagerState(
        pageCount = { bottomNavItems.size },
        initialPage = 0
    )

    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppBar(title = "Brofin")
        },
        bottomBar = {
            BottomNavigationBar(
                currentPage = pagerState.currentPage,
                onTabSelected = { page ->
                    coroutineScope.launch {
                        pagerState.scrollToPage(page)
                    }
                }
            )
        },
        containerColor = Color(0xFFF7FAFC)
    ) { innerPadding ->
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.padding(innerPadding),
            flingBehavior = PagerDefaults.flingBehavior(
                state = pagerState,
                snapAnimationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
        ) { page ->
            when (page) {
                0 -> HomeScreen()
                1 -> BudgetingDiaryScreen()
                2 -> SettingsScreen()
            }
        }
    }
}


// Example Screen

@Composable
fun SettingsScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Settings Screen")
    }
}
