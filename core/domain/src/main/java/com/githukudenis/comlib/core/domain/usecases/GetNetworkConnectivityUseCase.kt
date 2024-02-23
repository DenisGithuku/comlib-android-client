package com.githukudenis.comlib.core.domain.usecases

import com.githukudenis.comlib.core.common.ComlibConnectivityManager
import com.githukudenis.comlib.core.common.NetworkStatus
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNetworkConnectivityUseCase @Inject constructor(
    comlibConnectivityManager: ComlibConnectivityManager
) {
    val  networkStatus: Flow<NetworkStatus> = comlibConnectivityManager
        .observe()

}