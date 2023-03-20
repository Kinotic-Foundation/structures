package org.kinotic.structures.config;

import org.testcontainers.elasticsearch.ElasticsearchContainer;

public class ElasticsearchTestContainer extends ElasticsearchContainer {
    public ElasticsearchTestContainer(String dockerImageName) {
        super(dockerImageName);
    }
    public static ElasticsearchTestContainer create() {
        return new ElasticsearchTestContainer("docker.elastic.co/elasticsearch/elasticsearch:7.17.9");
    }
}