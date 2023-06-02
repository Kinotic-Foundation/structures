package org.kinotic.structures.internal.sample;

import org.kinotic.structures.api.config.StructuresProperties;
import org.kinotic.structures.api.services.EntitiesService;
import org.kinotic.structures.api.services.StructureService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by Navíd Mitchell 🤪 on 6/1/23.
 */
@Component
public class DataInitializer {

    private final StructureService structureService;
    private final EntitiesService entitiesService;
    private final StructuresProperties properties;

    public DataInitializer(StructureService structureService,
                           EntitiesService entitiesService,
                           StructuresProperties properties) {
        this.structureService = structureService;
        this.entitiesService = entitiesService;
        this.properties = properties;
    }

    @PostConstruct
    public void init() {
        if (properties.isInitializeWithSampleData()) {

        }
    }
}
