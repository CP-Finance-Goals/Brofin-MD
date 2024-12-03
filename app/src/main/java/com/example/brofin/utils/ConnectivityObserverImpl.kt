package com.example.brofin.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.core.content.ContextCompat
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class ConnectivityObserverImpl @Inject constructor (
    private val context: Context
): ConnectivityObserver {

    private val _isConnected = MutableStateFlow(false)
    override val isConnected: StateFlow<Boolean> get() = _isConnected

    init {
        val connectivityManager = ContextCompat.getSystemService(context, ConnectivityManager::class.java)
        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: android.net.Network) {
                super.onAvailable(network)
                _isConnected.value = true
            }

            override fun onLost(network: android.net.Network) {
                super.onLost(network)
                _isConnected.value = false
            }
        }

        connectivityManager?.registerDefaultNetworkCallback(networkCallback)
        _isConnected.value = isInternetAvailable()
    }

    private fun isInternetAvailable(): Boolean {
        val connectivityManager = ContextCompat.getSystemService(context, ConnectivityManager::class.java)
        val activeNetwork = connectivityManager?.activeNetwork
        val networkCapabilities = connectivityManager?.getNetworkCapabilities(activeNetwork)

        return networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
    }
}
