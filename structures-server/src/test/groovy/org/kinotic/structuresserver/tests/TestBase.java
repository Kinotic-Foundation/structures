package org.kinotic.structuresserver.tests;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.TestInstance;
import org.kinotic.structuresserver.config.TestConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@ContextConfiguration(initializers = TestConfiguration.Initializer.class, classes = TestConfiguration.class)
public abstract class TestBase {

//    @Autowired
//    private TraitService traitService;
//
//    @BeforeAll
//    public void setUp() throws IOException, PermenentTraitException, AlreadyExistsException {
//        Optional<Trait> realmOptional = traitService.getTraitByName("Custom");
//        if(realmOptional.isEmpty()){
//            Trait temp = new Trait();
//            temp.setName("Custom");
//            temp.setDescribeTrait("String field that defines a Custom Trait that can be applied to a Structure.");
//            temp.setSchema("{ \"type\": \"string\" }");
//            temp.setEsSchema("{ \"type\": \"keyword\" }");
//            temp.setRequired(true);
//            temp.setSystemManaged(true);
//            traitService.save(temp);
//        }
//    }
    @AfterAll
    public void tearDown() {}

}
