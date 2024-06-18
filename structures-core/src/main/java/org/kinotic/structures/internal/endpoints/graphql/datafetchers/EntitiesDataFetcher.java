package org.kinotic.structures.internal.endpoints.graphql.datafetchers;

import com.apollographql.federation.graphqljava._Entity;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import lombok.RequiredArgsConstructor;
import org.kinotic.structures.api.services.EntitiesService;
import org.kinotic.structures.internal.utils.StructuresUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * Created by Navíd Mitchell 🤪 on 6/18/24.
 */
@SuppressWarnings("rawtypes")
@RequiredArgsConstructor
public class EntitiesDataFetcher implements DataFetcher<CompletableFuture<List<Map>>> {

    private final EntitiesService entitiesService;
    private final String namespace;

    @Override
    public CompletableFuture<List<Map>> get(DataFetchingEnvironment env) throws Exception {
        List<Map<String, Object>> representations = env.getArgument(_Entity.argumentName);
        if(representations != null) {

            List<CompletableFuture<Map>> futures = new ArrayList<>(representations.size());
            // TODO: change this to mget
            for (Map<String, Object> representation : representations) {
                String typename = (String) representation.get("__typename");
                String id = (String) representation.get("id");
                String structureId = StructuresUtil.structureNameToId(namespace, typename);
                futures.add(entitiesService.findById(structureId,
                                                     id,
                                                     Map.class,
                                                     env.getLocalContext())
                                           .thenApply(entity -> new EntityMap(entity, typename)));
            }

            return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                                    .thenApply(v -> futures.stream()
                                                           .map(CompletableFuture::join)
                                                           .collect(Collectors.toList()));

        }else{
            return CompletableFuture.completedFuture(List.of());
        }
    }

}
