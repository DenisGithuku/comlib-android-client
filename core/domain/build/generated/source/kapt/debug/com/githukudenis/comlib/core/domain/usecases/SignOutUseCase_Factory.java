package com.githukudenis.comlib.core.domain.usecases;

import com.githukudenis.comlib.data.repository.AuthRepository;
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
public final class SignOutUseCase_Factory implements Factory<SignOutUseCase> {
  private final Provider<AuthRepository> authRepositoryProvider;

  public SignOutUseCase_Factory(Provider<AuthRepository> authRepositoryProvider) {
    this.authRepositoryProvider = authRepositoryProvider;
  }

  @Override
  public SignOutUseCase get() {
    return newInstance(authRepositoryProvider.get());
  }

  public static SignOutUseCase_Factory create(Provider<AuthRepository> authRepositoryProvider) {
    return new SignOutUseCase_Factory(authRepositoryProvider);
  }

  public static SignOutUseCase newInstance(AuthRepository authRepository) {
    return new SignOutUseCase(authRepository);
  }
}
