package org.kinotic.structures.internal.api.services.sql;

import co.elastic.clients.elasticsearch.sql.TranslateResponse;
import io.vertx.core.buffer.Buffer;

import java.util.concurrent.CompletableFuture;

/**
 * Created by Navíd Mitchell 🤪 on 4/29/24.
 */
public interface ElasticVertxClient {

    /**
     * Translates a SQL statement into an ElasticSearch SearchRequest
     *
     * @param statement the SQL statement to translate
     * @param params    any parameters to be used in the SQL statement
     * @return a CompletableFuture that will complete with the SearchRequest or an exception if an error occurred
     */
    CompletableFuture<TranslateResponse> translateSql(String statement,
                                                      Object[] params);

    /**
     * Executes a SQL statement against ElasticSearch
     *
     * @param statement the SQL statement to execute
     * @param params    any parameters to be used in the SQL statement
     * @return a CompletableFuture that will complete with the response or an exception if an error occurred
     */
    CompletableFuture<Buffer> querySql(String statement,
                                       Object[] params);
}
