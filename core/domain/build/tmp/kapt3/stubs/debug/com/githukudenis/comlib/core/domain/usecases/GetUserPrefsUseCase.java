package com.githukudenis.comlib.core.domain.usecases;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0086\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\b"}, d2 = {"Lcom/githukudenis/comlib/core/domain/usecases/GetUserPrefsUseCase;", "", "userPrefsRepository", "Lcom/githukudenis/comlib/data/repository/UserPrefsRepository;", "(Lcom/githukudenis/comlib/data/repository/UserPrefsRepository;)V", "invoke", "Lkotlinx/coroutines/flow/Flow;", "Lcom/githukudenis/comlib/core/model/UserPrefs;", "domain_debug"})
public final class GetUserPrefsUseCase {
    @org.jetbrains.annotations.NotNull
    private final com.githukudenis.comlib.data.repository.UserPrefsRepository userPrefsRepository = null;
    
    @javax.inject.Inject
    public GetUserPrefsUseCase(@org.jetbrains.annotations.NotNull
    com.githukudenis.comlib.data.repository.UserPrefsRepository userPrefsRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<com.githukudenis.comlib.core.model.UserPrefs> invoke() {
        return null;
    }
}