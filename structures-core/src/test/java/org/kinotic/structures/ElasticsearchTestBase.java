package org.kinotic.structures;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.kinotic.structures.api.domain.AlreadyExistsException;
import org.kinotic.structures.api.domain.PermenentTraitException;
import org.kinotic.structures.api.domain.Trait;
import org.kinotic.structures.api.services.TraitService;
import org.kinotic.structures.config.TestElasticsearchConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.io.IOException;
import java.util.Optional;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@ContextConfiguration(initializers = TestElasticsearchConfiguration.Initializer.class, classes = TestElasticsearchConfiguration.class)
public abstract class ElasticsearchTestBase {
    @Autowired
    private TraitService traitService;
    @BeforeAll
    public void setUp() throws IOException, PermenentTraitException, AlreadyExistsException {
//        container = new ElasticsearchContainer(ELASTICSEARCH_IMAGE)
//                .withExposedPorts(9200)
//                .withEnv("discovery.type", "single-node");
//        container.start();

        Optional<Trait> ipOptional = traitService.getTraitByName("VpnIp");
        if(ipOptional.isEmpty()){
            Trait temp = new Trait();
            temp.setName("VpnIp");
            temp.setDescribeTrait("VpnIp address that the devices should be provided on the VLAN.");
            temp.setSchema("{ \"type\": \"string\", \"format\": \"ipv4\" }");
            temp.setEsSchema("{ \"type\": \"ip\" }");
            temp.setRequired(true);
            traitService.save(temp);
        }
    }
    @AfterAll
    public void tearDown() {
//        container.stop();
    }
}
