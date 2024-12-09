package com.example.brofin.presentation.main.financials

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.brofin.presentation.main.financials.components.MyDropDownCustom
import com.example.brofin.presentation.main.financials.components.PredictTable

@Composable
fun FinancialScreen() {

    val categories = listOf("Financial Goals", "House Goals", "Prediksi Favorite")
    var selectedCategory by remember { mutableStateOf("Prediksi Favorite") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            MyDropDownCustom(
                selectedValue = selectedCategory,
                options = categories,
                color = MaterialTheme.colorScheme.primary,
                onValueChangedEvent = { selectedCategory = it },
            )

            Spacer(Modifier.height(16.dp))

            when (selectedCategory) {
                "Financial Goals" -> FinancialSelected()
                "House Goals" ->  HouseSelected()
                "Prediksi Favorite" -> FavoriteSelewted()
            }
        }
    }
}

