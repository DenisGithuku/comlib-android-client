package com.githukudenis.comlib.core.domain.usecases

import com.githukudenis.comlib.core.common.ComlibConnectivityManager
import com.githukudenis.comlib.core.common.NetworkStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import timber.log.Timber
import javax.inject.Inject

class GetNetworkConnectivityUseCase @Inject constructor(
    comlibConnectivityManager: ComlibConnectivityManager
) {
    val  networkStatus: Flow<NetworkStatus> = comlibConnectivityManager
        .networkStatus
        .mapLatest {
            Timber.tag("netstatus").d("networkStatus: $it")
            it
        }
}