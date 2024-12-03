package com.example.brofin.utils

import kotlinx.coroutines.flow.StateFlow

interface ConnectivityObserver {
    val isConnected: StateFlow<Boolean>
}