package org.kinotic.structuresserver;

import org.kinotic.continuum.api.annotations.EnableContinuum;
import org.kinotic.continuum.gateway.api.annotations.EnableContinuumGateway;
import org.kinotic.structures.api.annotations.EnableStructures;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.elasticsearch.ReactiveElasticsearchRestClientAutoConfiguration;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.graphql.GraphQlAutoConfiguration;
import org.springframework.boot.autoconfigure.graphql.reactive.GraphQlWebFluxAutoConfiguration;
import org.springframework.boot.autoconfigure.hazelcast.HazelcastAutoConfiguration;

@SpringBootApplication(exclude = {HazelcastAutoConfiguration.class,
								  JpaRepositoriesAutoConfiguration.class,
								  GraphQlAutoConfiguration.class,
								  GraphQlWebFluxAutoConfiguration.class,
								  ReactiveElasticsearchRestClientAutoConfiguration.class})
@EnableContinuum
@EnableStructures
@EnableContinuumGateway
public class StructuresServerApplication {
	public static void main(String[] args) {
		SpringApplication.run(StructuresServerApplication.class, args);
	}

}
