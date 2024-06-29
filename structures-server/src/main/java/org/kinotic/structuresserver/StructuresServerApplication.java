package org.kinotic.structuresserver;

import org.kinotic.continuum.api.annotations.EnableContinuum;
import org.kinotic.continuum.gateway.api.annotations.EnableContinuumGateway;
import org.kinotic.structures.api.annotations.EnableStructures;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.elasticsearch.ReactiveElasticsearchClientAutoConfiguration;
import org.springframework.boot.autoconfigure.hazelcast.HazelcastAutoConfiguration;

@SpringBootApplication(exclude = {HazelcastAutoConfiguration.class,
								  JpaRepositoriesAutoConfiguration.class,
								  ReactiveElasticsearchClientAutoConfiguration.class})
@EnableContinuum
@EnableStructures
@EnableContinuumGateway
public class StructuresServerApplication {
	public static void main(String[] args) {
		SpringApplication.run(StructuresServerApplication.class, args);
	}

}
