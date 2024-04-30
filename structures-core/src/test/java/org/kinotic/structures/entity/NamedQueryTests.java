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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kinotic.continuum.idl.api.schema.ArrayC3Type;
import org.kinotic.continuum.idl.api.schema.FunctionDefinition;
import org.kinotic.structures.api.decorators.QueryDecorator;
import org.kinotic.structures.api.domain.DefaultEntityContext;
import org.kinotic.structures.api.domain.EntityContext;
import org.kinotic.structures.api.domain.NamedQueriesDefinition;
import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.api.services.NamedQueriesService;
import org.kinotic.structures.internal.sample.DummyParticipant;
import org.kinotic.structures.support.StructureAndPersonHolder;
import org.kinotic.structures.support.TestHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class NamedQueryTests {

    @Autowired
    private NamedQueriesService namedQueriesService;
    @Autowired
    private TestHelper testHelper;


    private StructureAndPersonHolder createAndVerify(){
        return createAndVerify(1,
                               true,
                               new DefaultEntityContext(new DummyParticipant()),
                               "-" + System.currentTimeMillis());
    }

    private StructureAndPersonHolder createAndVerify(int numberOfPeopleToCreate,
                                                     boolean randomPeople,
                                                     EntityContext entityContext,
                                                     String structureSuffix){
        StructureAndPersonHolder ret = new StructureAndPersonHolder();

        StepVerifier.create(testHelper.createPersonStructureAndEntities(numberOfPeopleToCreate,
                                                                        randomPeople,
                                                                        entityContext,
                                                                        structureSuffix))
                    .expectNextMatches(structureAndPersonHolder -> {
                        boolean matches = structureAndPersonHolder.getStructure() != null &&
                                structureAndPersonHolder.getStructure().getId() != null &&
                                structureAndPersonHolder.getPersons().size() == numberOfPeopleToCreate;
                        if(matches){
                            ret.setStructure(structureAndPersonHolder.getStructure());
                            ret.setPersons(structureAndPersonHolder.getPersons());
                        }
                        return matches;
                    })
                    .verifyComplete();
        return ret;
    }

    @Test
    public void testNamedQuery() {

        EntityContext context1 = new DefaultEntityContext(new DummyParticipant("tenant1", "user1"));
        EntityContext context2 = new DefaultEntityContext(new DummyParticipant("kinotic", "user2"));

        StructureAndPersonHolder holder1 = createAndVerify(50, true, context1, "-testAll");

        Assertions.assertNotNull(holder1);

        StructureAndPersonHolder holder2 = createAndVerify(100, true, context2, "-testAll");

        Assertions.assertNotNull(holder2);

        NamedQueriesDefinition namedQueriesDefinition = createPeopleNamedQueries(holder1.getStructure());
        StepVerifier.create(Mono.fromFuture(() -> namedQueriesService.save(namedQueriesDefinition)))
                    .expectNextCount(1)
                    .verifyComplete();


    }

    private NamedQueriesDefinition createPeopleNamedQueries(Structure structure){
        QueryDecorator queryDecorator = new QueryDecorator()
                .setStatements("SELECT COUNT(*) as count FROM \""+structure.getItemIndex()+"\" GROUP BY lastName");
        FunctionDefinition findAllPeopleDefinition = new FunctionDefinition().setName("countPeopleByLastName");
        findAllPeopleDefinition.setDecorators(List.of(queryDecorator));
        findAllPeopleDefinition.setReturnType(new ArrayC3Type().setContains(structure.getEntityDefinition()));

        return new NamedQueriesDefinition().setNamespace(structure.getNamespace())
                                           .setStructure(structure.getName())
                                           .setId((structure.getNamespace() + "." + structure.getName()).toLowerCase())
                                           .setNamedQueries(List.of(findAllPeopleDefinition));
    }


}
