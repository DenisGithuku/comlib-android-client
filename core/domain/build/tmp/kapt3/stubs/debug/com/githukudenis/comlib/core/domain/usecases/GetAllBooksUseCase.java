package com.githukudenis.comlib.core.domain.usecases;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u001b\u0010\u0005\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b0\u00070\u0006H\u0086\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\n"}, d2 = {"Lcom/githukudenis/comlib/core/domain/usecases/GetAllBooksUseCase;", "", "booksRepository", "Lcom/githukudenis/comlib/data/repository/BooksRepository;", "(Lcom/githukudenis/comlib/data/repository/BooksRepository;)V", "invoke", "Lkotlinx/coroutines/flow/Flow;", "Lcom/githukudenis/comlib/core/common/DataResult;", "", "Lcom/githukudenis/comlib/core/model/book/Book;", "domain_debug"})
public final class GetAllBooksUseCase {
    @org.jetbrains.annotations.NotNull
    private final com.githukudenis.comlib.data.repository.BooksRepository booksRepository = null;
    
    @javax.inject.Inject
    public GetAllBooksUseCase(@org.jetbrains.annotations.NotNull
    com.githukudenis.comlib.data.repository.BooksRepository booksRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<com.githukudenis.comlib.core.common.DataResult<java.util.List<com.githukudenis.comlib.core.model.book.Book>>> invoke() {
        return null;
    }
}