package com.githukudenis.comlib.core.domain.di;

import com.githukudenis.comlib.core.domain.usecases.GetGenresUseCase;
import com.githukudenis.comlib.data.repository.GenresRepository;
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
public final class DomainModule_ProvideGetGenresUseCaseFactory implements Factory<GetGenresUseCase> {
  private final Provider<GenresRepository> genresRepositoryProvider;

  public DomainModule_ProvideGetGenresUseCaseFactory(
      Provider<GenresRepository> genresRepositoryProvider) {
    this.genresRepositoryProvider = genresRepositoryProvider;
  }

  @Override
  public GetGenresUseCase get() {
    return provideGetGenresUseCase(genresRepositoryProvider.get());
  }

  public static DomainModule_ProvideGetGenresUseCaseFactory create(
      Provider<GenresRepository> genresRepositoryProvider) {
    return new DomainModule_ProvideGetGenresUseCaseFactory(genresRepositoryProvider);
  }

  public static GetGenresUseCase provideGetGenresUseCase(GenresRepository genresRepository) {
    return Preconditions.checkNotNullFromProvides(DomainModule.INSTANCE.provideGetGenresUseCase(genresRepository));
  }
}
