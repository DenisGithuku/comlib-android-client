package com.githukudenis.comlib.core.common

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class ComlibConnectivityManager(
    private val context: Context
) {

    private val connectivityManager: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE)
    as ConnectivityManager

    private val _isConnected: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isConnected: Flow<Boolean> = _isConnected


    init {
        observeNetworkConnectivity()
    }

    @SuppressLint("MissingPermission", "NewApi")
    private fun observeNetworkConnectivity() {
        connectivityManager.registerDefaultNetworkCallback(object: ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: android.net.Network) {
                super.onAvailable(network)
                _isConnected.update { true }
            }
            override fun onLost(network: android.net.Network) {
                super.onLost(network)
                _isConnected.update { false }
            }
        })
    }
}