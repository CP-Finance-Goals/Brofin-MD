package com.example.brofin.presentation.main.financials.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.brofin.presentation.main.home.components.CustomTextFieldTwo
import com.example.brofin.utils.toIndonesianCurrency2
import java.util.Calendar

@Composable
fun OpenTextDialog2(
    label: String,
    textFieldValue: String,
    onTextChange: (String) -> Unit,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit,
    minAmount: Double,
    maxAmount: Double
) {
    var isError by remember { mutableStateOf(false) }

    fun formatCurrency(value: String): String {
        val cleanValue = value.replace(",", "") // Remove commas
        return cleanValue.toDoubleOrNull()?.toIndonesianCurrency2() ?: ""
    }

    var formatedData by remember {
        mutableStateOf(formatCurrency(textFieldValue))
    }
    LaunchedEffect(textFieldValue) {
        isError = when {
            textFieldValue.isEmpty() -> true
            textFieldValue == "0" -> true
            textFieldValue.toDoubleOrNull() == null -> true
            textFieldValue.toDouble() < minAmount || textFieldValue.toDouble() > maxAmount -> true
            else -> false
        }

        formatedData = formatCurrency(textFieldValue)
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Masukkan Data") },
        text = {
            Column {
                Text(text = "Banyak uang yang kamu masukan adalah $formatedData", style = MaterialTheme.typography.bodyMedium)
                CustomTextFieldTwo(
                    label = label,
                    text = if (textFieldValue == "0") "" else textFieldValue,
                    onTextChange = { input ->
                        // Prevent multiple decimal points
                        val newText = if (input.count { it == '.' } <= 1) input else textFieldValue
                        onTextChange(newText)
                    },
                    validate = {
                        when {
                            it.isEmpty() -> "Jumlah tidak boleh kosong"
                            it.toDoubleOrNull() == null -> "Jumlah harus berupa angka"
                            it.toDouble() < 0 -> "Jumlah tidak boleh kurang dari 0"
                            it.toDouble() < minAmount -> "minimal ${minAmount.toIndonesianCurrency2()}"
                            it.toDouble() > maxAmount -> "maksimal ${maxAmount.toIndonesianCurrency2()}"
                            else -> ""
                        }
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    ),
                    singleLine = true,
                    maxLines = 1,
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    when {
                        textFieldValue.isEmpty() -> {
                            isError = false
                        }
                        else -> {
                            isError = false
                            onConfirm(textFieldValue)
                        }
                    }
                },
                enabled = !isError
            ) {
                Text("Simpan", style = MaterialTheme.typography.bodyMedium)
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text("Batal", style = MaterialTheme.typography.bodyMedium)
            }
        }
    )
}

@Composable
fun OpenTextDialog(
    label: String,
    textFieldValue: String,
    onTextChange: (String) -> Unit,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var isError by remember { mutableStateOf(false) }
    fun formatCurrency(value: String): String {
        val cleanValue = value.replace(",", "")
        return cleanValue.toDoubleOrNull()?.toIndonesianCurrency2() ?: ""
    }

    var formatedData by remember {
        mutableStateOf(formatCurrency(textFieldValue))
    }

    LaunchedEffect(textFieldValue) {
        isError = when {
            textFieldValue.isEmpty() -> true
            textFieldValue == "0" -> true
            textFieldValue.toDoubleOrNull() == null -> true
            textFieldValue.toDouble() < 0 -> true
            else -> false
        }

        formatedData = formatCurrency(textFieldValue)
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Masukkan Data") },
        text = {
            Column {
                Text(text = "Data yang Anda masukkan: $formatedData", style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.padding(8.dp))
                CustomTextFieldTwo(
                    label = label,
                    text = if (textFieldValue == "0") "" else textFieldValue,
                    onTextChange = { input ->
                        val newText = if (input.count { it == '.' } <= 1) input else textFieldValue
                        onTextChange(newText)
                    },
                    validate = {
                        when {
                            it.isEmpty() -> "Jumlah tidak boleh kosong"
                            it.toDoubleOrNull() == null -> "Jumlah harus berupa angka"
                            it.toDouble() < 0 -> "Jumlah tidak boleh kurang dari 0"
                            else -> ""
                        }
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    ),
                    singleLine = true,
                    maxLines = 1,
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    when {
                        textFieldValue.isEmpty() -> {
                            isError = false
                        }
                        else -> {
                            isError = false
                            onConfirm(textFieldValue)
                        }
                    }
                },
                enabled = !isError
            ) {
                Text("Simpan", style = MaterialTheme.typography.bodyMedium)
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text("Batal", style = MaterialTheme.typography.bodyMedium)
            }
        }
    )
}


