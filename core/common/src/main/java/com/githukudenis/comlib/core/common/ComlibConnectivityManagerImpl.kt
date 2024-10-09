
/*
* Copyright 2023 Denis Githuku
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* https://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.githukudenis.comlib.core.common

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.os.Build
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class ComlibConnectivityManagerImpl(context: Context) : ComlibConnectivityManager {

    private val connectivityManager: ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    override fun observe(): Flow<NetworkStatus> =
        callbackFlow {
                val connectionCallback =
                    object : NetworkCallback() {
                        override fun onAvailable(network: Network) {
                            super.onAvailable(network)
                            launch { send(NetworkStatus.Available) }
                        }

                        override fun onLost(network: Network) {
                            super.onLost(network)
                            launch { send(NetworkStatus.Lost) }
                        }

                        override fun onUnavailable() {
                            super.onUnavailable()
                            launch { send(NetworkStatus.Unavailable) }
                        }

                        override fun onLosing(network: Network, maxMsToLive: Int) {
                            super.onLosing(network, maxMsToLive)
                            launch { send(NetworkStatus.Losing) }
                        }
                    }

            connectivityManager.registerDefaultNetworkCallback(connectionCallback)

            awaitClose { connectivityManager.unregisterNetworkCallback(connectionCallback) }
            }
            .distinctUntilChanged()
}
