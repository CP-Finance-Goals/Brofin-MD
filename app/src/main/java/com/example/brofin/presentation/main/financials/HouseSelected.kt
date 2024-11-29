package com.example.brofin.presentation.main.financials

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.brofin.presentation.main.home.components.CustomTextFieldTwo

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
fun ContentBox() {
    var isDialogOpen by remember { mutableStateOf(false) }
    var editedText by remember { mutableStateOf("") }
    var selectedItemIndex by remember { mutableIntStateOf(-1) }
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf("Pilih kota dari rumah tujuan anda") }

    val options = listOf("Bekasi", "Bogor", "Depok", "Jakarta Barat", "Jakarta Selatan", "Jakarta Timur", "Jakarta Utara", "Tangerang", "Jakarta Pusat")

    // List item untuk kedua kategori
    val listItems = remember { mutableStateListOf(
        "0", "0", "0", "0", "0", "0"
    ) }

    val listNamaItem = remember { mutableStateListOf(
        "Jumlah kamar tidur",
        "Jumlah kamar mandi",
        "Ukuran tanah (Meter)",
        "Ukuran Bangunan (Meter)",
        "Daya Listrik (Watt)",
        "Jumlah Lantai",
    )}

    val combinedList = listNamaItem.zip(listItems) { namaItem, itemValue ->
        Pair(namaItem, itemValue)
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
    }

    if (isDialogOpen) {
        EditTextDialog(
            textFieldValue = editedText,
            onTextChange = { editedText = it },
            onDismiss = { isDialogOpen = false },
            onConfirm = {
                listItems[selectedItemIndex] = editedText
                isDialogOpen = false
            }
        )
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
            style = MaterialTheme.typography.bodySmall,
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
            style = MaterialTheme.typography.bodySmall,
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
            style = MaterialTheme.typography.bodySmall,
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
fun EditTextDialog(
    textFieldValue: String,
    onTextChange: (String) -> Unit,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {

    var isError by remember { mutableStateOf(false) }

    LaunchedEffect(textFieldValue) {
        isError = when{
            textFieldValue.isEmpty() -> {
                true
            }

            textFieldValue == "0" -> {
                true
            }

            textFieldValue.toDoubleOrNull() == null -> {
                true
            }

            textFieldValue.toDouble() < 0 -> {
                true
            }

            else -> {
                false
            }
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("masukan data") },
        text = {
            Column {
                CustomTextFieldTwo(
                    label = "Jumlah",
                    text = if (textFieldValue == "0") "" else textFieldValue,
                    onTextChange = {
                        onTextChange(it)
                    },
                    validate = {
                        when {
                            it == "0" -> "Jumlah tidak boleh kosong"
                            it.isEmpty() -> "Jumlah tidak boleh kosong"  // Validasi tidak kosong
                            it.toDoubleOrNull() == null -> "Jumlah harus berupa angka"  // Validasi angka
                            it.toDouble() < 0 -> "Jumlah tidak boleh kurang dari 0"  // Validasi angka negatif
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
                   when{
                         textFieldValue.isEmpty() -> {
                             isError = true
                         }
                         textFieldValue.toDoubleOrNull() == null -> {
                             isError = true
                         }
                         textFieldValue.toDouble() < 0 -> {
                              isError = true
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
