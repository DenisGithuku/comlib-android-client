package com.githukudenis.comlib.core.domain.di;

import com.githukudenis.comlib.core.domain.usecases.FormatDateUseCase;
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
public final class DomainModule_ProvideFormatDateUseCaseFactory implements Factory<FormatDateUseCase> {
  @Override
  public FormatDateUseCase get() {
    return provideFormatDateUseCase();
  }

  public static DomainModule_ProvideFormatDateUseCaseFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static FormatDateUseCase provideFormatDateUseCase() {
    return Preconditions.checkNotNullFromProvides(DomainModule.INSTANCE.provideFormatDateUseCase());
  }

  private static final class InstanceHolder {
    private static final DomainModule_ProvideFormatDateUseCaseFactory INSTANCE = new DomainModule_ProvideFormatDateUseCaseFactory();
  }
}
