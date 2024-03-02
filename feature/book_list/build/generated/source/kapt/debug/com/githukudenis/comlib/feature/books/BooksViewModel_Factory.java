package com.githukudenis.comlib.feature.books;

import com.githukudenis.comlib.core.domain.usecases.ComlibUseCases;
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
public final class BooksViewModel_Factory implements Factory<BooksViewModel> {
  private final Provider<ComlibUseCases> comlibUseCasesProvider;

  public BooksViewModel_Factory(Provider<ComlibUseCases> comlibUseCasesProvider) {
    this.comlibUseCasesProvider = comlibUseCasesProvider;
  }

  @Override
  public BooksViewModel get() {
    return newInstance(comlibUseCasesProvider.get());
  }

  public static BooksViewModel_Factory create(Provider<ComlibUseCases> comlibUseCasesProvider) {
    return new BooksViewModel_Factory(comlibUseCasesProvider);
  }

  public static BooksViewModel newInstance(ComlibUseCases comlibUseCases) {
    return new BooksViewModel(comlibUseCases);
  }
}
