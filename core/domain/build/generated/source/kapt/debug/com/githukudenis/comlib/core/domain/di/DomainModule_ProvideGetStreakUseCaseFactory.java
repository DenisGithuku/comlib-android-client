package com.githukudenis.comlib.core.domain.di;

import com.githukudenis.comlib.core.domain.usecases.GetStreakUseCase;
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
public final class DomainModule_ProvideGetStreakUseCaseFactory implements Factory<GetStreakUseCase> {
  private final Provider<BookMilestoneRepository> bookMilestoneRepositoryProvider;

  public DomainModule_ProvideGetStreakUseCaseFactory(
      Provider<BookMilestoneRepository> bookMilestoneRepositoryProvider) {
    this.bookMilestoneRepositoryProvider = bookMilestoneRepositoryProvider;
  }

  @Override
  public GetStreakUseCase get() {
    return provideGetStreakUseCase(bookMilestoneRepositoryProvider.get());
  }

  public static DomainModule_ProvideGetStreakUseCaseFactory create(
      Provider<BookMilestoneRepository> bookMilestoneRepositoryProvider) {
    return new DomainModule_ProvideGetStreakUseCaseFactory(bookMilestoneRepositoryProvider);
  }

  public static GetStreakUseCase provideGetStreakUseCase(
      BookMilestoneRepository bookMilestoneRepository) {
    return Preconditions.checkNotNullFromProvides(DomainModule.INSTANCE.provideGetStreakUseCase(bookMilestoneRepository));
  }
}
