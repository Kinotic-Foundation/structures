package org.kinotic.structuresserver.tests;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.kinotic.structuresserver.config.TestConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@ContextConfiguration(initializers = TestConfiguration.Initializer.class, classes = TestConfiguration.class)
public abstract class TestBase {

    @BeforeAll
    public void setUp(){
    }

    @AfterAll
    public void tearDown() {

    }

}
