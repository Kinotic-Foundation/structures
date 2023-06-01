package org.kinotic.structures;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kinotic.structures.config.ElasticsearchProvider;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
@SpringBootTest
@ContextConfiguration(initializers = ElasticsearchProvider.Initializer.class)
public abstract class ElasticsearchTestBase {

    @BeforeAll
    public void setUp(){}

    @AfterAll
    public void tearDown() {}
}
