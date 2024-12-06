package com.example.brofin.presentation.main.financials

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.brofin.domain.StateApp
import com.example.brofin.presentation.components.LoadingDialog
import com.example.brofin.presentation.components.NetworkErrorDialog
import com.example.brofin.presentation.main.financials.components.ContentBox2
import com.example.brofin.presentation.main.financials.components.borderDashed
import com.example.brofin.presentation.main.financials.recommendation.components.GadgetItem
import com.example.brofin.presentation.main.financials.recommendation.components.GameItem
import com.example.brofin.presentation.main.financials.recommendation.components.LuxuryItem
import com.example.brofin.presentation.main.financials.recommendation.components.MobilItem
import com.example.brofin.presentation.main.financials.recommendation.components.MotorItem


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

           Spacer(modifier = Modifier.height(8.dp))

           HorizontalDivider(
               thickness = 1.dp,
               modifier = Modifier.fillMaxWidth()
           )

           LazyColumn(
               modifier = Modifier
                   .padding(8.dp)
                   .fillMaxSize()
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
       }
    }
}

