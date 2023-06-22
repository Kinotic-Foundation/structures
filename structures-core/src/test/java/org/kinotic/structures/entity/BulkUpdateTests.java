package org.kinotic.structures.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kinotic.structures.ElasticsearchTestBase;
import org.kinotic.structures.api.domain.DefaultEntityContext;
import org.kinotic.structures.api.domain.EntityContext;
import org.kinotic.structures.api.domain.RawJson;
import org.kinotic.structures.api.services.EntitiesService;
import org.kinotic.structures.internal.sample.DummyParticipant;
import org.kinotic.structures.support.StructureAndPersonHolder;
import org.kinotic.structures.support.TestHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class BulkUpdateTests extends ElasticsearchTestBase {

    @Autowired
    private EntitiesService entitiesService;
    @Autowired
    private TestHelper testHelper;

    private StructureAndPersonHolder createAndVerifyBulk(int numberOfPeopleToCreate,
                                                         boolean randomPeople,
                                                         EntityContext entityContext,
                                                         String structureSuffix){
        StructureAndPersonHolder ret = new StructureAndPersonHolder();

        StepVerifier.create(testHelper.createPersonStructureAndEntitiesBulk(numberOfPeopleToCreate,
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
    public void testBulk() throws InterruptedException{
        int numberOfPeopleToCreate = 50;
        EntityContext context1 = new DefaultEntityContext(new DummyParticipant("tenant1", "user1"));
        EntityContext context2 = new DefaultEntityContext(new DummyParticipant("tenant2", "user2"));

        StructureAndPersonHolder holder1 = createAndVerifyBulk(numberOfPeopleToCreate, true, context1, "-testBulk");

        Assertions.assertNotNull(holder1);

        StructureAndPersonHolder holder2 = createAndVerifyBulk(numberOfPeopleToCreate, true, context2, "-testBulk");

        Assertions.assertNotNull(holder2);

        // We have to wait since bulk updates are not queryable until they are indexed
        Thread.sleep(5000);

        // TODO: verify all data items as well, not just sizes
        StepVerifier.create(Mono.fromFuture(entitiesService.findAll(holder1.getStructure().getId(),
                                                                    Pageable.ofSize(numberOfPeopleToCreate * 2),// make sure page size is larger than number of entities
                                                                    RawJson.class,
                                                                    context1)))
                    .expectNextMatches(rawJsons -> rawJsons.getTotalElements() == numberOfPeopleToCreate
                            && rawJsons.getTotalPages() == 1
                            && rawJsons.getContent().size() == numberOfPeopleToCreate)
                    .as("Verifying Tenant 1 has "+numberOfPeopleToCreate+" entities")
                    .verifyComplete();

        StepVerifier.create(Mono.fromFuture(entitiesService.findAll(holder1.getStructure().getId(),
                                                                    Pageable.ofSize(numberOfPeopleToCreate * 2), // make sure page size is larger than number of entities
                                                                    RawJson.class,
                                                                    context2)))
                    .expectNextMatches(rawJsons -> rawJsons.getTotalElements() == numberOfPeopleToCreate
                            && rawJsons.getTotalPages() == 1
                            && rawJsons.getContent().size() == numberOfPeopleToCreate)
                    .as("Verifying Tenant 2 has "+numberOfPeopleToCreate+" entities")
                    .verifyComplete();
    }

}
