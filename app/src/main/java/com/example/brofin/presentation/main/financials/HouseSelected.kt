package com.example.brofin.presentation.main.financials

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.brofin.domain.StateApp
import com.example.brofin.domain.models.PredictResponse
import com.example.brofin.presentation.components.LoadingDialog
import com.example.brofin.presentation.components.NetworkErrorDialog
import com.example.brofin.presentation.main.financials.components.ContentBox
import com.example.brofin.presentation.main.financials.components.PredictRow
import com.example.brofin.presentation.main.financials.components.borderDashed
import com.example.brofin.utils.toIndonesianCurrency2

@Composable
fun HouseSelected(modifier: Modifier = Modifier, financialViewModel: FinancialViewModel = hiltViewModel()) {
    val state = financialViewModel.stateFinancial.collectAsStateWithLifecycle().value

    var showLoading by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf("") }
    val context = LocalContext.current
    var showError by remember { mutableStateOf(false) }
    var dataState by remember { mutableStateOf<PredictResponse?>(null) }

    LaunchedEffect(state) {
        when (state) {
            is StateApp.Loading -> showLoading = true
            is StateApp.Error -> {
                showError = true
                showLoading = false
                message = state.exception
            }
            is StateApp.Success -> {
                showLoading = false
                Toast.makeText(context, "Prediksi berhasil di dapatkan", Toast.LENGTH_SHORT).show()
                dataState = state.data
            }
            StateApp.Idle -> {
                showLoading = false
                showError = false
            }
        }
    }

    val scrollState = rememberLazyListState()

    Box(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
    ) {
        LoadingDialog(showDialog = showLoading) {
            showLoading = false
        }

        NetworkErrorDialog(showDialog = showError, message) {
            showError = false
            financialViewModel.resetState()
        }

        LazyColumn(
            state = scrollState,
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Card(
                    Modifier
                        .fillMaxWidth()
                        .borderDashed(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.onSurface,
                            dashLength = 10f,
                            gapLength = 6f
                        )
                        .padding(8.dp)
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    ContentBox()
                }
            }

            item {
                HorizontalDivider(
                    thickness = 1.dp,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item { Spacer(modifier = Modifier.height(8.dp)) }

            if (dataState != null) {
                item {
                    Card(
                        Modifier
                            .fillMaxWidth()
                            .borderDashed(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.onSurface,
                                dashLength = 10f,
                                gapLength = 6f
                            )
                            .padding(8.dp)
                            .background(MaterialTheme.colorScheme.background)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                        ) {
                            Text(
                                text = "Prediksi Harga Rumah",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )

                            PredictRow(
                                label = "Harga Rumah Saat Ini    ",
                                value = dataState!!.predictedPrice?.toDouble()
                                    ?.toIndonesianCurrency2() ?: "Tidak Tersedia"
                            )
                            PredictRow(
                                label = "Harga Rumah Setelah Inflasi ",
                                value = dataState!!.adjustedPrice?.toDouble()
                                    ?.toIndonesianCurrency2() ?: "Tidak Tersedia"
                            )
                            PredictRow(
                                label = "Estimasi Cicilan Bulanan",
                                value = dataState!!.cicilanBulanan?.toDouble()
                                    ?.toIndonesianCurrency2() ?: "Tidak Tersedia"
                            )
                            PredictRow(
                                label = "Rekomendasi Harga Rumah Untukmu",
                                value = dataState!!.maxAffordablePrice?.toDouble()
                                    ?.toIndonesianCurrency2() ?: "Tidak Tersedia"
                            )

                            Text(
                                text = "Rekomendasi KPR",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onBackground
                            )

                            PredictRow(
                                label = "Suku Bunga",
                                value = "${dataState!!.sukuBunga ?: "Tidak Tersedia"}%"
                            )

                            PredictRow(
                                label = "Tenor (tahun)",
                                value = "${dataState!!.tenor ?: "Tidak Tersedia"} tahun"
                            )

                            PredictRow(
                                label = "DP",
                                value = dataState!!.dp?.toDouble()
                                    ?.toIndonesianCurrency2() ?: "Tidak Tersedia"
                            )
                        }
                    }
                }
            }
        }
    }
}


