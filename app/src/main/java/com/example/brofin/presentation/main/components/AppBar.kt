package com.example.brofin.presentation.main.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.brofin.ui.theme.BrofinTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    title: String,
    onAccountClick: () -> Unit = {},
    onNotificationClick: () -> Unit = {},
){
    TopAppBar(
        title = {
            Text(text = title)
        },
        actions = {
            IconButton(onClick = { onNotificationClick() }) {
                Icon(imageVector = Icons.Filled.Notifications, contentDescription = "Account")
            }
            IconButton(onClick = { onAccountClick() }) {
                Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "Account")
            }
        },
    )
}

