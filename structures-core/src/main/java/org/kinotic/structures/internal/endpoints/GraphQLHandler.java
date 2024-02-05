package org.kinotic.structures.internal.endpoints;

import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.impl.NoStackTraceThrowable;
import io.vertx.core.json.JsonObject;
import io.vertx.core.spi.json.JsonCodec;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.graphql.impl.GraphQLBatch;
import io.vertx.ext.web.handler.graphql.impl.GraphQLInput;
import io.vertx.ext.web.handler.graphql.impl.GraphQLQuery;
import org.kinotic.structures.internal.graphql.GqlOperationService;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static io.vertx.core.http.HttpMethod.GET;
import static io.vertx.core.http.HttpMethod.POST;
import static java.util.stream.Collectors.toList;

/**
 * Created by Navíd Mitchell 🤪 on 6/7/23.
 */
public class GraphQLHandler implements Handler<RoutingContext> {

    private final GqlOperationService gqlOperationService;

    public GraphQLHandler(GqlOperationService gqlOperationService) {
        this.gqlOperationService = gqlOperationService;
    }

    @Override
    public void handle(RoutingContext rc) {
        HttpMethod method = rc.request().method();
        if (method == GET) {
            handleGet(rc);
        } else if (method == POST) {
            if (rc.getBody() == null) {
                // the body handler was not set, so we cannot securely process POST bodies
                // we could just add an ad-hoc body handler but this can lead to DDoS attacks
                // and it doesn't really cover all the uploads, such as multipart, etc...
                // as well as resource cleanup
                rc.fail(500, new NoStackTraceThrowable("BodyHandler is required to process POST requests"));
            } else {
                handlePost(rc, rc.getBody());
            }
        } else {
            rc.fail(405);
        }
    }

    private void handleGet(RoutingContext rc) {
        String query = rc.queryParams().get("query");
        if (query == null) {
            failQueryMissing(rc);
            return;
        }
        Map<String, Object> variables;
        try {
            variables = getVariablesFromQueryParam(rc);
        } catch (Exception e) {
            rc.fail(400, e);
            return;
        }
        executeOne(rc, new GraphQLQuery(query, rc.queryParams().get("operationName"), variables));
    }

    private void handlePost(RoutingContext rc, Buffer body) {
        Map<String, Object> variables;
        try {
            variables = getVariablesFromQueryParam(rc);
        } catch (Exception e) {
            rc.fail(400, e);
            return;
        }

        String query = rc.queryParams().get("query");
        if (query != null) {
            executeOne(rc, new GraphQLQuery(query, rc.queryParams().get("operationName"), variables));
            return;
        }

        switch (getContentType(rc)) {
            case "application/json":
                handlePostJson(rc, body, rc.queryParams().get("operationName"), variables);
                break;
            case "application/graphql":
                executeOne(rc, new GraphQLQuery(body.toString(), rc.queryParams().get("operationName"), variables));
                break;
            default:
                rc.fail(415);
        }
    }

    private void handlePostJson(RoutingContext rc, Buffer body, String operationName, Map<String, Object> variables) {
        GraphQLInput graphQLInput;
        try {
            graphQLInput = JsonCodec.INSTANCE.fromBuffer(body, GraphQLInput.class);
        } catch (Exception e) {
            rc.fail(400, e);
            return;
        }
        if (graphQLInput instanceof GraphQLBatch) {
            handlePostBatch(rc, (GraphQLBatch) graphQLInput, operationName, variables);
        } else if (graphQLInput instanceof GraphQLQuery) {
            handlePostQuery(rc, (GraphQLQuery) graphQLInput, operationName, variables);
        } else {
            rc.fail(500);
        }
    }

    private void handlePostBatch(RoutingContext rc, GraphQLBatch batch, String operationName, Map<String, Object> variables) {
        for (GraphQLQuery query : batch) {
            if (query.getQuery() == null) {
                failQueryMissing(rc);
                return;
            }
            if (operationName != null) {
                query.setOperationName(operationName);
            }
            if (variables != null) {
                query.setVariables(variables);
            }
        }
        executeBatch(rc, batch);
    }

    private void executeBatch(RoutingContext rc, GraphQLBatch batch) {
        List<CompletableFuture<Buffer>> results = batch.stream()
                                                           .map(q -> gqlOperationService.execute(rc, q))
                                                           .collect(toList());

        // FIXME: send result as response

//        CompletableFuture.allOf(results.toArray(new CompletableFuture<?>[0]))
//                         .thenApply(v -> {
//                             JsonArray jsonArray = results.stream()
//                                                          .map(CompletableFuture::join)
//                                                          .collect(JsonArray::new, JsonArray::add, JsonArray::addAll);
//                             return jsonArray.toBuffer();
//                         })
//                         .whenComplete((buffer, throwable) -> {
//                             sendResponse(rc, buffer, throwable);
//                         });
    }

    private void handlePostQuery(RoutingContext rc, GraphQLQuery query, String operationName, Map<String, Object> variables) {
        if (query.getQuery() == null) {
            failQueryMissing(rc);
            return;
        }
        if (operationName != null) {
            query.setOperationName(operationName);
        }
        if (variables != null) {
            query.setVariables(variables);
        }
        executeOne(rc, query);
    }

    private void executeOne(RoutingContext rc, GraphQLQuery query) {
        gqlOperationService.execute(rc, query)
                           .whenComplete((buffer, throwable) -> sendResponse(rc, buffer, throwable));
    }

    private String getContentType(RoutingContext rc) {
        String contentType = rc.parsedHeaders().contentType().value();
        return contentType.isEmpty() ? "application/json" : contentType.toLowerCase();
    }

    private Map<String, Object> getVariablesFromQueryParam(RoutingContext rc) throws Exception {
        String variablesParam = rc.queryParams().get("variables");
        if (variablesParam == null) {
            return null;
        } else {
            return new JsonObject(variablesParam).getMap();
        }
    }

    private void sendResponse(RoutingContext rc, Buffer buffer, Throwable throwable) {
        if (throwable == null) {
            rc.response().putHeader(HttpHeaders.CONTENT_TYPE, "application/json").end(buffer);
        } else {
            rc.fail(throwable);
        }
    }

    private void failQueryMissing(RoutingContext rc) {
        rc.fail(400, new NoStackTraceThrowable("Query is missing"));
    }


}
