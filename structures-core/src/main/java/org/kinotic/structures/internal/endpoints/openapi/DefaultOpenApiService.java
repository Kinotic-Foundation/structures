package org.kinotic.structures.internal.endpoints.openapi;

import io.opentelemetry.instrumentation.annotations.SpanAttribute;
import io.opentelemetry.instrumentation.annotations.WithSpan;
import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.*;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;
import org.apache.commons.text.WordUtils;
import org.kinotic.continuum.core.api.crud.Pageable;
import org.kinotic.continuum.idl.api.converter.IdlConverter;
import org.kinotic.continuum.idl.api.schema.*;
import org.kinotic.structures.api.config.StructuresProperties;
import org.kinotic.structures.api.domain.NamedQueriesDefinition;
import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.api.domain.idl.PageC3Type;
import org.kinotic.structures.api.domain.idl.PageableC3Type;
import org.kinotic.structures.api.domain.idl.decorators.QueryDecorator;
import org.kinotic.structures.api.services.NamedQueriesService;
import org.kinotic.structures.api.services.StructureService;
import org.kinotic.structures.internal.api.services.StructureConversionService;
import org.kinotic.structures.internal.config.OpenApiSecurityType;
import org.kinotic.structures.internal.idl.converters.openapi.OpenApiConversionState;
import org.kinotic.structures.internal.utils.OpenApiUtils;
import org.kinotic.structures.internal.utils.QueryUtils;
import org.kinotic.structures.internal.utils.SqlQueryType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Created by Navíd Mitchell 🤪on 3/17/23.
 */
@RequiredArgsConstructor
@Component
public class DefaultOpenApiService implements OpenApiService {
    private static final Logger log = LoggerFactory.getLogger(DefaultOpenApiService.class);

    private final NamedQueriesService namedQueriesService;
    private final StructureConversionService structureConversionService;
    private final StructureService structureService;
    private final StructuresProperties structuresProperties;


    private static ApiResponses getDefaultResponses(){
        ApiResponses responses = new ApiResponses();
        responses.put("400", new ApiResponse().description("Bad Request"));
        responses.put("401", new ApiResponse().description("Unauthorized"));
        responses.put("403", new ApiResponse().description("Forbidden"));
        responses.put("404", new ApiResponse().description("Not Found"));
        responses.put("500", new ApiResponse().description("Internal Server Error"));
        return responses;
    }

    @WithSpan
    @Override
    public CompletableFuture<OpenAPI> getOpenApiSpec(@SpanAttribute("namespace") String namespace) {
        return structureService
                .findAllPublishedForNamespace(namespace, Pageable.ofSize(100))
                .thenComposeAsync(structures -> {
                    OpenAPI openAPI = new OpenAPI(SpecVersion.V30);

                    Info info = new Info()
                            .title(namespace + " Structures API")
                            .version("1.0")
                            .description("Provides access to Structures Items for the " + namespace + " namespace");
                    openAPI.setInfo(info);

                    if(structuresProperties.getOpenApiServerUrl() != null){
                        openAPI.addServersItem(new Server().url(structuresProperties.getOpenApiServerUrl()));
                    }

                    Components components = new Components();

                    // security scheme
                    if(structuresProperties.getOpenApiSecurityType() == OpenApiSecurityType.BASIC){
                        SecurityScheme securityScheme = new SecurityScheme();
                        securityScheme.setType(SecurityScheme.Type.HTTP);
                        securityScheme.setScheme("basic");
                        components.addSecuritySchemes("BasicAuth", securityScheme);
                        openAPI.setSecurity(List.of(new SecurityRequirement().addList("BasicAuth")));
                    } else if (structuresProperties.getOpenApiSecurityType() == OpenApiSecurityType.BEARER) {
                        SecurityScheme securityScheme = new SecurityScheme();
                        securityScheme.setType(SecurityScheme.Type.HTTP);
                        securityScheme.setScheme("bearer");
                        components.addSecuritySchemes("BearerAuth", securityScheme);
                        openAPI.setSecurity(List.of(new SecurityRequirement().addList("BearerAuth")));
                    }

                    Paths paths = new Paths();
                    String basePath = structuresProperties.getOpenApiPath();

                    IdlConverter<Schema<?>, OpenApiConversionState> converter
                            = structureConversionService.createOpenApiConverter();
                    for(Structure structure : structures.getContent()){

                        Schema<?> schema = converter.convert(structure.getEntityDefinition());
                        if(schema instanceof ObjectSchema){

                            components.addSchemas(structure.getName(), schema);
                            // Add path items for the structure
                            addDefaultPathItems(paths, basePath, structure);
                        }else{
                            log.error("Structure {} EntityDefinition did not convert to an OpenAPI ObjectSchema",
                                      structure.getId());
                        }

                        addNamedQueryPathItems(paths, basePath, structure, converter, components);
                    }

                    // Add all the referenced schemas
                    Map<String, Schema<?>> referencedSchemas = converter.getConversionContext()
                                                                        .state()
                                                                        .getReferencedSchemas();
                    for(Map.Entry<String, Schema<?>> entry : referencedSchemas.entrySet()){
                        components.addSchemas(entry.getKey(), entry.getValue());
                    }

                    ObjectSchema countSchema = new ObjectSchema();
                    countSchema.addProperty("count", new IntegerSchema())
                               .description("Contains the total count of items");
                    components.addSchemas("CountResponse", countSchema);

                    openAPI.setPaths(paths);
                    openAPI.components(components);
                    return CompletableFuture.completedFuture(openAPI);
                });
    }

