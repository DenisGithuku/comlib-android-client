package com.githukudenis.comlib.core.domain.di;

import com.githukudenis.comlib.core.domain.usecases.GetTimePeriodUseCase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

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
public final class DomainModule_ProvideGetTimePeriodUseCaseFactory implements Factory<GetTimePeriodUseCase> {
  @Override
  public GetTimePeriodUseCase get() {
    return provideGetTimePeriodUseCase();
  }

  public static DomainModule_ProvideGetTimePeriodUseCaseFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static GetTimePeriodUseCase provideGetTimePeriodUseCase() {
    return Preconditions.checkNotNullFromProvides(DomainModule.INSTANCE.provideGetTimePeriodUseCase());
  }

  private static final class InstanceHolder {
    private static final DomainModule_ProvideGetTimePeriodUseCaseFactory INSTANCE = new DomainModule_ProvideGetTimePeriodUseCaseFactory();
  }
}
