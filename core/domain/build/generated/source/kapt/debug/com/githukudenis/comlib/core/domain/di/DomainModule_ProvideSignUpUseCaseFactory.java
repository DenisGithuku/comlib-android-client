package com.githukudenis.comlib.core.domain.di;

import com.githukudenis.comlib.core.domain.usecases.SignUpUseCase;
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
public final class DomainModule_ProvideSignUpUseCaseFactory implements Factory<SignUpUseCase> {
  private final Provider<AuthRepository> authRepositoryProvider;

  public DomainModule_ProvideSignUpUseCaseFactory(Provider<AuthRepository> authRepositoryProvider) {
    this.authRepositoryProvider = authRepositoryProvider;
  }

  @Override
  public SignUpUseCase get() {
    return provideSignUpUseCase(authRepositoryProvider.get());
  }

  public static DomainModule_ProvideSignUpUseCaseFactory create(
      Provider<AuthRepository> authRepositoryProvider) {
    return new DomainModule_ProvideSignUpUseCaseFactory(authRepositoryProvider);
  }

  public static SignUpUseCase provideSignUpUseCase(AuthRepository authRepository) {
    return Preconditions.checkNotNullFromProvides(DomainModule.INSTANCE.provideSignUpUseCase(authRepository));
  }
}
