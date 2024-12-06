package com.example.brofin.presentation.main.financials

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.wear.compose.material3.ScrollIndicator
import com.example.brofin.domain.StateApp
import com.example.brofin.presentation.components.LoadingDialog
import com.example.brofin.presentation.components.NetworkErrorDialog
import com.example.brofin.presentation.main.financials.components.OpenTextDialog2
import com.example.brofin.presentation.main.financials.recommendation.components.GadgetItem
import com.example.brofin.presentation.main.financials.recommendation.components.GameItem
import com.example.brofin.presentation.main.financials.recommendation.components.LuxuryItem
import com.example.brofin.presentation.main.financials.recommendation.components.MobilItem
import com.example.brofin.presentation.main.financials.recommendation.components.MotorItem
import com.example.brofin.utils.toFormattedDate
import com.example.brofin.utils.toIndonesianCurrency2


@Composable
fun FinancialSelected(
    modifier: Modifier = Modifier,
    viewmodel: FinancialViewModel = hiltViewModel()
) {
    val stateGame = viewmodel.gameState.collectAsStateWithLifecycle().value
    val stateLuxury = viewmodel.luxuryState.collectAsStateWithLifecycle().value
    val stateMobil = viewmodel.mobilState.collectAsStateWithLifecycle().value
    val stateMotor = viewmodel.motorState.collectAsStateWithLifecycle().value
    val stateGadget = viewmodel.gadgetState.collectAsStateWithLifecycle().value

    val context = LocalContext.current

    var showLoading by remember {
        mutableStateOf(false)
    }

    var showError by remember {
        mutableStateOf(false)
    }

    var messageError by remember {
        mutableStateOf("")
    }

    fun <T> filterNonNullItems(state: StateApp<List<T>>): List<T> {
        return when (state) {
            is StateApp.Success -> state.data.filterNotNull()
            else -> emptyList()
        }
    }

    val gameItems = filterNonNullItems(stateGame)
    val luxuryItems = filterNonNullItems(stateLuxury)
    val mobilItems = filterNonNullItems(stateMobil)
    val motorItems = filterNonNullItems(stateMotor)
    val gadgetItems = filterNonNullItems(stateGadget)

    LaunchedEffect(stateGame, stateLuxury, stateMobil, stateMotor, stateGadget) {
        var anyLoading = false
        var anyError = false
        var errorMessage = ""

        listOf(stateGame, stateLuxury, stateMobil, stateMotor, stateGadget).forEach { state ->
            when (state) {
                is StateApp.Loading -> anyLoading = true
                is StateApp.Error -> {
                    anyError = true
                    errorMessage = state.exception
                }
                StateApp.Idle -> {

                }
                is StateApp.Success -> {
                    anyLoading = false
                    Toast.makeText(context, "Rekomendasi berhasil di dapatkan", Toast.LENGTH_SHORT).show()
                }
            }
        }

        showLoading = anyLoading
        showError = anyError
        messageError = errorMessage

        if (!anyLoading && !anyError) {
            showLoading = false
            showError = false
        }
    }

    Box(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
    ) {


        LoadingDialog(showDialog = showLoading) {
            showLoading = false
        }

        NetworkErrorDialog(showDialog = showError, message = messageError) {
            showError = false
        }

       Column {
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
               ContentBox2()
           }

           HorizontalDivider(
               modifier = Modifier.fillMaxWidth()
           )

           // LazyColumn wrapped in a Scrollbar
           val listState = rememberLazyListState()

           Box(Modifier.fillMaxSize()) {
               LazyColumn(
                   state = listState,
                   modifier = Modifier
                       .padding(8.dp)
                       .fillMaxWidth()
                       .verticalScroll(rememberScrollState()) // for smooth scrolling
               ) {

                   item {
                       Column(
                           modifier = Modifier.fillMaxWidth()
                       ) {
                           Text(
                               text = "Rekomendasi",
                               style = MaterialTheme.typography.headlineSmall,
                               fontWeight = FontWeight.Bold,
                               color = MaterialTheme.colorScheme.onSurface
                           )
                       }
                   }

                   if (gameItems.isNotEmpty()) {
                       items(gameItems) { gameItem ->
                           GameItem(
                               modifier = Modifier.padding(vertical = 8.dp),
                               game = gameItem ?: return@items
                           )
                       }
                   }

                   if (luxuryItems.isNotEmpty()) {
                       items(luxuryItems) { luxuryItem ->
                           LuxuryItem(
                               modifier = Modifier.padding(vertical = 8.dp),
                               luxury = luxuryItem ?: return@items
                           )
                       }
                   }

                   if (mobilItems.isNotEmpty()) {
                       items(mobilItems) { mobilItem ->
                           MobilItem(
                               modifier = Modifier.padding(vertical = 8.dp),
                               mobil = mobilItem ?: return@items
                           )
                       }
                   }

                   if (motorItems.isNotEmpty()) {
                       items(motorItems) { motorItem ->
                           MotorItem(
                               modifier = Modifier.padding(vertical = 8.dp),
                               motor = motorItem ?: return@items
                           )
                       }
                   }

                   if (gadgetItems.isNotEmpty()) {
                       items(gadgetItems) { gadgetItem ->
                           GadgetItem(
                               modifier = Modifier.padding(vertical = 8.dp),
                               gadget = gadgetItem ?: return@items
                           )
                       }
                   }
               }

               ScrollIndicator(
                   state = listState,
               )
           }
       }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentBox2(
    viewmodel: FinancialViewModel = hiltViewModel()
) {
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = System.currentTimeMillis())
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf("Pilih Kategori") }
    var selectedIndex by remember { mutableIntStateOf(0) }
    val options = listOf("Mobil", "Gadget", "Motor", "Luxury Brand", "Games")

    val priceRanges = mapOf(
        "Mobil" to Pair(100000000.0, 16500000000.0),
        "Gadget" to Pair(110000.0, 17990000.0),
        "Motor" to Pair(1080000.0, 300000000.0),
        "Luxury Brand" to Pair(400000.0, 6200000.0),
        "Games" to Pair(4500.0, 260000.0),
    )

    var editItemText by remember { mutableStateOf("") }
    var showEditItemItemText by remember { mutableStateOf(false) }

    val listNamaItem = remember {
        mutableStateListOf("Jumlah Target Uang")
    }

    val listNilai = remember {
        mutableStateListOf("0")
    }

    var isValidInput by remember { mutableStateOf(true) }


    val combined = listNamaItem.zip(listNilai) { nama, nilai ->
        Pair(nama, nilai)
    }

    var minAmount by remember { mutableDoubleStateOf(0.0) }
    var maxAmount by remember { mutableDoubleStateOf(0.0) }

    val context = LocalContext.current

    LaunchedEffect(selectedOptionText) {
        val range = priceRanges[selectedOptionText]
        if (range != null) {
            minAmount = range.first
            maxAmount = range.second
        }
    }

    fun validateInput(): Boolean {
        val inputValue = listNilai[0].toDoubleOrNull()
        return inputValue != null && inputValue in minAmount..maxAmount
    }

    LaunchedEffect(listNilai[0]) {
        isValidInput = validateInput()
    }

    Column(
        Modifier.verticalScroll(state = rememberScrollState())
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = it }
        ) {
            TextField(
                value = selectedOptionText,
                onValueChange = { selectedOptionText = it },
                trailingIcon = { Icon(Icons.Default.ArrowDropDown, tint = MaterialTheme.colorScheme.onSurface, contentDescription = null) },
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
                            selectedIndex = options.indexOf(selectionOption)
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
        Spacer(modifier = Modifier.height(10.dp))

        combined.forEachIndexed { _, pair ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(0.8f)) {
                    RowNamaItem(pair.first)
                }

                Column(modifier = Modifier.weight(0.1f)) {
                    RowTitik2()
                }

                Column(modifier = Modifier.weight(0.6f)) {
                    RowItem(pair.second.toDouble().toIndonesianCurrency2())
                }

                Column(modifier = Modifier.weight(0.2f)) {
                    RowEditButton {
                        showEditItemItemText = true
                        editItemText = pair.second
                    }
                }
            }
        }

        PredictButton2(
            enabled = isValidInput,
            onClick = {
                if (isValidInput) {
                    viewmodel.recommendationPredict(selectedIndex, listNilai[0].toDouble())
                } else {
                    Toast.makeText(
                        context,
                        "Jumlah uang harus di antara Rp $minAmount - Rp $maxAmount",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        )

        if (showEditItemItemText) {
            OpenTextDialog2(
                textFieldValue = editItemText,
                onTextChange = { editItemText = it },
                label = "Jumlah Target Uang",
                onDismiss = {
                    showEditItemItemText = false
                },
                onConfirm = {
                    val inputValue = editItemText.toDoubleOrNull()
                    if (inputValue != null && inputValue in minAmount..maxAmount) {
                        listNilai[0] = editItemText
                        showEditItemItemText = false
                    } else {
                        Toast.makeText(
                            context,
                            "Input uang harus di antara Rp $minAmount - Rp $maxAmount",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                minAmount = minAmount,
                maxAmount = maxAmount
            )
        }

        if (showDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    Button(
                        onClick = {
                            datePickerState.selectedDateMillis?.let {
                                listNilai[1] = it.toFormattedDate()
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
                        showDatePicker = false
                    }) {
                        Text("Batal")
                    }
                },
                content = {
                    DatePicker(
                        state = datePickerState,
                        showModeToggle = false
                    )
                }
            )
        }
    }
}




fun Modifier.borderDashed(
    width: Dp,
    color: Color,
    dashLength: Float = 10f,
    gapLength: Float = 6f
): Modifier = this.then(
    Modifier.drawBehind {
        val size = this.size
        val cornerRadius = 16.dp.toPx()

        val paint = Paint().apply {
            this.color = color
            this.style = Stroke
            this.strokeWidth = width.toPx()
            this.pathEffect = PathEffect.dashPathEffect(floatArrayOf(dashLength, gapLength))
        }

        val path = Path().apply {
            addRoundRect(
                RoundRect(
                    left = 0f,
                    top = 0f,
                    right = size.width,
                    bottom = size.height,
                    radiusX = cornerRadius,
                    radiusY = cornerRadius
                )
            )
        }

        drawIntoCanvas { canvas ->
            canvas.drawPath(path, paint)
        }
    }
)
