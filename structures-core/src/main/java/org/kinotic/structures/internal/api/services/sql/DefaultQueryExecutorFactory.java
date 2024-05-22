package org.kinotic.structures.internal.api.services.sql;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.NotImplementedException;
import org.kinotic.continuum.idl.api.schema.FunctionDefinition;
import org.kinotic.structures.api.config.StructuresProperties;
import org.kinotic.structures.api.domain.NamedQueriesDefinition;
import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.api.idl.decorators.QueryDecorator;
import org.kinotic.structures.internal.api.services.sql.executors.AggregateQueryExecutor;
import org.kinotic.structures.internal.api.services.sql.executors.QueryExecutor;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

/**
 * Created by Navíd Mitchell 🤪 on 4/28/24.
 */
@Component
@RequiredArgsConstructor
public class DefaultQueryExecutorFactory implements QueryExecutorFactory {

    private static final Pattern aggregatePattern = Pattern.compile("\\b(AVG|COUNT|FIRST|LAST|MAX|MIN|SUM|KURTOSIS|MAD|PERCENTILE|PERCENTILE_RANK|SKEWNESS|STDDEV_POP|STDDEV_SAMP|SUM_OF_SQUARES|VAR_POP|VAR_SAMP)\\s*\\([a-zA-Z0-9_., ]+\\)");
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
                                                          FunctionDefinition namedQueryDefinition){
        // naive approach to how we handle these queries, this will be improved as we do more R&D on advanced approaches
        if(statement.toLowerCase().startsWith("select")) {

            // Check if this is an aggregate query
            if(aggregatePattern.matcher(statement.toUpperCase()).find()){
                return new AggregateQueryExecutor(structure,
                                                  elasticVertxClient,
                                                  namedQueryDefinition,
                                                  statement,
                                                  structuresProperties);
            }else {
                throw new NotImplementedException("Select without aggregate not supported yet");
            }

        }else if(statement.toLowerCase().startsWith("update")) {
            throw new NotImplementedException("Update not supported yet");
        }else if(statement.toLowerCase().startsWith("delete")) {
            throw new NotImplementedException("Delete not supported yet");
        }else if(statement.toLowerCase().startsWith("insert")) {
            throw new NotImplementedException("Insert not supported yet");
        }else {
            throw new IllegalArgumentException("Unsupported statement " + statement);
        }
    }
}
