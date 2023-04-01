package org.kinotic.structuresserver


import org.kinotic.structuresserver.tests.TestBase
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration
import org.springframework.boot.autoconfigure.hazelcast.HazelcastAutoConfiguration
import org.springframework.boot.context.properties.EnableConfigurationProperties

@SpringBootApplication(exclude = [ HazelcastAutoConfiguration.class, JpaRepositoriesAutoConfiguration.class ])
@EnableConfigurationProperties
class StructuresServerTestApplication extends TestBase {

}
