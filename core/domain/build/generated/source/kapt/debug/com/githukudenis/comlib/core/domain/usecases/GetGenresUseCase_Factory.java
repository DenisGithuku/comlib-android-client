package com.githukudenis.comlib.core.domain.usecases;

import com.githukudenis.comlib.data.repository.GenresRepository;
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
public final class GetGenresUseCase_Factory implements Factory<GetGenresUseCase> {
  private final Provider<GenresRepository> genresRepositoryProvider;

  public GetGenresUseCase_Factory(Provider<GenresRepository> genresRepositoryProvider) {
    this.genresRepositoryProvider = genresRepositoryProvider;
  }

  @Override
  public GetGenresUseCase get() {
    return newInstance(genresRepositoryProvider.get());
  }

  public static GetGenresUseCase_Factory create(
      Provider<GenresRepository> genresRepositoryProvider) {
    return new GetGenresUseCase_Factory(genresRepositoryProvider);
  }

  public static GetGenresUseCase newInstance(GenresRepository genresRepository) {
    return new GetGenresUseCase(genresRepository);
  }
}