    @WithSpan
    private void addDefaultPathItems(Paths paths, String basePath, Structure structure){

        String lowercaseNamespace = structure.getNamespace().toLowerCase();
        String lowercaseName = structure.getName().toLowerCase();
        String structureName = WordUtils.capitalize(structure.getName());

        // Create a path item for all the operations with basePath/structureNamespace/structureName/:id"
        PathItem byIdPathItem = new PathItem();

        // Operation for get by id
        Operation getByIdOperation = createOperation("Get "+structureName+" by Id",
                                                     "Gets " + structureName + " entities by their id.",
                                                     "get"+structureName+"ById",
                                                     structure,
                                                     1);


        getByIdOperation.addParametersItem(new Parameter().name("id")
                                                          .in("path")
                                                          .required(true)
                                                          .schema(new StringSchema())
                                                          .description("The id of the "+structureName+" to get."));

        byIdPathItem.get(getByIdOperation);

        // Operation for delete
        Operation deleteOperation = createOperation("Delete "+structureName,
                                                    "Deletes " + structureName + " entities",
                                                    "delete"+structureName,
                                                    structure,
                                                    -1);

        deleteOperation.addParametersItem(new Parameter().name("id")
                                                         .in("path")
                                                         .required(true)
                                                         .schema(new StringSchema())
                                                         .description("The id of the "+structureName+" to delete."));

        byIdPathItem.delete(deleteOperation);

        // add the path item to the paths
        paths.put(basePath + lowercaseNamespace + "/" + lowercaseName + "/{id}", byIdPathItem);


        // Operation for delete by query
        PathItem deleteByQueryPathItem = new PathItem();
        Operation deleteByQueryOperation = createOperation("Delete "+structureName+" by query",
                                                           "Delete " + structureName + " entities by query",
                                                           "delete"+structureName+"ByQuery",
                                                           structure,
                                                           -1);
        RequestBody deleteByQueryRequestBody = new RequestBody()
                .description("The query filter for delete operation")
                .content(new Content().addMediaType("text/plain",
                                                    new MediaType().schema(new StringSchema())));
        deleteByQueryOperation.requestBody(deleteByQueryRequestBody);
        deleteByQueryPathItem.post(deleteByQueryOperation);
        paths.put(basePath + lowercaseNamespace + "/" + lowercaseName + "/delete/by-query", deleteByQueryPathItem);


        // Find by Ids Operation
        PathItem findByIdsPathItem = new PathItem();
        Operation findByIdsOperation = createOperation("Find "+structureName +" entities by ids",
                                                       "Find " + structureName + " entities by their ids.",
                                                       "find"+structureName+"ByIds",
                                                       structure,
                                                       3);
        ArraySchema findByIdsSchema = new ArraySchema();
        findByIdsSchema.items(new StringSchema());
        RequestBody findByIdsRequestBody = new RequestBody()
                .description("The array if id's")
                .content(new Content().addMediaType("application/json",
                                                    new MediaType().schema(findByIdsSchema)));
        findByIdsOperation.requestBody(findByIdsRequestBody);
        findByIdsPathItem.post(findByIdsOperation);
        paths.put(basePath + lowercaseNamespace + "/" + lowercaseName + "/find/by-ids", findByIdsPathItem);


        // Create a path item for all the operations with basePath/structureNamespace/structureName/
        PathItem structurePathItem = new PathItem();

        // Request body for save operations
        Schema<?> refSchema = new Schema<>().$ref(Components.COMPONENTS_SCHEMAS_REF + structure.getName());
        RequestBody structureRequestBody = new RequestBody()
                .description("The "+structureName+" to save")
                .content(new Content().addMediaType("application/json",
                                                    new MediaType().schema(refSchema)));


        // Find All Operation
        Operation getAllOperation = createOperation("Find all "+structureName +" entities",
                                                    "Finds all " + structureName + " entities. Supports paging and sorting.",
                                                    "findAll"+structureName,
                                                    structure,
                                                    2);

        OpenApiUtils.addPagingAndSortingParameters(getAllOperation);

        structurePathItem.get(getAllOperation);


        // Save Operation
        Operation saveOperation = createOperation("Save "+structureName,
                                                    "Saves " + structureName + " entities.",
                                                    "save"+structureName,
                                                    structure,
                                                    1);
        saveOperation.requestBody(structureRequestBody);

        structurePathItem.post(saveOperation);

        // add the path item for all paths like basePath/structureNamespace/structureName/
        paths.put(basePath + lowercaseNamespace + "/" + lowercaseName, structurePathItem);

        // Sync Index operation
        PathItem syncPathItem = new PathItem();
        Operation syncOperation = createOperation("Sync " + structureName,
                                                  "Makes recent updates immediately available for search.",
                                                  "sync"+structureName,
                                                  structure,
                                                  -1);
        syncPathItem.get(syncOperation);
        paths.put(basePath + lowercaseNamespace + "/" + lowercaseName + "/util/sync", syncPathItem);


        // Update Operation
        PathItem updatePathItem = new PathItem();
        Operation updateOperation = createOperation("Update "+structureName,
                                                    "Updates " + structureName + " entities.",
                                                    "update"+structureName,
                                                    structure,
                                                    1);
        updateOperation.requestBody(structureRequestBody);
        updatePathItem.post(updateOperation);
        paths.put(basePath + lowercaseNamespace + "/" + lowercaseName + "/update", updatePathItem);


        // Bulk Save Operation
        PathItem bulkSavePathItem = new PathItem();
        Operation bulkSaveOperation = createOperation("Bulk Save for "+structureName + " entities",
                                                      "Saves multiple " + structureName + " entities.",
                                                      "bulkSave"+structureName,
                                                      structure,
                                                      -1);

        ArraySchema bulkSaveSchema = new ArraySchema();
        bulkSaveSchema.items(refSchema);
        RequestBody bulkSaveRequestBody = new RequestBody()
                .content(new Content().addMediaType("application/json",
                                                    new MediaType().schema(bulkSaveSchema)));
        bulkSaveOperation.requestBody(bulkSaveRequestBody);

        bulkSavePathItem.post(bulkSaveOperation);
        paths.put(basePath + lowercaseNamespace + "/" + lowercaseName + "/bulk", bulkSavePathItem);


        // Bulk Update Operation
        PathItem bulkUpdatePathItem = new PathItem();
        Operation bulkUpdateOperation = createOperation("Bulk Update for "+structureName + " entities",
                                                        "Updates multiple " + structureName + " entities.",
                                                        "bulkUpdate"+structureName,
                                                        structure,
                                                        -1);

        ArraySchema bulkUpdateSchema = new ArraySchema();
        bulkUpdateSchema.items(refSchema);
        RequestBody bulkUpdateRequestBody = new RequestBody()
                .content(new Content().addMediaType("application/json",
                                                    new MediaType().schema(bulkUpdateSchema)));
        bulkUpdateOperation.requestBody(bulkUpdateRequestBody);

        bulkUpdatePathItem.post(bulkUpdateOperation);
        paths.put(basePath + lowercaseNamespace + "/" + lowercaseName + "/bulk-update", bulkUpdatePathItem);


        // total count Operation
        PathItem countPathItem = new PathItem();
        Operation countOperation = createOperation("Get count for "+structureName,
                                                   "Gets total count of " + structureName + " entities.",
                                                   "count"+structureName,
                                                   structure,
                                                   0);
        countPathItem.get(countOperation);
        paths.put(basePath + lowercaseNamespace + "/" + lowercaseName + "/count/all", countPathItem);


        // total count for query Operation
        PathItem countByQueryPathItem = new PathItem();
        Operation countByQueryOperation = createOperation("Get count by query for "+structureName,
                                                          "Gets total count of "+structureName+" entities, by query",
                                                          "count"+structureName+"ByQuery",
                                                          structure,
                                                          0);
        RequestBody countByQueryRequestBody = new RequestBody()
                .description("The query to get counts for")
                .content(new Content().addMediaType("text/plain",
                                                    new MediaType().schema(new StringSchema())));
        countByQueryOperation.requestBody(countByQueryRequestBody);
        countByQueryPathItem.post(countByQueryOperation);
        paths.put(basePath + lowercaseNamespace + "/" + lowercaseName + "/count/by-query", countByQueryPathItem);


        // Create a path item for all the operations with basePath/structureNamespace/structureName/search
        PathItem searchPathItem = new PathItem();
        Operation searchOperation = createOperation("Search for "+structureName +" entities",
                                                    "Searches for " + structureName + " entities matching the search criteria. Supports paging and sorting.",
                                                    "search"+structureName,
                                                    structure,
                                                    2);

        OpenApiUtils.addPagingAndSortingParameters(searchOperation);

        RequestBody searchRequestBody = new RequestBody()
                .description("The search criteria")
                .content(new Content().addMediaType("text/plain",
                                                    new MediaType().schema(new StringSchema())));
        searchOperation.requestBody(searchRequestBody);

        searchPathItem.post(searchOperation);

        // add the path item for search to the paths
        paths.put(basePath + lowercaseNamespace + "/" + lowercaseName + "/search", searchPathItem);
    }

