package com.githukudenis.comlib.core.domain.di;

import com.githukudenis.comlib.core.domain.usecases.UpdateUserUseCase;
import com.githukudenis.comlib.data.repository.UserRepository;
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
public final class DomainModule_UpdateUserUseCaseFactory implements Factory<UpdateUserUseCase> {
  private final Provider<UserRepository> userRepositoryProvider;

  public DomainModule_UpdateUserUseCaseFactory(Provider<UserRepository> userRepositoryProvider) {
    this.userRepositoryProvider = userRepositoryProvider;
  }

  @Override
  public UpdateUserUseCase get() {
    return updateUserUseCase(userRepositoryProvider.get());
  }

  public static DomainModule_UpdateUserUseCaseFactory create(
      Provider<UserRepository> userRepositoryProvider) {
    return new DomainModule_UpdateUserUseCaseFactory(userRepositoryProvider);
  }

  public static UpdateUserUseCase updateUserUseCase(UserRepository userRepository) {
    return Preconditions.checkNotNullFromProvides(DomainModule.INSTANCE.updateUserUseCase(userRepository));
  }
}
