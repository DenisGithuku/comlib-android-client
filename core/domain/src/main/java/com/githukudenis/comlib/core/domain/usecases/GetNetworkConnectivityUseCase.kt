package com.githukudenis.comlib.core.domain.usecases

import com.githukudenis.comlib.core.common.ComlibConnectivityManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

class GetNetworkConnectivityUseCase @Inject constructor(
    private val comlibConnectivityManager: ComlibConnectivityManager
) {
    val isConnected: Flow<Boolean> = comlibConnectivityManager
        .isConnected
        .mapLatest { it }
}