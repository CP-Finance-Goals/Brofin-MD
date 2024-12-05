package com.example.brofin.presentation.main.financials

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.brofin.domain.StateApp
import com.example.brofin.domain.models.PredictResponse
import com.example.brofin.presentation.components.ErrorDialog
import com.example.brofin.presentation.components.LoadingDialog
import com.example.brofin.presentation.components.NetworkErrorDialog
import com.example.brofin.presentation.main.financials.components.OpenElectricityDialog
import com.example.brofin.presentation.main.financials.components.OpenTextDialog
import com.example.brofin.presentation.main.financials.components.OpenYearPickerDialog
import com.example.brofin.presentation.main.financials.components.PredictRow
import com.example.brofin.utils.toIndonesianCurrency2

@Composable
fun HouseSelected(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
    ) {
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
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentBox(
    financialViewModel: FinancialViewModel = hiltViewModel()
) {
    val houseState = financialViewModel.stateFinancial.collectAsStateWithLifecycle().value
    var loadingDialog by remember { mutableStateOf(false) }
    var errorDialog by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf("Kesalahan tidak terduga saat memprediksi harga rumah") }

    var dataPredict by remember { mutableStateOf<PredictResponse?>(null) }

    LaunchedEffect(houseState) {
        when (houseState) {
            is StateApp.Success -> {
                loadingDialog = false
                errorDialog = false
                dataPredict = houseState.data
                financialViewModel.resetState()
            }
            is StateApp.Error -> {
                errorDialog = true
                loadingDialog = false
                message = houseState.exception
            }
            is StateApp.Loading -> {
                loadingDialog = true
            }
            else -> {
                loadingDialog = false
                errorDialog = false
            }
        }
    }

    var isDialogOpen by remember { mutableStateOf(false) }
    var editedText by remember { mutableStateOf("") }
    var selectedItemIndex by remember { mutableIntStateOf(-1) }
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf("Pilih kota dari rumah tujuan anda") }
    var errorDialogConfirmation by remember { mutableStateOf(false) }
    val options = listOf("Bekasi", "Bogor", "Depok", "Jakarta Barat", "Jakarta Selatan", "Jakarta Timur", "Jakarta Utara", "Tangerang", "Jakarta Pusat")

    // List item untuk kedua kategori
    val listItems = remember { mutableStateListOf(
        "0", "0", "0", "0", "0", "0", "0", "0"
    ) }

    val context = LocalContext.current
    fun validate(): Boolean {
        return listItems.all { it.isNotEmpty()}
    }

    val listNamaItem = remember { mutableStateListOf(
        "Jumlah kamar tidur",
        "Jumlah kamar mandi",
        "Ukuran tanah (Meter)",
        "Ukuran Bangunan (Meter)",
        "Daya Listrik (Watt)",
        "Jumlah kamar pembantu",
        "Jumlah Lantai",
        "Tahun Target"
    )}

    val combinedList = listNamaItem.zip(listItems) { namaItem, itemValue ->
        Pair(namaItem, itemValue)
    }

    LoadingDialog(showDialog = loadingDialog) {
        loadingDialog = false
        financialViewModel.resetState()
    }

    NetworkErrorDialog(showDialog = errorDialog, message = message) {
        errorDialog = false
        financialViewModel.resetState()
    }

    ErrorDialog(showDialog = errorDialogConfirmation, message = "Pastikan semua data telah terisi dengan benar!") {
        errorDialogConfirmation = false
    }

    if (dataPredict != null) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            //1. Prediksi harga rumah impian user
            //2. Prediksi harga rumah impian setelah inflasi
            //3. Cicilan rumah yang mampu dibayar oleh user
            //4. Harga rumah yang direalistis dapat dibeli user
            //5. Rekomendasi KPR

            Text(
                text = "Prediksi Harga Rumah",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                item {
                    PredictRow(label = "Harga Rumah Saat Ini    ", value = dataPredict!!.predictedPrice?.toDouble()
                        ?.toIndonesianCurrency2() ?: "Tidak Tersedia")
                }
                item {
                    PredictRow(label = "Harga Rumah Setelah Inflasi ", value = dataPredict!!.adjustedPrice?.toDouble()
                        ?.toIndonesianCurrency2() ?: "Tidak Tersedia")
                }
                item {
                    PredictRow(label = "Estimasi Cicilan Bulanan", value = dataPredict!!.cicilanBulanan?.toDouble()
                        ?.toIndonesianCurrency2() ?: "Tidak Tersedia")
                }
                item {
                    PredictRow(label = "Rekomendasi Harga Rumah Untukmu", value = dataPredict!!.maxAffordablePrice?.toDouble()
                        ?.toIndonesianCurrency2() ?: "Tidak Tersedia")
                }
                item { Spacer(modifier = Modifier.height(16.dp)) }
                item { Text(text = "Rekomendasi KPR", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onBackground) }
                item {
                    PredictRow(label = "Suku Bunga", value = "${dataPredict!!.sukuBunga ?: "Tidak Tersedia"}%")
                }
                item {
                    PredictRow(label = "Tenor (tahun)", value = "${dataPredict!!.tenor ?: "Tidak Tersedia"} tahun")
                }
                item {
                    PredictRow(label = "DP", value = dataPredict!!.dp?.toDouble()
                        ?.toIndonesianCurrency2()
                        ?: "Tidak Tersedia")
                }
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        OutlinedButton(onClick = {
                            dataPredict = null
                        }) {
                            Text(text = "Coba Prediksi Lagi", style = MaterialTheme.typography.bodyMedium)
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Button(
                            onClick = {
                            Toast.makeText(context, "Rekomendasi telah disimpan", Toast.LENGTH_SHORT).show()
                        }) {
                           Text(text = "Simpan", style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }
        }
    } else {
        Column(
            Modifier.fillMaxWidth(),
        ) {
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = it }
            ) {
                TextField(
                    value = selectedOptionText,
                    onValueChange = { selectedOptionText = it },
                    trailingIcon = {
                        Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                    },
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(type = MenuAnchorType.PrimaryEditable)
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    containerColor = MaterialTheme.colorScheme.surface
                ) {
                    options.forEach { selectionOption ->
                        DropdownMenuItem(
                            onClick = {
                                selectedOptionText = selectionOption
                                expanded = false
                            },
                            text = {
                                Text(
                                    text = selectionOption,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        )
                    }
                }
            }

            combinedList.forEachIndexed { index, pair ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.weight(0.8f)
                    ) {
                        RowNamaItem(pair.first)
                    }

                    Column(
                        modifier = Modifier.weight(0.1f)
                    ) {
                        RowTitik2()
                    }

                    Column(
                        modifier = Modifier.weight(0.6f)
                    ) {
                        RowItem(pair.second)
                    }

                    Column(
                        modifier = Modifier.weight(0.2f)
                    ) {
                        RowEditButton {
                            editedText = pair.second
                            selectedItemIndex = index
                            isDialogOpen = true
                        }
                    }
                }
            }

            PredictButton {
//                if (validate() && selectedOptionText != "Pilih kota dari rumah tujuan anda") {
//                    val listInt = listItems.map { it.toInt() }
//                    financialViewModel.predict(
//                        city = selectedOptionText,
//                        bedrooms = listInt[0],
//                        bathrooms = listInt[1],
//                        landSizeM2 = listInt[2],
//                        buildingSizeM2 = listInt[3],
//                        electricity = listInt[4],
//                        maidBedrooms = listInt[5],
//                        floors = listInt[6],
//                        targetYears = listInt[7]
//                    )
//                } else {
//                    errorDialogConfirmation = true
//                }

                financialViewModel.predict(
                    city = "Bekasi",
                    bedrooms = 2,
                    bathrooms = 1,
                    landSizeM2 = 120,
                    buildingSizeM2 = 100,
                    electricity = 1200,
                    maidBedrooms = 1,
                    floors = 1,
                    targetYears = 5
                )
            }
        }
    }

    if (isDialogOpen) {
        when (selectedItemIndex) {
            4 -> {
                OpenElectricityDialog(
                    value = editedText,
                    onTextChange = {
                        editedText = it
                    },
                    onDismiss = {
                        isDialogOpen = false
                    }) {

                    isDialogOpen = false
                    listItems[selectedItemIndex] = editedText

                }
            }
            7 -> {
                OpenYearPickerDialog(
                    value = editedText,
                    onTextChange = {
                        listItems[selectedItemIndex] = it
                    },
                    onDismiss = {
                        isDialogOpen = false
                    }) {

                    isDialogOpen = false
                    listItems[selectedItemIndex] = it.toString()
                }
            }
            else -> {
                OpenTextDialog(
                    textFieldValue = editedText,
                    onTextChange = { editedText = it },
                    label = listNamaItem[selectedItemIndex],
                    onDismiss = {
                        isDialogOpen = false
                    }) {
                    isDialogOpen = false
                    listItems[selectedItemIndex] = editedText
                }
            }
        }
    }
}

@Composable
fun RowNamaItem(itemText: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = itemText,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun RowTitik2() {
    Row(
        modifier = Modifier
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = ":",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun RowItem(itemText: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            text = itemText,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun RowEditButton(onEdit: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onEdit,
            modifier = Modifier
                .size(40.dp)
        ) {
            Icon(Icons.Default.Edit, contentDescription = "Edit")
        }
    }
}


@Composable
fun PredictButton2(
    enabled: Boolean,  // Menambahkan parameter enabled
    onClick: () -> Unit
) {
    val colors = listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.secondary)

    Box(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(45.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(16.dp))
                .clickable(enabled = enabled, onClick = onClick)  // Gunakan enabled untuk menonaktifkan
                .background(Brush.horizontalGradient(colors)),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = if (enabled) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
            ),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Predict",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Predict",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = if (enabled) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}

@Composable
fun PredictButton(
    onClick: () -> Unit
) {
    val colors = listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.secondary)

    Box(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(45.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(16.dp))
                .clickable(onClick = onClick)
                .background(Brush.horizontalGradient(colors)),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Predict",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Predict",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}