    @WithSpan
    private void addNamedQueryPathItems(Paths paths,
                                        String basePath,
                                        Structure structure,
                                        IdlConverter<Schema<?>, OpenApiConversionState> converter,
                                        Components components){

        String lowercaseNamespace = structure.getNamespace().toLowerCase();
        String lowercaseName = structure.getName().toLowerCase();
        NamedQueriesDefinition namedQueriesDefinition = namedQueriesService.findByNamespaceAndStructure(structure.getNamespace(),
                                                                                                        structure.getName())
                                                                           .join();
        if(namedQueriesDefinition != null){

            // For any FunctionDefinition create a Named Query path item
            for(FunctionDefinition query : namedQueriesDefinition.getNamedQueries()){
                String queryName = query.getName();
                String summary = "Named query: " + queryName;
                QueryDecorator queryDecorator = query.findDecorator(QueryDecorator.class);
                if(queryDecorator != null) {

                    // Build the response schema
                    ApiResponse response = createResponse(queryName,
                                                          query,
                                                          converter);

                    Operation queryOperation = createOperation(summary,
                                                               "Executes the named query " + queryName,
                                                               queryName,
                                                               structure,
                                                               response);

                    // Build the request body if there are parameters
                    RequestBody requestBody = convertParamsToRequestBody(queryName,
                                                                         query.getParameters(),
                                                                         converter,
                                                                         components);
                    if (requestBody != null) {
                        queryOperation.requestBody(requestBody);
                    }

                    PathItem queryPathItem = new PathItem();
                    queryPathItem.post(queryOperation);

                    // vertx route is
                    // :structureNamespace/:structureName/named-query/:queryName
                    // TODO: should we also check if a Pageable parameter is defined in the FunctionDefinition
                    if (query.getReturnType() instanceof PageC3Type) {
                        SqlQueryType queryType = QueryUtils.determineQueryType(queryDecorator.getStatements());
                        switch (queryType) {
                            case AGGREGATE:
                                OpenApiUtils.addCursorPagingWithoutSortParameters(queryOperation);
                                break;
                            case SELECT:
                                OpenApiUtils.addPagingAndSortingParameters(queryOperation);
                                break;
                            default:
                                log.warn("Named query {} in Structure {} has a Page return type but, paging is not supported for query type {}. No page parameters will be added.",
                                         queryName,
                                         structure.getName(),
                                         queryType);
                                break;
                        }
                        paths.put(basePath + lowercaseNamespace + "/" + lowercaseName + "/named-query-page/" + queryName,
                                  queryPathItem);
                    } else {
                        paths.put(basePath + lowercaseNamespace + "/" + lowercaseName + "/named-query/" + queryName,
                                  queryPathItem);
                    }
                }else{
                    log.warn("No QueryDecorator found for Named query {} in Structure {}. No OpenAPI path will be created.", queryName, structure.getName());
                }
            }
        }
    }

