package com.example.brofin.presentation.main.financials

import android.widget.Space
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.brofin.presentation.main.home.components.CustomTextFieldTwo
import com.example.brofin.utils.getFormattedTimeInMillis

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FinancialSelected(modifier: Modifier = Modifier) {

    val categories = listOf("Mobil", "Gadget", "Motor", "Luxury Brand", "Games")
    var selectedValue by remember { mutableStateOf("Pilih Kategori") }
    var textValue by remember { mutableStateOf("") }
    var showDatePicker by remember { mutableStateOf(false) }
    var date by remember { mutableLongStateOf(getFormattedTimeInMillis(System.currentTimeMillis())) }
    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = date)
    var isDatePickerVisible by remember { mutableStateOf(false) }
    val scrrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
    ){
        Column(
            Modifier.verticalScroll(state = scrrollState)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            DynamicSelectTextField(
                selectedValue = selectedValue,
                options = categories,
                onValueChangedEvent = {
                    selectedValue = it
                },
                color = MaterialTheme.colorScheme.surface
            )
            Spacer(modifier = Modifier.height(10.dp))
            CustomTextFieldTwo(
                label = "Jumlah Target Uang",
                text = textValue,
                onTextChange = {
                    textValue = it
                },
                validate = {
                    if (it.isEmpty()) {
                        "Jumlah Target Uang tidak boleh kosong"
                    } else if (it.toDoubleOrNull() == null) {
                        "Jumlah Target Uang harus berupa angka"
                    } else {
                        ""
                    }
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                ),
                singleLine = true,
                maxLines = 1
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedButton(
                onClick = {
                    showDatePicker = true
                },
                shape = RoundedCornerShape(15.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    "Estimasi",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(8.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {

                },
                shape = RoundedCornerShape(15.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    "Hitung Prediksi",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(8.dp)
                )
            }
        }

        if (showDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    Button(
                        onClick = {
                            datePickerState.selectedDateMillis?.let {
                                date = it
                            }
                            showDatePicker = false
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    ) {
                        Text("Pilih")
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        isDatePickerVisible = false
                        showDatePicker = false
                    }) {
                        Text("Batal")
                    }
                },
                content = {
                    DatePicker(
                        state = datePickerState,
                        showModeToggle = true
                    )
                }
            )

        }
    }

}
