package com.example.brofin.presentation.main.financials

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.brofin.data.local.room.entity.FinancialGoalsEntity
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun CreateFinancialGoal(modifier: Modifier = Modifier) {

    val tombol1Active = remember { mutableStateOf(false) }
    val tombol2Active = remember { mutableStateOf(false) }

    Scaffold{ innerPadding ->
        Column(
            modifier = modifier.fillMaxSize(),
        ) {
            Row {
                Button(
                    onClick = { tombol1Active.value = !tombol1Active.value },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Tombol 1")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = { tombol2Active.value = !tombol2Active.value },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Tombol 2")
                }
            }
            CreateFinancialGoalContent(
                modifier = modifier,
                innerPadding = innerPadding,
                onSave = {},
                onCancel = {}
            )
        }
    }

}

@Composable
fun CreateFinancialGoalContent(
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues,
    onSave: (FinancialGoalsEntity) -> Unit,
    onCancel: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var targetAmount by remember { mutableStateOf("") }
    var installment by remember { mutableStateOf("") }
    var deadline by remember { mutableStateOf("") }

    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Input Nama Goal
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nama Goal") },
            modifier = Modifier.fillMaxWidth()
        )

        // Input Deskripsi (opsional)
        TextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Deskripsi (Opsional)") },
            modifier = Modifier.fillMaxWidth()
        )

        // Input Target Amount
        TextField(
            value = targetAmount,
            onValueChange = { targetAmount = it },
            label = { Text("Target Dana") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        // Input Installment
        TextField(
            value = installment,
            onValueChange = { installment = it },
            label = { Text("Cicilan Per Bulan") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        // Input Deadline
        TextField(
            value = deadline,
            onValueChange = { deadline = it },
            label = { Text("Deadline (yyyy-MM-dd)") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Button Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Button(onClick = { onCancel() }) {
                Text("Batal")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {
                // Validasi input
                val isInputValid = name.isNotBlank() && targetAmount.isNotBlank() &&
                        installment.isNotBlank() && deadline.isNotBlank()

                if (isInputValid) {
                    val goal = FinancialGoalsEntity(
                        id = 0, // Auto-generated in database
                        userId = "user123", // Hardcoded for now
                        name = name,
                        description = if (description.isNotBlank()) description else null,
                        targetAmount = targetAmount.toDoubleOrNull() ?: 0.0,
                        savedAmount = 0.0, // Default
                        installment = installment.toDoubleOrNull() ?: 0.0,
                        deadline = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                            .parse(deadline)?.time ?: System.currentTimeMillis(),
                        createdAt = System.currentTimeMillis(),
                        updatedAt = null,
                        prediction = null,
                        isCompleted = false,
                        invalid = false,
                        photoUrl = null
                    )
                    onSave(goal)
                } else {
                    Toast.makeText(context, "Mohon lengkapi semua data!", Toast.LENGTH_SHORT).show()
                }
            }) {
                Text("Simpan")
            }
        }
    }
}
