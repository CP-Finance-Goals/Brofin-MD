package com.example.brofin.presentation.diaries

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.brofin.data.mapper.toBudgetingDiary
import com.example.brofin.presentation.detail.components.DiariesItem
import kotlinx.coroutines.launch
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiariesList(
    viewModel: DiariesListViewModel = hiltViewModel()
) {
    var startDate by remember { mutableStateOf<Long?>(null) }
    var endDate by remember { mutableStateOf<Long?>(null) }
    var minAmount by remember { mutableStateOf<Double?>(null) }
    var maxAmount by remember { mutableStateOf<Double?>(null) }
    var sortBy by remember { mutableStateOf("date") }
    var sortOrder by remember { mutableStateOf("asc") }

    // Menyaring diaries sesuai dengan filter yang dipilih
    val diaries = viewModel.getFilteredDiaries(
        startDate = startDate,
        endDate = endDate,
        minAmount = minAmount,
        maxAmount = maxAmount,
        sortBy = sortBy,
        sortOrder = sortOrder
    ).collectAsStateWithLifecycle(emptyList())

    Scaffold(
        topBar = {
            // TopAppBar dengan tombol filter
            TopAppBar(
                title = { Text(text = "Daftar Pengeluaran", style = MaterialTheme.typography.titleMedium) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                actions = {
                    // Filter Button dengan ikon berubah tergantung urutan
                    IconButton(onClick = {
                        // Toggle sort order (ASC/DESC)
                        sortOrder = if (sortOrder == "asc") "desc" else "asc"
                    }) {
                        val icon = if (sortOrder == "asc") {
                            Icons.Default.ArrowDropDown // Untuk Descending
                        } else {
                            Icons.Default.ArrowDropUp // Untuk Ascending
                        }
                        Icon(imageVector = icon, contentDescription = "Sort")
                    }
                }
            )
        },
    ){ paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (diaries.value.isEmpty()) {
                    item {
                        Text(text = "Tidak ada data")
                    }
                } else {
                    itemsIndexed(diaries.value) { index, diary ->
                        if (index == 0) {
                            Spacer(modifier = Modifier.padding(top = 8.dp))
                        }
                        DiariesItem(
                            diary = diary?.toBudgetingDiary() ?: return@itemsIndexed
                        )
                        if (index == diaries.value.size - 1) {
                            Spacer(modifier = Modifier.padding(bottom = 8.dp))
                        }
                    }
                }
            }
        }
    }
}
