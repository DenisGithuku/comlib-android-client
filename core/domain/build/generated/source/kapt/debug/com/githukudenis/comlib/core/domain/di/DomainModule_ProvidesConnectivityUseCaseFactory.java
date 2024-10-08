package com.githukudenis.comlib.core.domain.di;

import com.githukudenis.comlib.core.common.ComlibConnectivityManager;
import com.githukudenis.comlib.core.domain.usecases.GetNetworkConnectivityUseCase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class DomainModule_ProvidesConnectivityUseCaseFactory implements Factory<GetNetworkConnectivityUseCase> {
  private final Provider<ComlibConnectivityManager> connectivityManagerProvider;

  public DomainModule_ProvidesConnectivityUseCaseFactory(
      Provider<ComlibConnectivityManager> connectivityManagerProvider) {
    this.connectivityManagerProvider = connectivityManagerProvider;
  }

  @Override
  public GetNetworkConnectivityUseCase get() {
    return providesConnectivityUseCase(connectivityManagerProvider.get());
  }

  public static DomainModule_ProvidesConnectivityUseCaseFactory create(
      Provider<ComlibConnectivityManager> connectivityManagerProvider) {
    return new DomainModule_ProvidesConnectivityUseCaseFactory(connectivityManagerProvider);
  }

  public static GetNetworkConnectivityUseCase providesConnectivityUseCase(
      ComlibConnectivityManager connectivityManager) {
    return Preconditions.checkNotNullFromProvides(DomainModule.INSTANCE.providesConnectivityUseCase(connectivityManager));
  }
}
