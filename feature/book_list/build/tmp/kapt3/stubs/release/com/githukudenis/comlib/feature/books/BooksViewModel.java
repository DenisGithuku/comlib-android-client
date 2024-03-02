package com.githukudenis.comlib.feature.books;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0011\u0010\u0011\u001a\u00020\u0012H\u0082@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0013J\u0011\u0010\u0014\u001a\u00020\u0012H\u0082@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0013J\u000e\u0010\u0015\u001a\u00020\u00122\u0006\u0010\u0016\u001a\u00020\u000bR\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0017"}, d2 = {"Lcom/githukudenis/comlib/feature/books/BooksViewModel;", "Landroidx/lifecycle/ViewModel;", "comlibUseCases", "Lcom/githukudenis/comlib/core/domain/usecases/ComlibUseCases;", "(Lcom/githukudenis/comlib/core/domain/usecases/ComlibUseCases;)V", "bookListUiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/githukudenis/comlib/feature/books/BookListUiState;", "genreListUiState", "Lcom/githukudenis/comlib/feature/books/GenreListUiState;", "selectedGenre", "Lcom/githukudenis/comlib/feature/books/GenreUiModel;", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "Lcom/githukudenis/comlib/feature/books/BooksUiState;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "getBookList", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getGenreList", "onChangeGenre", "genreUiModel", "books_release"})
@dagger.hilt.android.lifecycle.HiltViewModel
public final class BooksViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull
    private final com.githukudenis.comlib.core.domain.usecases.ComlibUseCases comlibUseCases = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<com.githukudenis.comlib.feature.books.GenreListUiState> genreListUiState = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<com.githukudenis.comlib.feature.books.BookListUiState> bookListUiState = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<com.githukudenis.comlib.feature.books.GenreUiModel> selectedGenre = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<com.githukudenis.comlib.feature.books.BooksUiState> uiState = null;
    
    @javax.inject.Inject
    public BooksViewModel(@org.jetbrains.annotations.NotNull
    com.githukudenis.comlib.core.domain.usecases.ComlibUseCases comlibUseCases) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<com.githukudenis.comlib.feature.books.BooksUiState> getUiState() {
        return null;
    }
    
    private final java.lang.Object getGenreList(kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.Object getBookList(kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    public final void onChangeGenre(@org.jetbrains.annotations.NotNull
    com.githukudenis.comlib.feature.books.GenreUiModel genreUiModel) {
    }
}