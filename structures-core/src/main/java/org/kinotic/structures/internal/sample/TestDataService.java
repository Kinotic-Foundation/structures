package org.kinotic.structures.internal.sample;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.kinotic.continuum.idl.api.schema.ArrayC3Type;
import org.kinotic.continuum.idl.api.schema.ObjectC3Type;
import org.kinotic.continuum.idl.api.schema.StringC3Type;
import org.kinotic.continuum.internal.utils.ContinuumUtil;
import org.kinotic.structures.api.decorators.IdDecorator;
import org.kinotic.structures.api.decorators.TextDecorator;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    private final ResourceLoader resourceLoader;
    private final ObjectMapper objectMapper;
    private final AsyncLoadingCache<String, List<Person>> peopleCache;

    public TestDataService(ResourceLoader resourceLoader, ObjectMapper objectMapper) {
        this.resourceLoader = resourceLoader;
        this.objectMapper = objectMapper;
        peopleCache = Caffeine.newBuilder()
                              .maximumSize(10)
                              .expireAfterAccess(Duration.ofMinutes(5))
                              .buildAsync(new PeopleCacheLoader(resourceLoader, objectMapper));
    }

    public ObjectC3Type createPersonSchema() {
        return new ObjectC3Type()
                .setName("Person")
                .setNamespace("org.kinotic.structures.internal.sample")
                .setProperties(Map.of("id", new StringC3Type().addDecorator(new IdDecorator()),
                                      "firstName", new StringC3Type(),
                                      "lastName", new StringC3Type(),
                                      "addresses", new ArrayC3Type()
                                              .setContains(new ObjectC3Type()
                                                                   .setName("Address")
                                                                   .setNamespace("org.kinotic.structures.internal.sample")
                                                                   .setProperties(Map.of("street", new StringC3Type()
                                                                                                 .addDecorator(new TextDecorator()),
                                                                                         "city", new StringC3Type(),
                                                                                         "state", new StringC3Type(),
                                                                                         "zip", new StringC3Type())))));
    }

    public CompletableFuture<Person> createTestPerson() {
        return peopleCache.get(PEOPLE_KEY).thenApply(people -> people.get(ContinuumUtil.getRandomNumberInRange(people.size() - 1)));
    }

    public CompletableFuture<Person> createTestPersonWithId() {
        return peopleCache.get(PEOPLE_WITH_ID_KEY).thenApply(people -> people.get(ContinuumUtil.getRandomNumberInRange(people.size() - 1)));
    }

    public CompletableFuture<List<Person>> createTestPeople(int numberToCreate) {
        return getPeopleCompletableFuture(numberToCreate, PEOPLE_KEY);
    }

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
