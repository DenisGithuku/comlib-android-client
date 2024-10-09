package com.githukudenis.comlib.core.domain.usecases;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J%\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u00062\u0006\u0010\t\u001a\u00020\nH\u0086B\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\f"}, d2 = {"Lcom/githukudenis/comlib/core/domain/usecases/GetBooksByUserUseCase;", "", "booksRepository", "Lcom/githukudenis/comlib/data/repository/BooksRepository;", "(Lcom/githukudenis/comlib/data/repository/BooksRepository;)V", "invoke", "Lcom/githukudenis/comlib/core/common/DataResult;", "", "Lcom/githukudenis/comlib/core/model/book/Book;", "userId", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "domain_debug"})
public final class GetBooksByUserUseCase {
    @org.jetbrains.annotations.NotNull
    private final com.githukudenis.comlib.data.repository.BooksRepository booksRepository = null;
    
    @javax.inject.Inject
    public GetBooksByUserUseCase(@org.jetbrains.annotations.NotNull
    com.githukudenis.comlib.data.repository.BooksRepository booksRepository) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object invoke(@org.jetbrains.annotations.NotNull
    java.lang.String userId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.githukudenis.comlib.core.common.DataResult<? extends java.util.List<com.githukudenis.comlib.core.model.book.Book>>> $completion) {
        return null;
    }
}