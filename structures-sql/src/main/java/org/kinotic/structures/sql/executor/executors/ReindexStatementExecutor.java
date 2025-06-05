package org.kinotic.structures.sql.executor.executors;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.kinotic.structures.sql.domain.Statement;
import org.kinotic.structures.sql.domain.statements.ReindexStatement;
import org.kinotic.structures.sql.executor.StatementExecutor;
import org.springframework.stereotype.Component;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.elasticsearch._types.Conflicts;
import co.elastic.clients.elasticsearch._types.Script;
import co.elastic.clients.elasticsearch._types.SlicesCalculation;
import lombok.RequiredArgsConstructor;

/**
 * Executes REINDEX statements against Elasticsearch.
 * Reindexes data between indices with customizable options using Lucene query syntax.
 * Migration-only operation.
 * Created by Navíd Mitchell 🤪🤝Grok on 3/31/25.
 */
@Component
@RequiredArgsConstructor
public class ReindexStatementExecutor implements StatementExecutor<ReindexStatement, String> {
    private final ElasticsearchAsyncClient client;

    @Override
    public boolean supports(Statement statement) {
        return statement instanceof ReindexStatement;
    }

    @Override
    public CompletableFuture<String> executeMigration(ReindexStatement statement) {
        // Always use wait_for_completion=false
        return client.reindex(r -> {
            r.source(s -> {
                s.index(statement.source());
                if (statement.query() != null) {
                    s.query(q -> q.queryString(qs -> qs.query(statement.query())));
                }
                if (statement.sourceFields() != null) {
                    s.sourceFields(Arrays.asList(statement.sourceFields().split(",")));
                }
                if (statement.size() != null) {
                    s.size(statement.size());
                }
                return s;
            })
            .dest(d -> d.index(statement.dest()))
            .conflicts(statement.conflicts() != null && "proceed".equals(statement.conflicts()) ? Conflicts.Proceed : Conflicts.Abort)
            .maxDocs(statement.maxDocs() != null ? statement.maxDocs().longValue() : null)
            .script(statement.script() != null ? Script.of(sc -> sc.source(statement.script())) : null)
            .slices(s -> {
                if (statement.slices() != null) {
                    if ("auto".equals(statement.slices())) {
                        s.computed(SlicesCalculation.Auto);
                    } else {
                        s.value(Integer.parseInt(statement.slices()));
                    }
                }
                return s;
            })
            .waitForCompletion(false);
            return r;
        }).thenCompose(response -> {
            String taskId = response.task();
            if (statement.waitForCompletion() != null && statement.waitForCompletion()) {
                // Poll for completion asynchronously
                return pollTaskUntilComplete(taskId).thenApply(done -> null);
            } else {
                return CompletableFuture.completedFuture(taskId);
            }
        });
    }

    private CompletableFuture<Boolean> pollTaskUntilComplete(String taskId) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        pollTaskRecursive(taskId, future, Instant.now(), Duration.ofHours(1));
        return future;
    }

    private void pollTaskRecursive(String taskId, CompletableFuture<Boolean> future, Instant start, Duration timeout) {
        client.tasks().get(g -> g.taskId(taskId)).whenComplete((taskResp, ex) -> {
            if (ex != null) {
                future.completeExceptionally(ex);
                return;
            }
            if (taskResp.completed()) {
                future.complete(true);
            } else if (Duration.between(start, Instant.now()).compareTo(timeout) > 0) {
                future.completeExceptionally(new RuntimeException("Timed out waiting for reindex task to complete"));
            } else {
                // Schedule next poll
                CompletableFuture.delayedExecutor(2, java.util.concurrent.TimeUnit.SECONDS)
                    .execute(() -> pollTaskRecursive(taskId, future, start, timeout));
            }
        });
    }

    @Override
    public CompletableFuture<String> executeQuery(ReindexStatement statement, Map<String, Object> parameters) {
        throw new UnsupportedOperationException("REINDEX is not supported as a named query");
    }
}