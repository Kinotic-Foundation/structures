package org.kinotic.structures.internal.api.services.sql;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.NotImplementedException;
import org.kinotic.continuum.idl.api.schema.FunctionDefinition;
import org.kinotic.structures.api.config.StructuresProperties;
import org.kinotic.structures.api.domain.NamedQueriesDefinition;
import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.api.domain.idl.decorators.QueryDecorator;
import org.kinotic.structures.internal.api.services.sql.executors.AggregateQueryExecutor;
import org.kinotic.structures.internal.api.services.sql.executors.QueryExecutor;
import org.kinotic.structures.internal.utils.QueryUtils;
import org.kinotic.structures.internal.utils.SqlQueryType;
import org.springframework.stereotype.Component;

/**
 * Created by Navíd Mitchell 🤪 on 4/28/24.
 */
@Component
@RequiredArgsConstructor
public class DefaultQueryExecutorFactory implements QueryExecutorFactory {

    private final ElasticVertxClient elasticVertxClient;
    private final StructuresProperties structuresProperties;

    public QueryExecutor createQueryExecutor(Structure structure,
                                             String queryName,
                                             NamedQueriesDefinition namedQueriesDefinition){
        FunctionDefinition namedQuery = null;
        for(FunctionDefinition queries : namedQueriesDefinition.getNamedQueries()){
            if(queries.getName().equals(queryName)){
                namedQuery = queries;
                break;
            }
        }
        if(namedQuery == null){
            throw new IllegalArgumentException("No query found with name " + queryName);
        }

        // Sanity check, but should never happen if using the CLI
        QueryDecorator queryDecorator = namedQuery.findDecorator(QueryDecorator.class);
        if(queryDecorator == null
                || queryDecorator.getStatements() == null){
            throw new IllegalArgumentException("No Query defined");
        }

        String[] statements = queryDecorator.getStatements().split(";");
        if(statements.length == 1){
            return createQueryExecutorForStatement(structure, statements[0], namedQuery);
        }else{
            throw new IllegalArgumentException("Multiple statements not supported yet");
        }
    }

    private QueryExecutor createQueryExecutorForStatement(Structure structure,
                                                          String statement,
                                                          FunctionDefinition namedQueryDefinition) {
        // naive approach to how we handle these queries, this will be improved as we do more R&D on advanced approaches
        SqlQueryType queryType = QueryUtils.determineQueryType(statement);
        switch (queryType) {
            case AGGREGATE:
                return new AggregateQueryExecutor(structure,
                                                  elasticVertxClient,
                                                  namedQueryDefinition,
                                                  statement,
                                                  structuresProperties);
            case DELETE:
                throw new NotImplementedException("Delete not supported yet");
            case INSERT:
                throw new NotImplementedException("Insert not supported yet");
            case SELECT:
                throw new NotImplementedException("Select without aggregate not supported yet");
            case UPDATE:
                throw new NotImplementedException("Update not supported yet");
            default:
                throw new IllegalArgumentException("Unsupported query type " + queryType);
        }
    }
}
