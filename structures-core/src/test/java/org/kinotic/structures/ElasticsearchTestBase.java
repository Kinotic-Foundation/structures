package org.kinotic.structures;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.kinotic.structures.config.ElasticsearchProvider;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@ContextConfiguration(initializers = ElasticsearchProvider.Initializer.class)
public abstract class ElasticsearchTestBase {

    @BeforeAll
    public void setUp(){}

    @AfterAll
    public void tearDown() {}
}
