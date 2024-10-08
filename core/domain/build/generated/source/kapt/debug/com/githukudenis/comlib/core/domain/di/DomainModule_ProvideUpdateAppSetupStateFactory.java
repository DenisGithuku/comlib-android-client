package com.githukudenis.comlib.core.domain.di;

import com.githukudenis.comlib.core.domain.usecases.UpdateAppSetupState;
import com.githukudenis.comlib.data.repository.UserPrefsRepository;
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
public final class DomainModule_ProvideUpdateAppSetupStateFactory implements Factory<UpdateAppSetupState> {
  private final Provider<UserPrefsRepository> userPrefsRepositoryProvider;

  public DomainModule_ProvideUpdateAppSetupStateFactory(
      Provider<UserPrefsRepository> userPrefsRepositoryProvider) {
    this.userPrefsRepositoryProvider = userPrefsRepositoryProvider;
  }

  @Override
  public UpdateAppSetupState get() {
    return provideUpdateAppSetupState(userPrefsRepositoryProvider.get());
  }

  public static DomainModule_ProvideUpdateAppSetupStateFactory create(
      Provider<UserPrefsRepository> userPrefsRepositoryProvider) {
    return new DomainModule_ProvideUpdateAppSetupStateFactory(userPrefsRepositoryProvider);
  }

  public static UpdateAppSetupState provideUpdateAppSetupState(
      UserPrefsRepository userPrefsRepository) {
    return Preconditions.checkNotNullFromProvides(DomainModule.INSTANCE.provideUpdateAppSetupState(userPrefsRepository));
  }
}
