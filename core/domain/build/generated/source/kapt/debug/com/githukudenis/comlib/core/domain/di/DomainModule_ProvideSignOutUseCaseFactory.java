package com.githukudenis.comlib.core.domain.di;

import com.githukudenis.comlib.core.domain.usecases.SignOutUseCase;
import com.githukudenis.comlib.data.repository.AuthRepository;
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
public final class DomainModule_ProvideSignOutUseCaseFactory implements Factory<SignOutUseCase> {
  private final Provider<AuthRepository> authRepositoryProvider;

  public DomainModule_ProvideSignOutUseCaseFactory(
      Provider<AuthRepository> authRepositoryProvider) {
    this.authRepositoryProvider = authRepositoryProvider;
  }

  @Override
  public SignOutUseCase get() {
    return provideSignOutUseCase(authRepositoryProvider.get());
  }

  public static DomainModule_ProvideSignOutUseCaseFactory create(
      Provider<AuthRepository> authRepositoryProvider) {
    return new DomainModule_ProvideSignOutUseCaseFactory(authRepositoryProvider);
  }

  public static SignOutUseCase provideSignOutUseCase(AuthRepository authRepository) {
    return Preconditions.checkNotNullFromProvides(DomainModule.INSTANCE.provideSignOutUseCase(authRepository));
  }
}
