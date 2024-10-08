package com.githukudenis.comlib.core.domain.usecases;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t\u00a8\u0006\n"}, d2 = {"Lcom/githukudenis/comlib/core/domain/usecases/GetNetworkConnectivityUseCase;", "", "comlibConnectivityManager", "Lcom/githukudenis/comlib/core/common/ComlibConnectivityManager;", "(Lcom/githukudenis/comlib/core/common/ComlibConnectivityManager;)V", "networkStatus", "Lkotlinx/coroutines/flow/Flow;", "Lcom/githukudenis/comlib/core/common/NetworkStatus;", "getNetworkStatus", "()Lkotlinx/coroutines/flow/Flow;", "domain_debug"})
public final class GetNetworkConnectivityUseCase {
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.Flow<com.githukudenis.comlib.core.common.NetworkStatus> networkStatus = null;
    
    @javax.inject.Inject
    public GetNetworkConnectivityUseCase(@org.jetbrains.annotations.NotNull
    com.githukudenis.comlib.core.common.ComlibConnectivityManager comlibConnectivityManager) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<com.githukudenis.comlib.core.common.NetworkStatus> getNetworkStatus() {
        return null;
    }
}