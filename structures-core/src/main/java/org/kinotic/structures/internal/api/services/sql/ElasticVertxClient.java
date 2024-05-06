package org.kinotic.structures.internal.api.services.sql;

import co.elastic.clients.elasticsearch.sql.TranslateResponse;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import org.kinotic.continuum.core.api.crud.Pageable;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Created by Navíd Mitchell 🤪 on 4/29/24.
 */
public interface ElasticVertxClient {

    /**
     * Translates a SQL statement into an ElasticSearch SearchRequest
     *
     * @param statement the SQL statement to translate
     * @param parameters    any parameters to be used in the SQL statement
     * @return a CompletableFuture that will complete with the SearchRequest or an exception if an error occurred
     */
    CompletableFuture<TranslateResponse> translateSql(String statement,
                                                      List<?> parameters);

    /**
     * Executes a SQL statement against ElasticSearch
     *
     * @param statement the SQL statement to execute
     * @param parameters any arguments to be used in the SQL statement
     * @param filter    the Query DSL filter if desired as explained <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/sql-rest-filtering.html">here</a>
     * @param pageable  the pageable to use for the query or null if not needed
     * @return a CompletableFuture that will complete with the response or an exception if an error occurred
     */
    CompletableFuture<Buffer> querySql(String statement,
                                       List<?> parameters,
                                       JsonObject filter,
                                       Pageable pageable);
}
