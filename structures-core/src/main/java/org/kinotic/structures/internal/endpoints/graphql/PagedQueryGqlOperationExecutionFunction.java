package org.kinotic.structures.internal.endpoints.graphql;

import com.fasterxml.jackson.databind.ObjectMapper;
import graphql.ExecutionResult;
import org.kinotic.continuum.core.api.crud.Pageable;
import org.kinotic.continuum.idl.api.schema.FunctionDefinition;
import org.kinotic.continuum.idl.api.schema.ParameterDefinition;
import org.kinotic.structures.api.domain.RawJson;
import org.kinotic.structures.api.domain.idl.PageableC3Type;
import org.kinotic.structures.api.services.EntitiesService;
import org.kinotic.structures.internal.api.services.sql.MapParameterHolder;
import org.kinotic.structures.internal.utils.GqlUtils;

import java.util.concurrent.CompletableFuture;

/**
 * Created by Navíd Mitchell 🤪 on 6/13/24.
 */
public class PagedQueryGqlOperationExecutionFunction implements GqlOperationExecutionFunction {

    private static final Pageable CURSOR_PAGEABLE = Pageable.create(null, 25, null);

    private final EntitiesService entitiesService;
    private final ObjectMapper objectMapper;
    private final String pageableName;
    private final String queryName;
    private final String structureId;

    public PagedQueryGqlOperationExecutionFunction(EntitiesService entitiesService,
                                                   ObjectMapper objectMapper,
                                                   FunctionDefinition queryDefinition,
                                                   String structureId) {
        this.entitiesService = entitiesService;
        this.objectMapper = objectMapper;
        this.queryName = queryDefinition.getName();
        this.structureId = structureId;

        String pageParamName = null;
        for(ParameterDefinition parameter : queryDefinition.getParameters()){
            if(parameter.getType() instanceof PageableC3Type){
                pageParamName = parameter.getName();
                break;
            }
        }
        pageableName = pageParamName;
    }

    @Override
    public CompletableFuture<ExecutionResult> apply(GqlOperationArguments args) {

        ParsedFields fields = args.getParsedFields();
        Pageable pageable;
        if(pageableName != null) {
            pageable = GqlUtils.parseVariable(args.getVariables(),
                                              pageableName,
                                              Pageable.class,
                                              objectMapper);
        }else{
            pageable = CURSOR_PAGEABLE;
        }

        return entitiesService.namedQueryPage(structureId,
                                              queryName,
                                              new MapParameterHolder(args.getVariables()),
                                              pageable,
                                              RawJson.class,
                                              GqlUtils.createContext(args, fields.getContentFields()))
                              .thenApply(page -> GqlUtils.convertToResult(args.getOperationName(),
                                                                          page,
                                                                          args.getParsedFields()));
    }
}
