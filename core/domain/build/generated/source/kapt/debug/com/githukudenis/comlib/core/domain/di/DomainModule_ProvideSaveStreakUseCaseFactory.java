package com.githukudenis.comlib.core.domain.di;

import com.githukudenis.comlib.core.domain.usecases.SaveStreakUseCase;
import com.githukudenis.comlib.data.repository.BookMilestoneRepository;
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
public final class DomainModule_ProvideSaveStreakUseCaseFactory implements Factory<SaveStreakUseCase> {
  private final Provider<BookMilestoneRepository> bookMilestoneRepositoryProvider;

  public DomainModule_ProvideSaveStreakUseCaseFactory(
      Provider<BookMilestoneRepository> bookMilestoneRepositoryProvider) {
    this.bookMilestoneRepositoryProvider = bookMilestoneRepositoryProvider;
  }

  @Override
  public SaveStreakUseCase get() {
    return provideSaveStreakUseCase(bookMilestoneRepositoryProvider.get());
  }

  public static DomainModule_ProvideSaveStreakUseCaseFactory create(
      Provider<BookMilestoneRepository> bookMilestoneRepositoryProvider) {
    return new DomainModule_ProvideSaveStreakUseCaseFactory(bookMilestoneRepositoryProvider);
  }

  public static SaveStreakUseCase provideSaveStreakUseCase(
      BookMilestoneRepository bookMilestoneRepository) {
    return Preconditions.checkNotNullFromProvides(DomainModule.INSTANCE.provideSaveStreakUseCase(bookMilestoneRepository));
  }
}