@Composable
fun OpenTextDialogXX(
    label: String,
    textFieldValue: String,
    onTextChange: (String) -> Unit,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var isError by remember { mutableStateOf(false) }

    LaunchedEffect(textFieldValue) {
        isError = when {
            textFieldValue.isEmpty() -> true
            textFieldValue == "0" -> true
            textFieldValue.toDoubleOrNull() == null -> true
            textFieldValue.toDouble() < 0 -> true
            else -> false
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Masukkan Data") },
        text = {
            Column {
                CustomTextFieldTwo(
                    label = label,
                    text = if (textFieldValue == "0") "" else textFieldValue,
                    onTextChange = { input ->
                        onTextChange(input)
                    },
                    validate = {
                        when {
                            it.isEmpty() -> "Jumlah tidak boleh kosong"
                            it.toDoubleOrNull() == null -> "Jumlah harus berupa angka"
                            it.toDouble() < 0 -> "Jumlah tidak boleh kurang dari 0"
                            else -> ""
                        }
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    ),
                    singleLine = true,
                    maxLines = 1,
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    when {
                        textFieldValue.isEmpty() -> {
                            isError = false
                        }

                        else -> {
                            isError = false
                            onConfirm(textFieldValue)
                        }
                    }
                },
                enabled = !isError
            ) {
                Text("Simpan", style = MaterialTheme.typography.bodyMedium)
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text("Batal", style = MaterialTheme.typography.bodyMedium)
            }
        }
    )
}

@Composable
fun OpenTextDialogV3(
    label: String,
    textFieldValue: String,
    onTextChange: (String) -> Unit,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var isError by remember { mutableStateOf(false) }

    fun convertToSquareMeter(value: String): String {
        val cleanValue = value.replace(",", "")
        return cleanValue.toDoubleOrNull()?.let {
            it.toString()
        } ?: ""
    }

    var formatedData by remember {
        mutableStateOf(convertToSquareMeter(textFieldValue))
    }


    LaunchedEffect(textFieldValue) {
        isError = when {
            textFieldValue.isEmpty() -> true
            textFieldValue == "0" -> true
            textFieldValue.toDoubleOrNull() == null -> true
            textFieldValue.toDouble() < 0 -> true
            else -> false
        }

        formatedData = convertToSquareMeter(textFieldValue)
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Masukkan Data") },
        text = {
            Column {
                Text(text = "Data yang Anda masukkan: $formatedData m", style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.padding(8.dp))
                CustomTextFieldTwo(
                    label = label,
                    text = if (textFieldValue == "0") "" else textFieldValue,
                    onTextChange = { input ->
                        val newText = if (input.count { it == '.' } <= 1) input else textFieldValue
                        onTextChange(newText)
                    },
                    validate = {
                        when {
                            it.isEmpty() -> "Jumlah tidak boleh kosong"
                            it.toDoubleOrNull() == null -> "Jumlah harus berupa angka"
                            it.toDouble() < 0 -> "Jumlah tidak boleh kurang dari 0"
                            else -> ""
                        }
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    ),
                    singleLine = true,
                    maxLines = 1,
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    when {
                        textFieldValue.isEmpty() -> {
                            isError = false
                        }
                        else -> {
                            isError = false
                            onConfirm(textFieldValue)
                        }
                    }
                },
                enabled = !isError
            ) {
                Text("Simpan", style = MaterialTheme.typography.bodyMedium)
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text("Batal", style = MaterialTheme.typography.bodyMedium)
            }
        }
    )
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OpenElectricityDialog(
    value: String,
    onTextChange: (String) -> Unit,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    val wattOptions = listOf("450", "900", "1200", "2300", "3500", "5000")

    var expanded by remember { mutableStateOf(false) }
    var isError by remember { mutableStateOf(false) }
    var selectedWatt by remember { mutableStateOf(value) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Pilih Daya Watt (VA)") },
        text = {
            Column {
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { newExpanded -> expanded = newExpanded }
                ) {

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                            .clickable(onClick = {}),
                        border = BorderStroke(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = selectedWatt,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.weight(1f)
                            )
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = "Dropdown Icon",
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        wattOptions.forEach { watt ->
                            DropdownMenuItem(
                                onClick = {
                                    selectedWatt = watt
                                    onTextChange(watt)
                                    expanded = false
                                },
                                text = {
                                    Text(watt)
                                }
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {

            Button(
                onClick = {
                    when{
                        value.isEmpty() -> {
                            isError = true
                        }
                        value == "0" ->{
                            isError = true
                        }
                        else -> {
                            isError = false
                            onConfirm(selectedWatt)
                        }
                    }
                },
                enabled = !isError
            ) {
                Text("Simpan", style = MaterialTheme.typography.bodyMedium)
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text("Batal", style = MaterialTheme.typography.bodyMedium)
            }
        }
    )
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OpenYearPickerDialog(
    value: String,
    onTextChange: (String) -> Unit,
    onDismiss: () -> Unit,
    onConfirm: (Int) -> Unit
) {
    val currentYear = Calendar.getInstance().get(Calendar.YEAR)
    val years = (currentYear..currentYear + 20).map { it.toString() }
    var isError by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }
    var selectedYear by remember { mutableStateOf(hitungTahunDariSelisih(value.toInt()).toString()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Pilih Tahun") },
        text = {
            Column {
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = it }
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                            .clickable(onClick = {}),
                        border = BorderStroke(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = selectedYear,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.weight(1f)
                            )
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = "Dropdown Icon",
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        years.forEach { year ->
                            DropdownMenuItem(
                                onClick = {
                                    selectedYear = year
                                    onTextChange(year)
                                    expanded = false
                                },
                                text = {
                                    Text(year)
                                }
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    when{
                        value.isEmpty() -> {
                            isError = true
                        }
                        value.toIntOrNull() == null -> {
                            isError = true
                        }
                        value.toInt() < 0 -> {
                            isError = true
                        }
                        else -> {
                            isError = false
                            val yearDifference = hitungSelisih(selectedYear.toInt())
                            onConfirm(yearDifference)
                        }
                    }
                },
                enabled = !isError
            ) {
                Text("Simpan", style = MaterialTheme.typography.bodyMedium)
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text("Batal", style = MaterialTheme.typography.bodyMedium)
            }
        }
    )
}

fun hitungSelisih(data: Int): Int{
    val currentYear = Calendar.getInstance().get(Calendar.YEAR)
    val yearDifference = data - currentYear
    return yearDifference
}

fun hitungTahunDariSelisih(selisih: Int): Int {
    val currentYear = Calendar.getInstance().get(Calendar.YEAR)
    val year = currentYear + selisih
    return year
}
