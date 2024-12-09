package com.example.brofin.presentation.main.financials

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.brofin.domain.models.Predict
import com.example.brofin.presentation.expenses.ConfirmationDialog
import com.example.brofin.presentation.main.financials.components.PredictTable

@Composable
fun FavoriteSelewted(
    modifier: Modifier = Modifier,
    financialViewModel: FinancialViewModel = hiltViewModel()
) {
    val statePredict = financialViewModel.statePredict.collectAsStateWithLifecycle().value
    var showConfirmationDelete by remember { mutableStateOf(false) }
    var predictSelectedDelete by remember { mutableStateOf<Predict?>(null) }
    val context = LocalContext.current

    Box(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
    ) {


        ConfirmationDialog(
            showDialog = showConfirmationDelete,
            message = "Apakah anda yakin ingin menghapus data ini?",
            onConfirm = {
                showConfirmationDelete = false
                if (predictSelectedDelete != null) {
                    financialViewModel.deletePredict(predictSelectedDelete!!)
                } else {
                    Toast.makeText(
                        context,
                        "Gagal menghapus prediksi",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }) {
            showConfirmationDelete = false
        }

        Column {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if (statePredict.isNotEmpty()){
                    items(statePredict){items ->
                        PredictTable(items!!, ondelete = {dataPredict ->
                            predictSelectedDelete = dataPredict
                            showConfirmationDelete = true
                        })
                    }
                }else{
                    item{
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Favorite Kosong",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }
                item{
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }
}

