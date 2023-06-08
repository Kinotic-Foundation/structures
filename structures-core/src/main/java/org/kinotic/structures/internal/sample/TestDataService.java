package org.kinotic.structures.internal.sample;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.apache.commons.lang3.tuple.Pair;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.kinotic.continuum.idl.api.schema.ArrayC3Type;
import org.kinotic.continuum.idl.api.schema.ObjectC3Type;
import org.kinotic.continuum.idl.api.schema.StringC3Type;
import org.kinotic.continuum.internal.utils.ContinuumUtil;
import org.kinotic.structures.api.decorators.IdDecorator;
import org.kinotic.structures.api.decorators.NestedDecorator;
import org.kinotic.structures.api.decorators.TextDecorator;
import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.api.services.StructureService;
import org.kinotic.structures.internal.utils.StructuresUtil;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Created by Navíd Mitchell 🤪 on 6/1/23.
 */
@Component
public class TestDataService {

    private static final String PEOPLE_FILE = "classpath:people.json";
    private static final String PEOPLE_WITH_ID_FILE = "classpath:person-with-id.json";
    private static final String PEOPLE_KEY = "people";
    private static final String PEOPLE_WITH_ID_KEY = "people-with-id";

    private final StructureService structureService;

    private final AsyncLoadingCache<String, List<Person>> peopleCache;

    public TestDataService(StructureService structureService,
                           ResourceLoader resourceLoader,
                           ObjectMapper objectMapper) {

        this.structureService = structureService;

        peopleCache = Caffeine.newBuilder()
                              .maximumSize(10)
                              .expireAfterAccess(Duration.ofMinutes(5))
                              .buildAsync(new PeopleCacheLoader(resourceLoader, objectMapper));
    }

    /**
     * @return a {@link ObjectC3Type} representing a person.
     */
    public ObjectC3Type createPersonSchema() {
        return new ObjectC3Type()
                .setName("Person")
                .setNamespace("org.kinotic.sample")
                .addProperty("id", new StringC3Type().addDecorator(new IdDecorator()))
                .addProperty("firstName", new StringC3Type())
                .addProperty("lastName", new StringC3Type())
                .addProperty("addresses", new ArrayC3Type()
                        .setContains(new ObjectC3Type()
                                .setName("Address")
                                .setNamespace("org.kinotic.sample")
                                .addProperty("street", new StringC3Type().addDecorator(new TextDecorator()))
                                .addProperty("city", new StringC3Type())
                                .addProperty("state", new StringC3Type())
                                .addProperty("zip", new StringC3Type()))
                        .addDecorator(new NestedDecorator()));
    }

    /**
     * Creates a person structure if it does not exist.
     * @return a {@link CompletableFuture} that will return a {@link Pair} of the {@link Structure} and a {@link Boolean} indicating if the structure was created.
     */
    public CompletableFuture<Pair<Structure, Boolean>> createPersonStructureIfNotExists(){
        String structureId = StructuresUtil.structureNameToId("org.kinotic.data", "Person");
        return structureService.findById(structureId)
                               .thenCompose(structure -> {
                                   if(structure != null){
                                       return CompletableFuture.completedFuture(Pair.of(structure, false));
                                   }else{
                                       return createPersonStructure(null).thenApply(saved -> Pair.of(saved, true));
                                   }
                               });
    }

    /**
     * Creates a {@link Person} {@link Structure} and publishes it.
     * @param structureNameSuffix if not null will be appended to the structure name.
     * @return a {@link CompletableFuture} that will return the {@link Structure} that was created.
     */
    public CompletableFuture<Structure> createPersonStructure(String structureNameSuffix) {
        Structure structure = new Structure();
        structure.setName("Person"+(structureNameSuffix != null ? structureNameSuffix : ""));
        structure.setNamespace("org.kinotic.sample");
        structure.setDescription("Defines a Person");

        ObjectC3Type personType = createPersonSchema();

        structure.setEntityDefinition(personType);
        structure.setNamespace(personType.getNamespace());

        return structureService.save(structure)
                               .thenCompose(saved -> structureService.publish(saved.getId())
                                                                     .thenApply(published -> saved));
    }

    /**
     * @return a {@link CompletableFuture} that will return a random {@link Person} from the cache.
     */
    public CompletableFuture<Person> createTestPerson() {
        return peopleCache.get(PEOPLE_KEY).thenApply(people -> people.get(ContinuumUtil.getRandomNumberInRange(people.size() - 1)));
    }

    /**
     * @return a {@link CompletableFuture} that will return a random {@link Person} with the id populated, from the cache.
     */
    public CompletableFuture<Person> createTestPersonWithId() {
        return peopleCache.get(PEOPLE_WITH_ID_KEY).thenApply(people -> people.get(ContinuumUtil.getRandomNumberInRange(people.size() - 1)));
    }

    /**
     * @return a {@link CompletableFuture} that will return a {@link List} of random {@link Person} from the cache.
     */
    public CompletableFuture<List<Person>> createTestPeople(int numberToCreate) {
        return getPeopleCompletableFuture(numberToCreate, PEOPLE_KEY);
    }

    /**
     * @return a {@link CompletableFuture} that will return a {@link List} of random {@link Person} with the id populated, from the cache.
     */
    public CompletableFuture<List<Person>> createTestPeopleWithId(int numberToCreate) {
        return getPeopleCompletableFuture(numberToCreate, PEOPLE_WITH_ID_KEY);
    }

    private CompletableFuture<List<Person>> getPeopleCompletableFuture(int numberToCreate,
                                                                       String peopleKey) {
        return peopleCache.get(peopleKey).thenApply(people -> {
            int size = people.size();
            List<Person> ret = new ArrayList<>(numberToCreate);
            for(int i = 0; i < numberToCreate; i++) {
                ret.add(people.get(ContinuumUtil.getRandomNumberInRange(size - 1)));
            }
            return ret;
        });
    }

    private static class PeopleCacheLoader implements CacheLoader<String, List<Person>> {

        private final ResourceLoader resourceLoader;
        private final ObjectMapper objectMapper;

        public PeopleCacheLoader(ResourceLoader resourceLoader,
                                 ObjectMapper objectMapper) {
            this.resourceLoader = resourceLoader;
            this.objectMapper = objectMapper;
        }

        @Override
        public @Nullable List<Person> load(String key) throws Exception {
            if(key.equals(PEOPLE_KEY)) {
                return objectMapper.readValue(resourceLoader.getResource(PEOPLE_FILE).getInputStream(),
                                              objectMapper.getTypeFactory()
                                                          .constructCollectionType(List.class, Person.class));
            }else if(key.equals(PEOPLE_WITH_ID_KEY)) {
                return objectMapper.readValue(resourceLoader.getResource(PEOPLE_WITH_ID_FILE).getInputStream(),
                                              objectMapper.getTypeFactory()
                                                          .constructCollectionType(List.class, Person.class));
            }else{
                throw new IllegalArgumentException("Unknown key: " + key);
            }
        }
    }

}
