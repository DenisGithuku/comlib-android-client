package com.githukudenis.comlib.core.common

import kotlinx.coroutines.flow.Flow

interface ComlibConnectivityManager {
    val networkStatus: Flow<NetworkStatus>
}