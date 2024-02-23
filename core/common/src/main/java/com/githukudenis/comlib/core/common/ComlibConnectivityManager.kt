package com.githukudenis.comlib.core.common

import kotlinx.coroutines.flow.Flow

interface ComlibConnectivityManager {
    fun observe(): Flow<NetworkStatus>
}