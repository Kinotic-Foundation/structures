package org.kinotic.structures.internal.api.services.sql;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.NotImplementedException;
import org.kinotic.continuum.idl.api.schema.FunctionDefinition;
import org.kinotic.structures.api.domain.NamedQueriesDefinition;
import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.api.idl.decorators.QueryDecorator;
import org.kinotic.structures.api.services.NamedQueriesService;
import org.kinotic.structures.internal.api.services.sql.executors.AggregateQueryExecutor;
import org.kinotic.structures.internal.api.services.sql.executors.QueryExecutor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Pattern;

/**
 * Created by Navíd Mitchell 🤪 on 4/28/24.
 */
@RequiredArgsConstructor
@Component
public class DefaultQueryExecutorFactory implements QueryExecutorFactory {

    private static Pattern aggregatePattern = Pattern.compile("\\b(AVG|COUNT|FIRST|LAST|MAX|MIN|SUM|KURTOSIS|MAD|PERCENTILE|PERCENTILE_RANK|SKEWNESS|STDDEV_POP|STDDEV_SAMP|SUM_OF_SQUARES|VAR_POP|VAR_SAMP)\\s*\\([a-zA-Z0-9_, ]+\\)");
    private final ElasticVertxClient elasticVertxClient;
    private final NamedQueriesService namedQueriesService;
//    private final ElasticsearchAsyncClient esAsyncClient;

    @Override
    public CompletableFuture<QueryExecutor> createQueryExecutor(Structure structure,
                                                                String queryName){
        return namedQueriesService.findByNamespaceAndStructure(structure.getNamespace(),
                                                               structure.getName())
                                  .thenCompose(nqd -> createQueryExecutor(structure, queryName, nqd));
    }

    private CompletableFuture<QueryExecutor> createQueryExecutor(Structure structure,
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
            return CompletableFuture.failedFuture(new IllegalArgumentException("No query found with name " + queryName));
        }

        // Sanity check, but should never happen if using the CLI
        QueryDecorator queryDecorator = namedQuery.findDecorator(QueryDecorator.class);
        if(queryDecorator == null
                || queryDecorator.getStatements() == null){
            return CompletableFuture.failedFuture(new IllegalArgumentException("No Query defined"));
        }

        String[] statements = queryDecorator.getStatements().split(";");
        if(statements.length == 1){
            return createQueryExecutorForStatement(structure, statements[0], namedQuery);
        }else{
            return CompletableFuture.failedFuture(new IllegalArgumentException("Multiple statements not supported yet"));
        }
    }

    private CompletableFuture<QueryExecutor> createQueryExecutorForStatement(Structure structure,
                                                                             String statement,
                                                                             FunctionDefinition namedQueryDefinition){
        // naive approach to how we handle these queries, this will be improved as we do more R&D on advanced approaches
        if(statement.toLowerCase().startsWith("select")) {

            // Add dummy values for arguments since this is required by elastic
            List<String> arguments = null;
            if(namedQueryDefinition.getParameters() != null){
                arguments = new ArrayList<>(namedQueryDefinition.getParameters().size());
                for(int i = 0; i < namedQueryDefinition.getParameters().size(); i++){
                    arguments.add("test");
                }
            }
            // Check if this is an aggregate query
            if(aggregatePattern.matcher(statement.toUpperCase()).find()){
                return CompletableFuture.completedFuture(new AggregateQueryExecutor(structure,
                                                                                   elasticVertxClient,
                                                                                   namedQueryDefinition,
                                                                                   statement));
            }else {
                throw (new NotImplementedException("Select without aggregate not supported yet"));
            }

        }else if(statement.toLowerCase().startsWith("update")) {
            return CompletableFuture.failedFuture(new NotImplementedException("Update not supported yet"));
        }else if(statement.toLowerCase().startsWith("delete")) {
            return CompletableFuture.failedFuture(new NotImplementedException("Delete not supported yet"));
        }else if(statement.toLowerCase().startsWith("insert")) {
            return CompletableFuture.failedFuture(new NotImplementedException("Insert not supported yet"));
        }else {
            return CompletableFuture.failedFuture(new IllegalArgumentException("Unsupported statement " + statement));
        }
    }
}
