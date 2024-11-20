package com.example.brofin.presentation.expenses

import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.brofin.R
import com.example.brofin.domain.StateApp
import com.example.brofin.presentation.components.LoadingDialog
import com.example.brofin.presentation.expenses.components.AttachmentBottomSheet
import com.example.brofin.presentation.expenses.components.CategoryModalBottomSheet
import com.example.brofin.presentation.main.home.components.CustomTextFieldTwo
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenses(
    modifier: Modifier = Modifier,
    photoUri: Uri?,
    onBackClick: () -> Unit,
    currentBackStack: () -> Unit,
    goCamera: () -> Unit,
    goGallery: () -> Unit,
    goback: () -> Unit,
    addExpensesViewModel: AddExpensesViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        currentBackStack()
    }

    val state by addExpensesViewModel.addState.collectAsStateWithLifecycle()

    val bottomSheetState = rememberModalBottomSheetState()
    val sheetStateAttachment = rememberModalBottomSheetState()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    var isLoading by remember { mutableStateOf(false) }
    var isVisible by remember { mutableStateOf(false) }
    var attachmentVisible by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf("Pilih Kategori") }
    var idCategory by remember { mutableIntStateOf(0) }
    var description by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var photoUriData by remember { mutableStateOf<Uri?>(null) }
    var showDatePicker by remember { mutableStateOf(false) }
    var isDatePickerVisible by remember { mutableStateOf(false) }
    var date by remember { mutableLongStateOf(System.currentTimeMillis()) }
    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = System.currentTimeMillis())

    LaunchedEffect(state) {
        when(state){
            is StateApp.Error -> {
                isLoading = false
                Toast.makeText(context, (state as StateApp.Error).exception, Toast.LENGTH_SHORT).show()
                addExpensesViewModel.resetState()
            }
            StateApp.Idle -> {
                isLoading = false
            }
            StateApp.Loading -> {
                isLoading = true
            }
            is StateApp.Success ->{
                isLoading = false
                goback()
                addExpensesViewModel.resetState()
            }
        }
    }


    LaunchedEffect(photoUri) {
        photoUriData = photoUri
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tambahkan Pengeluaran") },
                navigationIcon = {
                    IconButton(onClick = { onBackClick() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Kembali")
                    }
                }
            )
        }
    ) { paddingValues ->

        Box(
            modifier = modifier
                .padding(paddingValues)

        ) {
            LoadingDialog(showDialog = isLoading) {
                isLoading = false
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.verticalScroll(scrollState).padding(16.dp)
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Tanggal: ${SimpleDateFormat("dd MMMM yyyy", Locale("id")).format(Date(date))}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.weight(1f)
                    )
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Pilih Tanggal",
                        modifier = Modifier.clickable(
                            onClick = {
                                showDatePicker = true
                            }
                        )
                    )
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { isVisible = true },
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    shape = MaterialTheme.shapes.medium,
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = selectedCategory,
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

                CustomTextFieldTwo(
                    label = "Jumlah",
                    text = amount,
                    onTextChange = {
                        amount = it
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

                CustomTextFieldTwo(
                    label = "Catatan (Opsional)",
                    text = description,
                    onTextChange = {
                        description = it
                    },
                    maxLines = 4,
                    height = 120.dp,
                    singleLine = false
                )

                DashedBorderCard(onClick = {
                    attachmentVisible = true
                    scope.launch { sheetStateAttachment.show() }
                })


                Button(
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    onClick = {
                        when{
                            amount.isEmpty() -> Toast.makeText(context, "Jumlah tidak boleh kosong", Toast.LENGTH_SHORT).show()
                            amount.toDoubleOrNull() == null -> Toast.makeText(context, "Jumlah harus berupa angka", Toast.LENGTH_SHORT).show()
                            amount.toDouble() < 0 -> Toast.makeText(context, "Jumlah tidak boleh kurang dari 0", Toast.LENGTH_SHORT).show()
                            selectedCategory == "Pilih Kategori" -> Toast.makeText(context, "Kategori tidak boleh kosong", Toast.LENGTH_SHORT).show()
                            else -> {
                                addExpensesViewModel.insert(
                                    date = date,
                                    amount = amount.toDouble(),
                                    description = description,
                                    photoUri = photoUriData.toString(),
                                    categoryId = idCategory,
                                )
                            }
                        }
                    }
                ) {
                    Text("Tambahkan Pengeluaran")
                }

                if (photoUriData != null) {
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data(
                                photoUriData ?: R.drawable.placeholder
                            )
                            .crossfade(true)
                            .build(),
                        contentDescription = "Photo",
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp)),
                        placeholder = painterResource(R.drawable.placeholder),
                        error = painterResource(R.drawable.placeholder)
                    )
                }

                if (isVisible) {
                    CategoryModalBottomSheet(
                        bottomSheetState = bottomSheetState,
                        onDismisRequest = {
                            scope.launch { bottomSheetState.hide() }
                            isVisible = false
                        },
                        onClick = { category ->
                            Toast.makeText(
                                context,
                                "Kategori ${category.id} dipilih",
                                Toast.LENGTH_SHORT
                            ).show()
                            scope.launch { bottomSheetState.hide() }
                            isVisible = false
                            selectedCategory = category.namaKategori
                            idCategory = category.id
                        }
                    )
                }

                if (attachmentVisible) {
                    ModalBottomSheet(
                        onDismissRequest = {
                            scope.launch { sheetStateAttachment.hide() }
                            attachmentVisible = false
                        },
                        sheetState = sheetStateAttachment,
                        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                    ) {
                        AttachmentBottomSheet(onClick = { id ->
                            when (id) {
                                1 -> {
                                    goCamera()
                                    "Kamera"
                                }

                                2 -> {
                                    goGallery()
                                    "Galeri"
                                }

                                else -> "Unknown"
                            }
                            scope.launch { sheetStateAttachment.hide() }
                            attachmentVisible = false
                        })
                    }
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
}



@Composable
fun DashedBorderCard(onClick: () -> Unit) {
    val color = MaterialTheme.colorScheme.primary
    Box(
        modifier = Modifier
            .fillMaxWidth()


    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onClick()
                },
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            ),
            shape = MaterialTheme.shapes.medium,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Row(
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.AttachFile,
                    contentDescription = "Tambahkan Gambar",
                    tint = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Tambahkan Gambar (Opsional)",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
        }

        Canvas(modifier = Modifier.matchParentSize()) {
            val dashWidth = 10f
            val dashGap = 10f
            val strokeWidth = 4f

            drawRoundRect(
                color = color.copy(alpha = 0.5f),
                topLeft = Offset.Zero,
                size = Size(size.width, size.height),
                style = Stroke(
                    width = strokeWidth,
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(dashWidth, dashGap), 0f)
                ),
                cornerRadius = CornerRadius(16.dp.toPx())
            )
        }
    }
}
