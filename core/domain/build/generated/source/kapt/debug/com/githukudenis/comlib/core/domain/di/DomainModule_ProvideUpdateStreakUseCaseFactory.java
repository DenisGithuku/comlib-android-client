package com.githukudenis.comlib.core.domain.di;

import com.githukudenis.comlib.core.domain.usecases.UpdateStreakUseCase;
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
public final class DomainModule_ProvideUpdateStreakUseCaseFactory implements Factory<UpdateStreakUseCase> {
  private final Provider<BookMilestoneRepository> bookMilestoneRepositoryProvider;

  public DomainModule_ProvideUpdateStreakUseCaseFactory(
      Provider<BookMilestoneRepository> bookMilestoneRepositoryProvider) {
    this.bookMilestoneRepositoryProvider = bookMilestoneRepositoryProvider;
  }

  @Override
  public UpdateStreakUseCase get() {
    return provideUpdateStreakUseCase(bookMilestoneRepositoryProvider.get());
  }

  public static DomainModule_ProvideUpdateStreakUseCaseFactory create(
      Provider<BookMilestoneRepository> bookMilestoneRepositoryProvider) {
    return new DomainModule_ProvideUpdateStreakUseCaseFactory(bookMilestoneRepositoryProvider);
  }

  public static UpdateStreakUseCase provideUpdateStreakUseCase(
      BookMilestoneRepository bookMilestoneRepository) {
    return Preconditions.checkNotNullFromProvides(DomainModule.INSTANCE.provideUpdateStreakUseCase(bookMilestoneRepository));
  }
}
