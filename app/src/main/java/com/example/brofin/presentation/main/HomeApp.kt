package com.example.brofin.presentation.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.brofin.domain.models.BudgetingDiary
import com.example.brofin.presentation.main.budget.BudgetScreen
import com.example.brofin.presentation.main.financials.FinancialScreen
import com.example.brofin.presentation.main.home.HomeScreen
import com.example.brofin.presentation.main.navigation.home.BottomNavigationBar
import com.example.brofin.presentation.main.navigation.home.bottomNavItems
import com.example.brofin.presentation.settings.SettingScreen
import com.example.brofin.utils.BudgetAllocation
import kotlinx.coroutines.launch

@Composable
fun HomeApp(
    viewmodel: HomeAppViewModel= hiltViewModel(),
    goLogin: () -> Unit,
    goCreateDiary: () -> Unit,
    goDetail: (List<BudgetingDiary>, BudgetAllocation, Double) -> Unit,
    goList: () -> Unit,
) {

    val state = viewmodel.isUserLoggedIn.collectAsState(true)

    LaunchedEffect(state.value) {
        when (state.value) {
            false -> goLogin()
            else -> {
            }
        }
    }

    val pagerState = rememberPagerState(
        pageCount = { bottomNavItems.size },
        initialPage = 0
    )

    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavigationBar(
                currentPage = pagerState.currentPage,
                onTabSelected = { page ->
                    coroutineScope.launch {
                        pagerState.scrollToPage(page)
                    }
                },
                onAddClick = { goCreateDiary() }
            )
        },
        containerColor = Color(0xFFF7FAFC)
    ) { innerPadding ->
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.padding(innerPadding),
            flingBehavior = PagerDefaults.flingBehavior(
                state = pagerState,
            )
        ) { page ->
            when (page) {
                0 -> HomeScreen(goList = goList)
                1 -> BudgetScreen(goDetail = goDetail)
                2 -> FinancialScreen()
                3 -> SettingScreen()
            }
        }
    }
}
