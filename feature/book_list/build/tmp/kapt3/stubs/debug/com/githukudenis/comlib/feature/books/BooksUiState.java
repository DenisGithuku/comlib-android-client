package com.githukudenis.comlib.feature.books;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0003\u0003\u0004\u0005B\u0007\b\u0004\u00a2\u0006\u0002\u0010\u0002\u0082\u0001\u0003\u0006\u0007\b\u00a8\u0006\t"}, d2 = {"Lcom/githukudenis/comlib/feature/books/BooksUiState;", "", "()V", "Error", "Loading", "Success", "Lcom/githukudenis/comlib/feature/books/BooksUiState$Error;", "Lcom/githukudenis/comlib/feature/books/BooksUiState$Loading;", "Lcom/githukudenis/comlib/feature/books/BooksUiState$Success;", "book_list_debug"})
public abstract class BooksUiState {
    
    private BooksUiState() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003H\u00c6\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u00d6\u0003J\t\u0010\r\u001a\u00020\u000eH\u00d6\u0001J\t\u0010\u000f\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0010"}, d2 = {"Lcom/githukudenis/comlib/feature/books/BooksUiState$Error;", "Lcom/githukudenis/comlib/feature/books/BooksUiState;", "message", "", "(Ljava/lang/String;)V", "getMessage", "()Ljava/lang/String;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "book_list_debug"})
    public static final class Error extends com.githukudenis.comlib.feature.books.BooksUiState {
        @org.jetbrains.annotations.NotNull
        private final java.lang.String message = null;
        
        public Error(@org.jetbrains.annotations.NotNull
        java.lang.String message) {
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getMessage() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.githukudenis.comlib.feature.books.BooksUiState.Error copy(@org.jetbrains.annotations.NotNull
        java.lang.String message) {
            return null;
        }
        
        @java.lang.Override
        public boolean equals(@org.jetbrains.annotations.Nullable
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override
        public int hashCode() {
            return 0;
        }
        
        @java.lang.Override
        @org.jetbrains.annotations.NotNull
        public java.lang.String toString() {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u00c6\n\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0013\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u00d6\u0003J\t\u0010\u0007\u001a\u00020\bH\u00d6\u0001J\t\u0010\t\u001a\u00020\nH\u00d6\u0001\u00a8\u0006\u000b"}, d2 = {"Lcom/githukudenis/comlib/feature/books/BooksUiState$Loading;", "Lcom/githukudenis/comlib/feature/books/BooksUiState;", "()V", "equals", "", "other", "", "hashCode", "", "toString", "", "book_list_debug"})
    public static final class Loading extends com.githukudenis.comlib.feature.books.BooksUiState {
        @org.jetbrains.annotations.NotNull
        public static final com.githukudenis.comlib.feature.books.BooksUiState.Loading INSTANCE = null;
        
        private Loading() {
        }
        
        @java.lang.Override
        public boolean equals(@org.jetbrains.annotations.Nullable
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override
        public int hashCode() {
            return 0;
        }
        
        @java.lang.Override
        @org.jetbrains.annotations.NotNull
        public java.lang.String toString() {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\t\u0010\u000f\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0010\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u0011\u001a\u00020\u0007H\u00c6\u0003J\'\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007H\u00c6\u0001J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u00d6\u0003J\t\u0010\u0017\u001a\u00020\u0018H\u00d6\u0001J\t\u0010\u0019\u001a\u00020\u001aH\u00d6\u0001R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e\u00a8\u0006\u001b"}, d2 = {"Lcom/githukudenis/comlib/feature/books/BooksUiState$Success;", "Lcom/githukudenis/comlib/feature/books/BooksUiState;", "selectedGenre", "Lcom/githukudenis/comlib/feature/books/GenreUiModel;", "bookListUiState", "Lcom/githukudenis/comlib/feature/books/BookListUiState;", "genreListUiState", "Lcom/githukudenis/comlib/feature/books/GenreListUiState;", "(Lcom/githukudenis/comlib/feature/books/GenreUiModel;Lcom/githukudenis/comlib/feature/books/BookListUiState;Lcom/githukudenis/comlib/feature/books/GenreListUiState;)V", "getBookListUiState", "()Lcom/githukudenis/comlib/feature/books/BookListUiState;", "getGenreListUiState", "()Lcom/githukudenis/comlib/feature/books/GenreListUiState;", "getSelectedGenre", "()Lcom/githukudenis/comlib/feature/books/GenreUiModel;", "component1", "component2", "component3", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "book_list_debug"})
    public static final class Success extends com.githukudenis.comlib.feature.books.BooksUiState {
        @org.jetbrains.annotations.NotNull
        private final com.githukudenis.comlib.feature.books.GenreUiModel selectedGenre = null;
        @org.jetbrains.annotations.NotNull
        private final com.githukudenis.comlib.feature.books.BookListUiState bookListUiState = null;
        @org.jetbrains.annotations.NotNull
        private final com.githukudenis.comlib.feature.books.GenreListUiState genreListUiState = null;
        
        public Success(@org.jetbrains.annotations.NotNull
        com.githukudenis.comlib.feature.books.GenreUiModel selectedGenre, @org.jetbrains.annotations.NotNull
        com.githukudenis.comlib.feature.books.BookListUiState bookListUiState, @org.jetbrains.annotations.NotNull
        com.githukudenis.comlib.feature.books.GenreListUiState genreListUiState) {
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.githukudenis.comlib.feature.books.GenreUiModel getSelectedGenre() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.githukudenis.comlib.feature.books.BookListUiState getBookListUiState() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.githukudenis.comlib.feature.books.GenreListUiState getGenreListUiState() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.githukudenis.comlib.feature.books.GenreUiModel component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.githukudenis.comlib.feature.books.BookListUiState component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.githukudenis.comlib.feature.books.GenreListUiState component3() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.githukudenis.comlib.feature.books.BooksUiState.Success copy(@org.jetbrains.annotations.NotNull
        com.githukudenis.comlib.feature.books.GenreUiModel selectedGenre, @org.jetbrains.annotations.NotNull
        com.githukudenis.comlib.feature.books.BookListUiState bookListUiState, @org.jetbrains.annotations.NotNull
        com.githukudenis.comlib.feature.books.GenreListUiState genreListUiState) {
            return null;
        }
        
        @java.lang.Override
        public boolean equals(@org.jetbrains.annotations.Nullable
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override
        public int hashCode() {
            return 0;
        }
        
        @java.lang.Override
        @org.jetbrains.annotations.NotNull
        public java.lang.String toString() {
            return null;
        }
    }
}