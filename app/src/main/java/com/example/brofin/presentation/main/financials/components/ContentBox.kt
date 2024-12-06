package com.example.brofin.presentation.main.financials.components

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.brofin.domain.StateApp
import com.example.brofin.domain.models.PredictResponse
import com.example.brofin.presentation.components.ErrorDialog
import com.example.brofin.presentation.components.LoadingDialog
import com.example.brofin.presentation.components.NetworkErrorDialog
import com.example.brofin.presentation.main.financials.FinancialViewModel
import com.example.brofin.utils.toIndonesianCurrency2


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentBox(
    financialViewModel: FinancialViewModel = hiltViewModel()
) {

    var isDialogOpen by remember { mutableStateOf(false) }
    var editedText by remember { mutableStateOf("") }
    var selectedItemIndex by remember { mutableIntStateOf(-1) }
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf("Pilih kota dari rumah tujuan anda") }
    var errorDialogConfirmation by remember { mutableStateOf(false) }
    val options = listOf("Bekasi", "Bogor", "Depok", "Jakarta Barat", "Jakarta Selatan", "Jakarta Timur", "Jakarta Utara", "Tangerang", "Jakarta Pusat")
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
    )
    }

    val combinedList = listNamaItem.zip(listItems) { namaItem, itemValue ->
        Pair(namaItem, itemValue)
    }


    ErrorDialog(showDialog = errorDialogConfirmation, message = "Pastikan semua data telah terisi dengan benar!") {
        errorDialogConfirmation = false
    }

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