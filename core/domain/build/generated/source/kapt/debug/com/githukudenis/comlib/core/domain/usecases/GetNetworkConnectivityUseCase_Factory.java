package com.githukudenis.comlib.core.domain.usecases;

import com.githukudenis.comlib.core.common.ComlibConnectivityManager;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class GetNetworkConnectivityUseCase_Factory implements Factory<GetNetworkConnectivityUseCase> {
  private final Provider<ComlibConnectivityManager> comlibConnectivityManagerProvider;

  public GetNetworkConnectivityUseCase_Factory(
      Provider<ComlibConnectivityManager> comlibConnectivityManagerProvider) {
    this.comlibConnectivityManagerProvider = comlibConnectivityManagerProvider;
  }

  @Override
  public GetNetworkConnectivityUseCase get() {
    return newInstance(comlibConnectivityManagerProvider.get());
  }

  public static GetNetworkConnectivityUseCase_Factory create(
      Provider<ComlibConnectivityManager> comlibConnectivityManagerProvider) {
    return new GetNetworkConnectivityUseCase_Factory(comlibConnectivityManagerProvider);
  }

  public static GetNetworkConnectivityUseCase newInstance(
      ComlibConnectivityManager comlibConnectivityManager) {
    return new GetNetworkConnectivityUseCase(comlibConnectivityManager);
  }
}
