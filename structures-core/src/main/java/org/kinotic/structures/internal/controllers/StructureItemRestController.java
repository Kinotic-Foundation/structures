package org.kinotic.structures.internal.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.search.SearchHits;
import org.kinotic.structures.api.domain.TypeCheckMap;
import org.kinotic.structures.internal.api.services.ItemServiceInternal;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.security.Principal;
import java.util.*;

/**
 * Created by Navíd Mitchell 🤪 on 3/18/23.
 */
//@RestController
//@RequestMapping("/api")
public class StructureItemRestController {

    private final ItemServiceInternal itemService;
    private final ObjectMapper objectMapper;

    public StructureItemRestController(ItemServiceInternal itemService, ObjectMapper objectMapper) {
        this.itemService = itemService;
        this.objectMapper = objectMapper;
    }

    @GetMapping(value = "/{structureId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<String> listAll(@PathVariable String structureId,
                               @RequestParam(required = false, defaultValue = "0") int page,
                               @RequestParam(required = false, defaultValue = "25") int size,
                               Principal principal) {
        return Mono.defer(() -> {
            try {
                HashMap<String, Object> context = new HashMap<>();
                context.put("principal", principal);
                SearchHits searchHits = itemService.getAll(structureId, size, page, context);
                String json = objectMapper.writeValueAsString(searchHits);
                return Mono.just(json);
            } catch (Exception e) {
                return Mono.error(e);
            }
        }).subscribeOn(Schedulers.boundedElastic());
    }

    @PostMapping(value = "/{structureId}/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<String> search(@PathVariable String structureId,
                               @RequestBody String search,
                               @RequestParam(required = false, defaultValue = "0") int page,
                               @RequestParam(required = false, defaultValue = "25") int size,
                               Principal principal) {
        return Mono.defer(() -> {
            try {
                HashMap<String, Object> context = new HashMap<>();
                context.put("principal", principal);
                SearchHits searchHits = itemService.search(structureId, search, size, page, context);
                String json = objectMapper.writeValueAsString(searchHits);
                return Mono.just(json);
            } catch (Exception e) {
                return Mono.error(e);
            }
        }).subscribeOn(Schedulers.boundedElastic());
    }

    @PostMapping("/{structureId}")
    public Mono<LinkedHashMap<String, Object>> upsertItem(@PathVariable String structureId, @RequestBody Map<String, Object> item, Principal principal) {
        return Mono.defer(() -> {
            try {
                HashMap<String, Object> context = new HashMap<>();
                context.put("principal", principal);
                return Mono.just((LinkedHashMap<String, Object>)itemService.upsertItem(structureId, new TypeCheckMap(item), context));
            } catch (Exception e) {
                return Mono.error(e);
            }
        }).subscribeOn(Schedulers.boundedElastic());
    }

    @PostMapping("/{structureId}/bulk-upsert")
    public Mono<Void> bulkUpsertItem(@PathVariable String structureId, @RequestBody List<Map<String, Object>> itemList, Principal principal) throws Exception {
        try {
            itemService.requestBulkUpdatesForStructure(structureId);
            itemList.forEach(item -> {
                try {
                    HashMap<String, Object> context = new HashMap<>();
                    context.put("principal", principal);
                    itemService.pushItemForBulkUpdate(structureId, new TypeCheckMap(item), context);
                } catch (Exception e) {
                    // FIXME: how to handle this, we will not know where we had issues.. we could capture all the ones that errored out
                    //  and pass them back - or we fail fast and just respond with the id or some other identifiable info for debugging
                    throw new RuntimeException(e);
                }
            });
        } finally {
            itemService.flushAndCloseBulkUpdate(structureId);
        }
        return Mono.empty().then();
    }

    @GetMapping("/{structureId}/{id}")
    public Mono<LinkedHashMap<String, Object>> getItemById(@PathVariable String structureId, @PathVariable String id, Principal principal) {
        return Mono.defer(() -> {
            try {
                HashMap<String, Object> context = new HashMap<>();
                context.put("principal", principal);
                Optional<TypeCheckMap> item = itemService.getItemById(structureId, id, context);
                return item.map(typeCheckMap -> Mono.just((LinkedHashMap<String, Object>) typeCheckMap))
                           .orElseGet(Mono::empty);
            } catch (Exception e) {
                return Mono.error(e);
            }
        }).subscribeOn(Schedulers.boundedElastic());
    }

    @DeleteMapping("/{structureId}/{id}")
    public Mono<Void> deleteItem(@PathVariable String structureId, @PathVariable String id, Principal principal) {
        return Mono.defer(() -> {
            try {
                HashMap<String, Object> context = new HashMap<>();
                context.put("principal", principal);
                itemService.delete(structureId, id, context);
                return Mono.empty().then();
            } catch (Exception e) {
                return Mono.error(e);
            }
        }).subscribeOn(Schedulers.boundedElastic());
    }

}
