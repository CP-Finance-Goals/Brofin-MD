package com.example.brofin.presentation.expenses

import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.text.input.ImeAction
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
import com.example.brofin.utils.PaymentMethode
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

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
    val date by remember { mutableLongStateOf(System.currentTimeMillis()) }
    var photoUriData by remember { mutableStateOf<Uri?>(null) }

    LaunchedEffect(state) {
        when(state){
            is StateApp.Error -> {
                isLoading = false
                Toast.makeText(context, (state as StateApp.Error).exception, Toast.LENGTH_SHORT).show()
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
                .padding(16.dp)
        ) {


            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.verticalScroll(scrollState)
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Tanggal: ${SimpleDateFormat("dd MMM yyyy").format(date)}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.weight(1f)
                    )
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Pilih Tanggal"
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

                // Amount Input
                OutlinedTextField(
                    value = amount,
                    onValueChange = { amount = it },
                    label = { Text("Jumlah") },
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.primary
                    ),
                )

                // Description Input
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    shape = RoundedCornerShape(16.dp),
                    label = { Text("Deskripsi (Opsional)") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    maxLines = 4,
                    colors = OutlinedTextFieldDefaults.colors(

                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.primary
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),

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
                        val parsedAmount = if (amount.isNotEmpty()) {
                            try {
                                amount.toDouble()
                            } catch (e: NumberFormatException) {
                                Toast.makeText(context, "Jumlah tidak valid", Toast.LENGTH_SHORT).show()
                                return@Button
                            }

                        } else {
                            Toast.makeText(context, "Jumlah harus diisi", Toast.LENGTH_SHORT).show()
                            return@Button
                        }

                        addExpensesViewModel.insert(
                            date = date,
                            photoUri = photoUriData.toString(),
                            description = description,
                            amount = parsedAmount,
                            categoryId = idCategory,
                            isExpense = true,
                        )
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
                                "Kategori ${category.namaKategori} dipilih",
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
                            val selectedAttachment = when (id) {
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

            LoadingDialog(showDialog = isLoading) {
                isLoading = false
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

@Composable
fun PaymentMethodSelector(
    selectedPaymentMethod: PaymentMethode?,
    onPaymentMethodSelected: (PaymentMethode) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val paymentMethods = PaymentMethode.entries

    Column {
        Text(
            text = "Pilih Metode Pembayaran",
            style = MaterialTheme.typography.bodyMedium
        )

        // Dropdown Button
        Box(modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = { expanded = !expanded },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = selectedPaymentMethod?.name ?: "Pilih Metode")
            }

            // Dropdown Menu
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                paymentMethods.forEach { method ->
                    DropdownMenuItem(
                        onClick = {
                            onPaymentMethodSelected(method)
                            expanded = false
                        },
                        text = {
                            Text(text = method.name)
                        }

                    )
                }
            }
        }
    }
}

