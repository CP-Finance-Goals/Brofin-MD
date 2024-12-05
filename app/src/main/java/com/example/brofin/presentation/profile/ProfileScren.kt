package com.example.brofin.presentation.profile

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.brofin.R
import com.example.brofin.domain.StateApp
import com.example.brofin.presentation.components.ErrorDialog
import com.example.brofin.presentation.components.LoadingDialog
import com.example.brofin.presentation.detail.components.ImageWithLoadingIndicator
import com.example.brofin.presentation.main.home.components.CustomTextFieldTwo
import com.example.brofin.utils.getFormattedTimeInMillis
import com.example.brofin.utils.toFormattedDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewmodel: ProfileViewModel = hiltViewModel(),
    profileUri: Uri?,
    goCamera: () -> Unit,
    backHandler : () -> Unit,
    currenhtBackStack : () -> Unit
) {

    val userState = viewmodel.dataProfile.collectAsStateWithLifecycle().value
    val updateSucces = viewmodel.updatedSucces.collectAsStateWithLifecycle().value
    val context = LocalContext.current
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf("") }
    var photoUrl by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var photoUriData by remember { mutableStateOf<Uri?>(null) }
    var showErrorDialog by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf("") }

    LaunchedEffect(updateSucces) {
        if (updateSucces) {
            Toast.makeText(context, "Berhasil mengubah data", Toast.LENGTH_SHORT).show()
            backHandler()
            viewmodel.resetState()
        }
    }

    LaunchedEffect(profileUri) {
        photoUriData = profileUri
    }

    LaunchedEffect(Unit) {
        currenhtBackStack()
    }

    BackHandler(enabled = true) {
        backHandler()
    }

    LaunchedEffect(userState) {
        when (userState) {
            is StateApp.Success -> {
                showErrorDialog = false
                showDialog = false
                val user = userState.data
                name = user.name ?: ""
                email = user.email ?: ""
                dob = user.dob ?: ""
                photoUrl = user.photoUrl ?: ""
            }
            is StateApp.Error -> {
                showErrorDialog = true
                message = userState.exception
            }
            is StateApp.Idle -> {
                showErrorDialog = false
                showDialog = false
            }
            is StateApp.Loading -> {
                showErrorDialog = false
                showDialog = true
            }
        }

    }

    var showDatePicker by remember { mutableStateOf(false) }
    var isDatePickerVisible by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = System.currentTimeMillis())
    val contentResolver = context.contentResolver

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
    ) {paddingValues ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
        ){

            LoadingDialog(showDialog = showDialog) {
                showDialog = false
            }

            ErrorDialog(showDialog = showErrorDialog, message = message) {
                showErrorDialog = false
                viewmodel.resetState()
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                Column (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(200.dp)
                            .clip(CircleShape)
                            .background(Color.LightGray)
                    ) {
                        if (photoUriData != null) {
                            AsyncImage(
                                model = ImageRequest.Builder(context)
                                    .data(photoUriData)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = "Photo",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp)),
                                placeholder = painterResource(R.drawable.placeholder),
                                error = painterResource(R.drawable.placeholder)
                            )
                        } else {
                            ImageWithLoadingIndicator(photoUrl)
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        text = "Ganti foto profil >",
                        color = Color.Blue,
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .clickable(
                                onClick = {
                                    goCamera()
                                }
                            )
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                CustomTextFieldTwo(
                    label = "Nama",
                    text = name,
                    onTextChange = {
                        name = it
                    },
                    singleLine = true,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(16.dp))
                CustomTextFieldTwo(
                    label = "Email",
                    text = email,
                    onTextChange = {
                    },
                    enable = false
                )
                Spacer(modifier = Modifier.height(16.dp))

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showDatePicker = true },
                    shape = MaterialTheme.shapes.medium,
                    border = CardDefaults.outlinedCardBorder().copy(
                        width = 1.dp,
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
                            )
                        )
                    ),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Tanggal ulang tahun: $dob",
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

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center

                ) {
                    OutlinedButton(
                        onClick = {
                            backHandler()
                        },
                    ) {
                        Text("Batal")
                    }
                    Spacer(modifier = Modifier.width(16.dp))

                    Button(onClick = {
                        viewmodel.editProfile(name, dob, photoUriData, contentResolver, context)
                    },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)) {
                        Text("Simpan")
                    }
                }

                if (showDatePicker) {
                    DatePickerDialog(
                        onDismissRequest = { showDatePicker = false },
                        confirmButton = {
                            Button(
                                onClick = {
                                    datePickerState.selectedDateMillis?.let {
                                        dob = getFormattedTimeInMillis(it).toFormattedDate()
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
}

