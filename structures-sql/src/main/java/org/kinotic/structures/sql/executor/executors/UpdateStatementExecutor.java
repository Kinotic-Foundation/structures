package org.kinotic.structures.sql.executor.executors;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.elasticsearch.core.UpdateByQueryResponse;
import co.elastic.clients.json.JsonData;
import lombok.RequiredArgsConstructor;
import org.kinotic.structures.sql.domain.Expression;
import org.kinotic.structures.sql.domain.Statement;
import org.kinotic.structures.sql.domain.statements.UpdateStatement;
import org.kinotic.structures.sql.executor.QueryBuilder;
import org.kinotic.structures.sql.executor.StatementExecutor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Executes UPDATE statements against Elasticsearch.
 * Applies SET assignments and evaluates WHERE clauses with comparison operators.
 * Created by Navíd Mitchell 🤝 Grok on 3/31/25.
 */
@Component
@RequiredArgsConstructor
public class UpdateStatementExecutor implements StatementExecutor<UpdateStatement, Long> {
    private final ElasticsearchAsyncClient client;

    @Override
    public boolean supports(Statement statement) {
        return statement instanceof UpdateStatement;
    }

    @Override
    public void executeMigration(UpdateStatement statement) {
        try {
            executeQuery(statement, null).get();
        } catch (Exception e) {
            throw new RuntimeException("Failed to execute migration UPDATE", e);
        }
    }

    @Override
    public CompletableFuture<Long> executeQuery(UpdateStatement statement, Map<String, Object> parameters) {
        String script = buildScript(statement.assignments(), parameters);
        Map<String, Object> params = buildScriptParams(statement.assignments(), parameters);
        Map<String, JsonData> scriptParams = convertToJsonDataMap(params);

        return client.updateByQuery(u -> u
                .index(statement.tableName())
                .query(QueryBuilder.buildQuery(statement.whereClause(), parameters))
                .script(s -> s.source(script).params(scriptParams))
        ).thenApply(UpdateByQueryResponse::updated);
    }

    private String buildScript(Map<String, Expression> assignments, Map<String, Object> parameters) {
        StringBuilder script = new StringBuilder();
        assignments.forEach((field, expr) -> {
            if (expr instanceof Expression.Literal literal) {
                if ("?".equals(literal.getValue())) {
                    if (parameters == null) {
                        throw new IllegalStateException("Parameterized assignment not supported without parameters");
                    }
                    script.append("ctx._source.").append(field).append(" = params.").append(field).append(";");
                } else {
                    script.append("ctx._source.").append(field).append(" = params.").append(field).append(";");
                }
            } else if (expr instanceof Expression.BinaryExpression binExpr) {
                String operator = switch (binExpr.getOperator()) {
                    case "+" -> "+";
                    case "-" -> "-";
                    case "*" -> "*";
                    case "/" -> "/";
                    case "==" -> "=="; // Not typically used in SET, but included
                    default -> throw new IllegalStateException("Unsupported operator: " + binExpr.getOperator());
                };
                String right = "?".equals(binExpr.getRight()) ? "params." + field : binExpr.getRight();
                script.append("ctx._source.").append(field).append(" = ctx._source.").append(binExpr.getLeft())
                      .append(" ").append(operator).append(" ").append(right).append(";");
            }
        });
        return script.toString();
    }

    private Map<String, Object> buildScriptParams(Map<String, Expression> assignments, Map<String, Object> parameters) {
        Map<String, Object> params = new HashMap<>();
        assignments.forEach((field, expr) -> {
            if (expr instanceof Expression.Literal literal) {
                if ("?".equals(literal.getValue())) {
                    if (parameters == null) {
                        throw new IllegalStateException("Parameterized assignment not supported without parameters");
                    }
                    Object paramValue = parameters.get(field);
                    if (paramValue == null) {
                        throw new IllegalArgumentException("Missing parameter for " + field);
                    }
                    params.put(field, paramValue);
                } else {
                    params.put(field, QueryBuilder.parseValue(literal.getValue()));
                }
            } else if (expr instanceof Expression.BinaryExpression binExpr) {
                if ("?".equals(binExpr.getRight())) {
                    if (parameters == null) {
                        throw new IllegalStateException("Parameterized expression not supported without parameters");
                    }
                    Object paramValue = parameters.get(field);
                    if (paramValue == null) {
                        throw new IllegalArgumentException("Missing parameter for " + field);
                    }
                    params.put(field, paramValue);
                } else if (!binExpr.getRight().matches("[a-zA-Z_][a-zA-Z_0-9]*")) { // Not a field reference
                    params.put(field, QueryBuilder.parseValue(binExpr.getRight()));
                }
            }
        });
        return params;
    }

    private Map<String, JsonData> convertToJsonDataMap(Map<String, Object> params) {
        Map<String, JsonData> jsonDataParams = new HashMap<>();
        if (params != null) {
            params.forEach((key, value) -> jsonDataParams.put(key, JsonData.of(value)));
        }
        return jsonDataParams;
    }
}