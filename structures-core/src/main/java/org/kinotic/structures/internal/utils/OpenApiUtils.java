package org.kinotic.structures.internal.utils;

import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.media.*;
import io.swagger.v3.oas.models.parameters.Parameter;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Created by Navíd Mitchell 🤪 on 5/7/24.
 */
public class OpenApiUtils {

    public static ObjectSchema createPageSchema(Schema<?> pageContentSchema){
        ObjectSchema pageSchema = new ObjectSchema();
        pageSchema.addProperty("content", new ArraySchema()
                          .items(pageContentSchema))
                          .description("The content of the page.")
                  .addProperty("totalElements", new IntegerSchema()
                          .nullable(true)
                          .description("The total number of entities."))
                  .addProperty("cursor", new StringSchema()
                          .nullable(true)
                          .description("The cursor to be used for subsequent retrieval of data. Null if no more data is available, or if cursor paging is not being used."));
        return pageSchema;
    }

    public static void addPagingAndSortingParameters(Operation operation){
        operation.addParametersItem(new Parameter().name("page")
                                                   .in("query")
                                                   .required(false)
                                                   .schema(new IntegerSchema().nullable(true))
                                                   .description("The page number to get. The first page is 0. (This is not needed if cursor paging is being used.)"));

        operation.addParametersItem(new Parameter().name("cursor")
                                                   .in("query")
                                                   .required(false)
                                                   .schema(new StringSchema().nullable(true))
                                                   .description("The page to get with a cursor. Use null to get the first page. (This is not needed if offset paging is being used.)"));

        operation.addParametersItem(new Parameter().name("size")
                                                   .in("query")
                                                   .required(false)
                                                   .schema(new IntegerSchema()._default(25)
                                                                              .maximum(BigDecimal.valueOf(1000)))
                                                   .description("The number of items per page. The default is 25."));
        addSortingParameters(operation);
    }

    public static void addSortingParameters(Operation operation){
        operation.addParametersItem(new Parameter().name("sort")
                                                   .in("query")
                                                   .required(false)
                                                   .schema(new StringSchema())
                                                   .description("The field to apply sorting to."
                                                                        +  " Multiple sort fields can be applied by separating them with a comma."
                                                                        +  " The sort order for each sort field will be ascending unless it is prefixed with a minus (“-“), in which case it will be descending.")
                                                   .examples(Map.of("Single Sort", new Example().value("name"),
                                                                    "Multiple Sort", new Example().value("name,-age"))));
    }

}
