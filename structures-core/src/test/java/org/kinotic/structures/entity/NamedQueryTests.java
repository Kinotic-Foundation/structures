/*
 *
 * Copyright 2008-2021 Kinotic and the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kinotic.structures.entity;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kinotic.continuum.idl.api.schema.*;
import org.kinotic.structures.ElasticsearchTestBase;
import org.kinotic.structures.api.domain.DefaultEntityContext;
import org.kinotic.structures.api.domain.EntityContext;
import org.kinotic.structures.api.domain.NamedQueriesDefinition;
import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.api.domain.idl.decorators.QueryDecorator;
import org.kinotic.structures.api.services.EntitiesService;
import org.kinotic.structures.api.services.NamedQueriesService;
import org.kinotic.structures.internal.api.services.sql.MapParameterHolder;
import org.kinotic.structures.internal.sample.DummyParticipant;
import org.kinotic.structures.internal.sample.Person;
import org.kinotic.structures.support.StructureAndPersonHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class NamedQueryTests extends ElasticsearchTestBase {

    @Autowired
    private NamedQueriesService namedQueriesService;
    @Autowired
    private EntitiesService entitiesService;

    @Test
    public void testNamedQuery() {

        EntityContext context1 = new DefaultEntityContext(new DummyParticipant("tenant1", "user1"));
        EntityContext context2 = new DefaultEntityContext(new DummyParticipant("kinotic", "user2"));

        StructureAndPersonHolder holder1 = createAndVerify(50, true, context1, "namedQueryTest");

        Assertions.assertNotNull(holder1);

        StructureAndPersonHolder holder2 = createAndVerify(100, true, context2, "namedQueryTest");

        Assertions.assertNotNull(holder2);

        FunctionDefinition countPeopleByLastNameDefinition = createCountByLastName(holder2.getStructure());
        NamedQueriesDefinition namedQueriesDefinition = createNamedQuery(holder2.getStructure(), List.of(countPeopleByLastNameDefinition));
        StepVerifier.create(Mono.fromFuture(() -> namedQueriesService.save(namedQueriesDefinition)))
                    .expectNextCount(1)
                    .verifyComplete();

        // find last name to use
        List<Person> persons = holder2.getPersons();
        String lastName = persons.get(0).getLastName();

        // now count the number of people with that last name manually
        long count = persons.stream().filter(person -> person.getLastName().equals(lastName)).count();

        // Now query to make sure they match
        CompletableFuture<List<Map>> future = entitiesService.namedQuery(holder2.getStructure().getId(),
                                                                             "countPeopleByLastName",
                                                                             new MapParameterHolder(Map.of("lastName", lastName)),
                                                                             Map.class,
                                                                             context2);

        StepVerifier.create(Mono.fromFuture(() -> future))
                    .expectNextMatches(maps -> {
                        Assertions.assertEquals(1, maps.size());
                        Map<?,?> map = maps.get(0);
                        Object countReceived = map.get("count");
                        if(countReceived instanceof Integer){
                            Assertions.assertEquals(count, ((Integer)countReceived).longValue());
                        }else{
                            Assertions.assertEquals(count, countReceived);
                        }
                        Assertions.assertEquals(lastName, map.get("lastName"));
                        return true;
                    })
                    .verifyComplete();

        // Now change the named queries and make sure the cache get invalidated properly
        FunctionDefinition countAllPeopleDefinition = createCountAll(holder2.getStructure());
        NamedQueriesDefinition namedQueriesDefinition2 = createNamedQuery(holder2.getStructure(), List.of(countAllPeopleDefinition));
        StepVerifier.create(Mono.fromFuture(() -> namedQueriesService.save(namedQueriesDefinition2)))
                    .expectNextCount(1)
                    .verifyComplete();

        // Now query to make sure the new query is being used
        CompletableFuture<List<Map>> future2 = entitiesService.namedQuery(holder2.getStructure().getId(),
                                                                             "countAllPeople",
                                                                             null,
                                                                             Map.class,
                                                                             context2);

        StepVerifier.create(Mono.fromFuture(() -> future2))
                    .expectNextMatches(maps -> {
                        Assertions.assertEquals(1, maps.size());
                        Map<?,?> map = maps.get(0);
                        Object countReceived = map.get("count");
                        if(countReceived instanceof Integer){
                            Assertions.assertEquals(persons.size(), ((Integer)countReceived).longValue());
                        }else{
                            Assertions.assertEquals(persons.size(), countReceived);
                        }
                        return true;
                    })
                    .verifyComplete();

        // now make sure first query no longer exists
        CompletableFuture<List<Map>> future3 = entitiesService.namedQuery(holder2.getStructure().getId(),
                                                                             "countPeopleByLastName",
                                                                             new MapParameterHolder(Map.of("lastName", lastName)),
                                                                             Map.class,
                                                                             context2);
        // verify error
        StepVerifier.create(Mono.fromFuture(() -> future3))
                    .expectErrorMatches(throwable -> throwable instanceof IllegalArgumentException && throwable.getMessage().contains("No query found with name countPeopleByLastName"))
                    .verify();

    }

    private NamedQueriesDefinition createNamedQuery(Structure structure, List<FunctionDefinition> queries){

        return new NamedQueriesDefinition().setNamespace(structure.getNamespace())
                                           .setStructure(structure.getName())
                                           .setId((structure.getNamespace() + "." + structure.getName()).toLowerCase())
                                           .setNamedQueries(queries);
    }

    @NotNull
    private static FunctionDefinition createCountAll(Structure structure) {
        ObjectC3Type resultType = new ObjectC3Type().setName("Count")
                                                    .setNamespace(structure.getNamespace())
                                                    .addProperty("count", new LongC3Type());

        QueryDecorator queryDecorator = new QueryDecorator()
                .setStatements("SELECT COUNT(firstName) as count FROM \""+ structure.getItemIndex()+"\"");
        FunctionDefinition countPeopleDefinition = new FunctionDefinition().setName("countAllPeople");
        countPeopleDefinition.setDecorators(List.of(queryDecorator));
        countPeopleDefinition.setReturnType(new ArrayC3Type().setContains(resultType));
        return countPeopleDefinition;
    }

    @NotNull
    private static FunctionDefinition createCountByLastName(Structure structure) {
        ObjectC3Type resultType = new ObjectC3Type().setName("CountByLastName")
                                                    .setNamespace(structure.getNamespace())
                                                    .addProperty("count", new LongC3Type())
                                                    .addProperty("lastName", new StringC3Type());

        QueryDecorator queryDecorator = new QueryDecorator()
                .setStatements("SELECT COUNT(firstName) as count, lastName FROM \""+ structure.getItemIndex()+"\" WHERE lastName = ? GROUP BY lastName");
        FunctionDefinition countPeopleDefinition = new FunctionDefinition().setName("countPeopleByLastName");
        countPeopleDefinition.setDecorators(List.of(queryDecorator));
        countPeopleDefinition.addParameter("lastName", new StringC3Type());
        countPeopleDefinition.setReturnType(new ArrayC3Type().setContains(resultType));
        return countPeopleDefinition;
    }
}
