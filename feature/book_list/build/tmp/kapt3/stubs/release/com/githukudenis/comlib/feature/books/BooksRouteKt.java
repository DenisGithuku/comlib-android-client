package com.githukudenis.comlib.feature.books;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000B\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a4\u0010\u0000\u001a\u00020\u00012\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\bH\u0007\u001aF\u0010\t\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u000b2\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u00010\u00052\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\bH\u0007\u001a\b\u0010\u000e\u001a\u00020\u0001H\u0003\u001aH\u0010\u000f\u001a\u00020\u00012\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u00010\u00052\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00010\u0005H\u0003\u001a\b\u0010\u0016\u001a\u00020\u0001H\u0003\u00a8\u0006\u0017"}, d2 = {"BooksRoute", "", "viewModel", "Lcom/githukudenis/comlib/feature/books/BooksViewModel;", "onOpenBook", "Lkotlin/Function1;", "", "onNavigateUp", "Lkotlin/Function0;", "BooksScreen", "state", "Lcom/githukudenis/comlib/feature/books/BooksUiState;", "onChangeGenre", "Lcom/githukudenis/comlib/feature/books/GenreUiModel;", "ErrorScreen", "LoadedScreen", "paddingValues", "Landroidx/compose/foundation/layout/PaddingValues;", "genreListUiState", "Lcom/githukudenis/comlib/feature/books/GenreListUiState;", "bookListUiState", "Lcom/githukudenis/comlib/feature/books/BookListUiState;", "LoadingScreen", "books_release"})
public final class BooksRouteKt {
    
    @androidx.compose.runtime.Composable
    public static final void BooksRoute(@org.jetbrains.annotations.NotNull
    com.githukudenis.comlib.feature.books.BooksViewModel viewModel, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onOpenBook, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateUp) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    public static final void BooksScreen(@org.jetbrains.annotations.NotNull
    com.githukudenis.comlib.feature.books.BooksUiState state, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super com.githukudenis.comlib.feature.books.GenreUiModel, kotlin.Unit> onChangeGenre, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onOpenBook, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateUp) {
    }
    
    @androidx.compose.runtime.Composable
    private static final void LoadingScreen() {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    private static final void LoadedScreen(androidx.compose.foundation.layout.PaddingValues paddingValues, com.githukudenis.comlib.feature.books.GenreListUiState genreListUiState, com.githukudenis.comlib.feature.books.BookListUiState bookListUiState, kotlin.jvm.functions.Function1<? super com.githukudenis.comlib.feature.books.GenreUiModel, kotlin.Unit> onChangeGenre, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onOpenBook) {
    }
    
    @androidx.compose.runtime.Composable
    private static final void ErrorScreen() {
    }
}