    private static ApiResponse createResponse(String queryName,
                                              FunctionDefinition query,
                                              IdlConverter<Schema<?>, OpenApiConversionState> converter) {

        ApiResponse response = new ApiResponse().description(queryName + " OK");
        Content content = new Content();

        C3Type returnType = query.getReturnType();

        if(!(returnType instanceof VoidC3Type)){
            MediaType mediaType = new MediaType();
            mediaType.setSchema(converter.convert(returnType));
            content.addMediaType("application/json", mediaType);
        }

        response.setContent(content);
        return response;
    }

    private RequestBody convertParamsToRequestBody(String name,
                                                   List<ParameterDefinition> parameters,
                                                   IdlConverter<Schema<?>, OpenApiConversionState> converter,
                                                   Components components){

        String requestSchemaName = WordUtils.capitalize(name) + "Request";
        ObjectSchema requestSchema = new ObjectSchema();
        requestSchema.setName(requestSchemaName);

        for(ParameterDefinition parameter : parameters){

            // We skip the pageable since this will be provided as a http query parameter
            if(!(parameter.getType() instanceof PageableC3Type)){

                Schema<?> schema = converter.convert(parameter.getType());

                // if this is an object we create a reference schema
                if(parameter.getType() instanceof ComplexC3Type){
                    ComplexC3Type complexField = (ComplexC3Type) parameter.getType();
                    components.addSchemas(complexField.getName(), schema);
                    schema = new Schema<>().$ref("#/components/schemas/"+complexField.getName());
                }
                requestSchema.addProperty(parameter.getName(), schema);
            }
        }

        // If no properties then we return null
        if(requestSchema.getProperties() != null
                && !requestSchema.getProperties().isEmpty()) {

            components.addSchemas(requestSchemaName, requestSchema);
            Schema<?> refSchema = new Schema<>().$ref("#/components/schemas/" + requestSchemaName);

            return new RequestBody().description("The Body for the named query " + name)
                                    .content(new Content()
                                                     .addMediaType("application/json",
                                                                   new MediaType().schema(refSchema)));
        } else {
            return null;
        }
    }

