package com.githukudenis.comlib.core.common

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged

class ComlibConnectivityManagerImpl(
    context: Context
): ComlibConnectivityManager {

    private val connectivityManager: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE)
    as ConnectivityManager

    override val networkStatus: Flow<NetworkStatus>
        @SuppressLint("MissingPermission")
        get() = callbackFlow {
            val networkRequest = NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .build()

            val connectionCallback = object : NetworkCallback() {
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    trySend(NetworkStatus.CONNECTED)
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    trySend(NetworkStatus.DISCONNECTED)
                }
            }

            connectivityManager.registerNetworkCallback(networkRequest, connectionCallback)

            awaitClose {
                connectivityManager.unregisterNetworkCallback(connectionCallback)
            }
        }.distinctUntilChanged()
}