package org.kinotic.structures.internal.sql.executor.executors;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.elasticsearch._types.Conflicts;
import co.elastic.clients.elasticsearch._types.Script;
import co.elastic.clients.elasticsearch._types.SlicesCalculation;
import lombok.RequiredArgsConstructor;
import org.kinotic.structures.internal.sql.domain.Statement;
import org.kinotic.structures.internal.sql.domain.statements.ReindexStatement;
import org.kinotic.structures.internal.sql.executor.StatementExecutor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Executes REINDEX statements against Elasticsearch.
 * Reindexes data between indices with customizable options using Lucene query syntax.
 * Migration-only operation.
 * Created by Navíd Mitchell 🤪🤝Grok on 3/31/25.
 */
@Component
@RequiredArgsConstructor
public class ReindexStatementExecutor implements StatementExecutor<ReindexStatement, Void> {
    private final ElasticsearchAsyncClient client;

    @Override
    public boolean supports(Statement statement) {
        return statement instanceof ReindexStatement;
    }

    @Override
    public void executeMigration(ReindexStatement statement) {
        try {
            client.reindex(r -> {
                r.source(s -> {
                     s.index(statement.getSource());
                     if (statement.getQuery() != null) {
                         s.query(q -> q.queryString(qs -> qs.query(statement.getQuery())));
                     }
                     if (statement.getSourceFields() != null) {
                         s.sourceFields(Arrays.asList(statement.getSourceFields().split(",")));
                     }
                     if (statement.getSize() != null) {
                         s.size(statement.getSize());
                     }
                     return s;
                 })
                 .dest(d -> d.index(statement.getDest()))
                 .conflicts(statement.getConflicts() != null && "proceed".equals(statement.getConflicts()) ? Conflicts.Proceed : Conflicts.Abort)
                 .maxDocs(statement.getMaxDocs() != null ? statement.getMaxDocs().longValue() : null)
                 .script(statement.getScript() != null ? Script.of(sc -> sc.source(statement.getScript())) : null)
                 .slices(s -> {
                     if (statement.getSlices() != null) {
                         if ("auto".equals(statement.getSlices())) {
                             s.computed(SlicesCalculation.Auto);
                         } else {
                             s.value(Integer.parseInt(statement.getSlices()));
                         }
                     }
                     return s;
                 });
                return r;
            }).get();
        } catch (Exception e) {
            throw new RuntimeException("Failed to execute migration REINDEX", e);
        }
    }

    @Override
    public CompletableFuture<Void> executeQuery(ReindexStatement statement, Map<String, Object> parameters) {
        throw new UnsupportedOperationException("REINDEX is not supported as a named query");
    }
}