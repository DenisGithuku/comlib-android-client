package com.githukudenis.comlib.core.domain.di;

import com.githukudenis.comlib.core.domain.usecases.GetUserProfileUseCase;
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
public final class DomainModule_ProvidesGetUserProfileUseCaseFactory implements Factory<GetUserProfileUseCase> {
  private final Provider<UserRepository> userRepositoryProvider;

  public DomainModule_ProvidesGetUserProfileUseCaseFactory(
      Provider<UserRepository> userRepositoryProvider) {
    this.userRepositoryProvider = userRepositoryProvider;
  }

  @Override
  public GetUserProfileUseCase get() {
    return providesGetUserProfileUseCase(userRepositoryProvider.get());
  }

  public static DomainModule_ProvidesGetUserProfileUseCaseFactory create(
      Provider<UserRepository> userRepositoryProvider) {
    return new DomainModule_ProvidesGetUserProfileUseCaseFactory(userRepositoryProvider);
  }

  public static GetUserProfileUseCase providesGetUserProfileUseCase(UserRepository userRepository) {
    return Preconditions.checkNotNullFromProvides(DomainModule.INSTANCE.providesGetUserProfileUseCase(userRepository));
  }
}
