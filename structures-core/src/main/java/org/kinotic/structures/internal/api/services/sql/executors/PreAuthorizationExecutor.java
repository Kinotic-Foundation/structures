package org.kinotic.structures.internal.api.services.sql.executors;

import lombok.RequiredArgsConstructor;
import org.kinotic.continuum.core.api.crud.Page;
import org.kinotic.continuum.core.api.crud.Pageable;
import org.kinotic.structures.api.domain.EntityContext;
import org.kinotic.structures.api.domain.NamedQueryOperation;
import org.kinotic.structures.api.services.security.AuthorizationService;
import org.kinotic.structures.internal.api.services.sql.ParameterHolder;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Created By navidmitchell on 12/30/24
 */
@RequiredArgsConstructor
public class PreAuthorizationExecutor implements QueryExecutor{

    private final AuthorizationService<NamedQueryOperation> authorizationService;
    private final QueryExecutor delegate;

    @Override
    public <T> CompletableFuture<List<T>> execute(ParameterHolder parameterHolder, Class<T> type, EntityContext context) {
        return authorizationService.authorize(NamedQueryOperation.EXECUTE, context)
                                   .thenCompose(v -> delegate.execute(parameterHolder,
                                                                           type,
                                                                           context));
    }

    @Override
    public <T> CompletableFuture<Page<T>> executePage(ParameterHolder parameterHolder,
                                                      Pageable pageable,
                                                      Class<T> type,
                                                      EntityContext context) {
        return authorizationService.authorize(NamedQueryOperation.EXECUTE_PAGE, context)
                                   .thenCompose(v -> delegate.executePage(parameterHolder,
                                                                               pageable,
                                                                               type,
                                                                               context));
    }
}
