package org.kinotic.structures;

import org.kinotic.structures.api.domain.DefaultEntityContext;
import org.kinotic.structures.api.domain.EntityContext;
import org.kinotic.structures.api.config.ElasticConnectionInfo;
import org.kinotic.structures.internal.sample.DummyParticipant;
import org.kinotic.structures.support.StructureAndPersonHolder;
import org.kinotic.structures.support.TestHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import reactor.test.StepVerifier;

import java.util.List;

@SpringBootTest
public abstract class ElasticsearchTestBase {

    public static final ElasticsearchContainer ELASTICSEARCH_CONTAINER;

    @Autowired
    protected TestHelper testHelper;

    static {
        String osName = System.getProperty("os.name");
        String osArch = System.getProperty("os.arch");

        ELASTICSEARCH_CONTAINER = new ElasticsearchContainer("docker.elastic.co/elasticsearch/elasticsearch:8.17.3");
        ELASTICSEARCH_CONTAINER.withEnv("discovery.type", "single-node")
                               .withEnv("xpack.security.enabled", "false");

        // We need this until this is resolved https://github.com/elastic/elasticsearch/issues/118583
        if(osName != null && osName.startsWith("Mac") && osArch != null && osArch.equals("aarch64")){
            ELASTICSEARCH_CONTAINER.withEnv("_JAVA_OPTIONS", "-XX:UseSVE=0");
        }

        ELASTICSEARCH_CONTAINER.start();
    }


    @DynamicPropertySource
    static void registerElasticProperties(DynamicPropertyRegistry registry) {
        String[] parts = ELASTICSEARCH_CONTAINER.getHttpHostAddress().split(":");
        ElasticConnectionInfo connectionInfo = new ElasticConnectionInfo(parts[0], Integer.parseInt(parts[1]), "http");
        registry.add("spring.data.elasticsearch.cluster-nodes", ELASTICSEARCH_CONTAINER::getHttpHostAddress);
        registry.add("structures.elastic-connections", () -> List.of(connectionInfo));
    }

    protected StructureAndPersonHolder createAndVerify(){
        return createAndVerify(1,
                               true,
                               new DefaultEntityContext(new DummyParticipant()),
                               "_" + System.currentTimeMillis());
    }

    protected StructureAndPersonHolder createAndVerify(int numberOfPeopleToCreate,
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
}
