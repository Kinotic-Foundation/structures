package org.kinotic.structuresserver.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.search.SearchHits;
import org.kinotic.structures.api.domain.TypeCheckMap;
import org.kinotic.structures.api.services.ItemService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by Navíd Mitchell 🤪 on 3/18/23.
 */
@RestController
// Currently this is the only path that is secured
@RequestMapping("/api")
public class StructureItemController {

    private final ItemService itemService;
    private final ObjectMapper objectMapper;

    public StructureItemController(ItemService itemService, ObjectMapper objectMapper) {
        this.itemService = itemService;
        this.objectMapper = objectMapper;
    }

    @GetMapping(value = "/{structureId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<String> listItems(@PathVariable String structureId,
                                  @RequestParam(required = false, defaultValue = "0") int page,
                                  @RequestParam(required = false, defaultValue = "25") int size) {
        return Mono.defer(() -> {
            try {
                SearchHits searchHits = itemService.getAll(structureId, size, page);
                String json = objectMapper.writeValueAsString(searchHits);
                return Mono.just(json);
            } catch (Exception e) {
                return Mono.error(e);
            }
        }).subscribeOn(Schedulers.boundedElastic());
    }

    @PostMapping(value = "/{structureId}/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<String> listItems(@PathVariable String structureId,
                                  @RequestBody String search,
                                  @RequestParam(required = false, defaultValue = "0") int page,
                                  @RequestParam(required = false, defaultValue = "25") int size) {
        return Mono.defer(() -> {
            try {
                SearchHits searchHits = itemService.search(structureId, search, size, page);
                String json = objectMapper.writeValueAsString(searchHits);
                return Mono.just(json);
            } catch (Exception e) {
                return Mono.error(e);
            }
        }).subscribeOn(Schedulers.boundedElastic());
    }

    @PostMapping("/{structureId}")
    public Mono<LinkedHashMap<String, Object>> upsertItem(@PathVariable String structureId, @RequestBody Map<String, Object> item) {
        return Mono.defer(() -> {
            try {
                return Mono.just((LinkedHashMap<String, Object>)itemService.upsertItem(structureId, new TypeCheckMap(item)));
            } catch (Exception e) {
                return Mono.error(e);
            }
        }).subscribeOn(Schedulers.boundedElastic());
    }

    @PostMapping("/{structureId}/bulk-upsert")
    public Mono<Void> bulkUpsertItem(@PathVariable String structureId, @RequestBody List<Map<String, Object>> itemList) throws Exception {
        try {
            itemService.requestBulkUpdatesForStructure(structureId);
            itemList.forEach(item -> {
                try {
                    itemService.pushItemForBulkUpdate(structureId, new TypeCheckMap(item));
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
    public Mono<LinkedHashMap<String, Object>> getItemById(@PathVariable String structureId, @PathVariable String id) {
        return Mono.defer(() -> {
            try {
                Optional<TypeCheckMap> item = itemService.getItemById(structureId, id);
                return item.map(typeCheckMap -> Mono.just((LinkedHashMap<String, Object>) typeCheckMap))
                           .orElseGet(Mono::empty);
            } catch (Exception e) {
                return Mono.error(e);
            }
        }).subscribeOn(Schedulers.boundedElastic());
    }

    @DeleteMapping("/{structureId}/{id}")
    public Mono<Void> deleteItem(@PathVariable String structureId, @PathVariable String id) {
        return Mono.defer(() -> {
            try {
                itemService.delete(structureId, id);
                return Mono.empty().then();
            } catch (Exception e) {
                return Mono.error(e);
            }
        }).subscribeOn(Schedulers.boundedElastic());
    }

}