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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.material3.IconButton
import com.example.brofin.domain.StateApp
import com.example.brofin.domain.models.PredictResponse
import com.example.brofin.presentation.components.LoadingDialog
import com.example.brofin.presentation.components.NetworkErrorDialog
import com.example.brofin.presentation.expenses.ConfirmationDialog
import com.example.brofin.presentation.main.financials.components.ContentBox
import com.example.brofin.presentation.main.financials.components.PredictRow
import com.example.brofin.presentation.main.financials.components.borderDashed
import com.example.brofin.utils.toIndonesianCurrency2

@Composable
fun HouseSelected(modifier: Modifier = Modifier, financialViewModel: FinancialViewModel = hiltViewModel()) {

    val state = financialViewModel.stateFinancial.collectAsStateWithLifecycle().value
    val inserSelected = financialViewModel.stateInsertAndRemove.collectAsStateWithLifecycle().value

    var showLoading by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }
    var dataState by remember { mutableStateOf<PredictResponse?>(null) }
    var showConfirmationSave by remember { mutableStateOf(false) }
    var insertIsSucces by remember { mutableStateOf(false) }

    var cityData by remember { mutableStateOf("") }
    var bedroomsData by remember { mutableIntStateOf(0) }
    var bathroomsData by remember { mutableIntStateOf(0) }
    var landSizeM2Data by remember { mutableIntStateOf(0) }
    var buildingSizeM2Data by remember { mutableIntStateOf(0) }
    var electricityData by remember { mutableIntStateOf(0) }
    var maidBedroomsData by remember { mutableIntStateOf(0) }
    var floorsData by remember { mutableIntStateOf(0) }
    var targetYearsData by remember { mutableIntStateOf(0) }

    val context = LocalContext.current
    val toastShown by financialViewModel.toastShown

    LaunchedEffect(dataState) {
        if (dataState != null && !toastShown) {
            Toast.makeText(context, "Prediksi berhasil di dapatkan", Toast.LENGTH_SHORT).show()
            financialViewModel.showToast()
        }
    }

    LaunchedEffect(state) {
        when (state) {
            is StateApp.Loading -> {
                showLoading = true
                showError = false
            }
            is StateApp.Error -> {
                showError = true
                showLoading = false
                message = state.exception
            }
            is StateApp.Success -> {
                showLoading = false
                dataState = state.data
            }
            StateApp.Idle -> {
                showLoading = false
                showError = false
            }
        }
    }

    LaunchedEffect(inserSelected) {
        when (inserSelected) {
            is StateApp.Loading -> {
                showLoading = true
                showError = false
                insertIsSucces = false
            }
            is StateApp.Error -> {
                showError = true
                showLoading = false
                insertIsSucces = false
                message = inserSelected.exception
            }
            is StateApp.Success -> {
                Toast.makeText(context, "Berhasil menambakan predict ke favorite", Toast.LENGTH_SHORT).show()
                showLoading = false
                insertIsSucces = true
            }
            StateApp.Idle -> {
                showLoading = false
                showError = false
                insertIsSucces = false
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

       ConfirmationDialog(
           showDialog = showConfirmationSave,
           message = "Apakah kamu yakin menyimpan prediksi ini? \nPrediksi ini akan tersimpan di favorite",
           onConfirm = {
               showConfirmationSave = false
               financialViewModel.insertPredict(
                   dataState!!,
                   city = cityData,
                   bedrooms = bedroomsData,
                   bathrooms = bathroomsData,
                   landSizeM2 = landSizeM2Data,
                   buildingSizeM2 = buildingSizeM2Data,
                   electricity = electricityData,
                   maidBedrooms = maidBedroomsData,
                   floors = floorsData,
                   targetYears = targetYearsData,
               )
       }) {
           showConfirmationSave = false
       }

        NetworkErrorDialog(showDialog = showError, message) {
            showError = false
            financialViewModel.resetState()
        }

        LazyColumn(
            state = scrollState,
            modifier = Modifier
                .fillMaxSize(),
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
                    ContentBox(
                        itemtransfer = {city, bedrooms, bathrooms, landSizeM2, buildingSizeM2, electricity, maidBedrooms, floors, targetYears ->
                            cityData = city
                            bedroomsData = bedrooms
                            bathroomsData = bathrooms
                            landSizeM2Data = landSizeM2
                            buildingSizeM2Data = buildingSizeM2
                            electricityData = electricity
                            maidBedroomsData = maidBedrooms
                            floorsData = floors
                            targetYearsData = targetYears
                        }
                    )
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
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Card(
                            modifier = Modifier
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

                        IconButton(
                            onClick = {
                                if(!insertIsSucces){
                                    showConfirmationSave = true
                                } else {
                                    Toast.makeText(context, "kamu sudah menambahkan prediksi ini ke favorite", Toast.LENGTH_SHORT).show()
                                }
                            },
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = "Favorite",
                                tint = if (insertIsSucces) Color.Red else Color.DarkGray
                            )
                        }
                    }
                }
            }
        }
    }
}