    private Operation createOperation(String operationSummary,
                                      String description,
                                      String operationId,
                                      Structure structure,
                                      int responseType) {

        // create a response for the structure item
        ApiResponse response = new ApiResponse().description(operationSummary + " OK");
        Content content = new Content();
        MediaType mediaType = new MediaType();

        if(responseType == 0){ // Count Response

            mediaType.setSchema(new Schema<>().$ref(Components.COMPONENTS_SCHEMAS_REF + "CountResponse"));

        }else if(responseType == 1){ // Structure Response

            mediaType.setSchema(new Schema<>().$ref(Components.COMPONENTS_SCHEMAS_REF + structure.getName()));

        }else if(responseType == 2){ // Page Response

            ObjectSchema pageSchema = OpenApiUtils.createPageSchema(new Schema<>().$ref(Components.COMPONENTS_SCHEMAS_REF + structure.getName()));
            mediaType.setSchema(pageSchema);

        }else if(responseType == 3){ // Array Response

            mediaType.setSchema(new ArraySchema()
                                        .items(new Schema<>().$ref(Components.COMPONENTS_SCHEMAS_REF + structure.getName())));

        }

        content.addMediaType("application/json", mediaType);
        response.setContent(content);

        return createOperation(operationSummary,
                               description,
                               operationId,
                               structure,
                               response);
    }

    private Operation createOperation(String operationSummary,
                                      String description,
                                      String operationId,
                                      Structure structure,
                                      ApiResponse response) {

        Operation operation = new Operation().summary(operationSummary)
                                             .description(description)
                                             .tags(List.of(structure.getName()))
                                             .operationId(operationId);

        if (structuresProperties.getOpenApiSecurityType() == OpenApiSecurityType.BASIC) {
            operation.security(List.of(new SecurityRequirement().addList("BasicAuth")));
        } else if (structuresProperties.getOpenApiSecurityType() == OpenApiSecurityType.BEARER) {
            operation.security(List.of(new SecurityRequirement().addList("BearerAuth")));
        }

        // Add the default responses and the response for the structure item being returned
        ApiResponses defaultResponses = getDefaultResponses();

        defaultResponses.put("200", response);

        operation.setResponses(defaultResponses);

        return operation;
    }

}
