package org.kinotic.structures.support;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.kinotic.continuum.idl.api.directory.SchemaFactory;
import org.kinotic.continuum.idl.api.schema.C3Type;
import org.kinotic.continuum.idl.api.schema.ObjectC3Type;
import org.kinotic.structures.api.decorators.IdDecorator;
import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.api.services.EntitiesService;
import org.kinotic.structures.api.services.StructureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class TestHelper {

    @Autowired
    private SchemaFactory schemaFactory;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private StructureService structureService;

    @Autowired
    private EntitiesService entitiesService;

    public Person createTestPerson() {
        return new Person()
                .setFirstName("Jesse")
                .setLastName("Pinkman")
                .setAddresses(List.of(new Address()
                                              .setStreet("1001 Central Ave NE")
                                              .setCity("Albuquerque")
                                              .setState("NM")
                                              .setZip("87106")));
    }

    public CompletableFuture<Structure> createPersonStructure() {
        Structure structure = new Structure();
        structure.setName("Person-" + System.currentTimeMillis());
        structure.setDescription("Defines a Person");

        C3Type c3Type = schemaFactory.createForClass(Person.class);
        if(c3Type instanceof ObjectC3Type){
            ObjectC3Type personType = (ObjectC3Type) c3Type;
            personType.getProperties().get("id").addDecorator(new IdDecorator());
            structure.setEntityDefinition(personType);
            structure.setNamespace(personType.getNamespace());

            return structureService.save(structure)
                                   .thenCompose(saved -> structureService.publish(saved.getId())
                                                                         .thenApply(published -> saved));
        }else{
            return CompletableFuture.failedFuture(new RuntimeException("Failed to create structure for Person"));
        }
    }

    public Mono<StructureAndPersonHolder> createDataForSingleEntityTest(){
        return Mono.fromFuture(() -> createPersonStructure()
                .thenCompose(structure -> {
                    Person person = createTestPerson();
                    byte[] jsonData;
                    try {
                        jsonData = objectMapper.writeValueAsBytes(person);
                    } catch (JsonProcessingException e) {
                        return CompletableFuture.failedFuture(e);
                    }
                    ByteBuffer buffer = ByteBuffer.wrap(jsonData);
                    return entitiesService.save(structure.getId(), buffer)
                                          .thenCompose(saved -> {
                                              try {
                                                  Person savedPerson = objectMapper.readValue(saved.array(),
                                                                                              Person.class);
                                                  return CompletableFuture.completedFuture(new StructureAndPersonHolder(structure,
                                                                                                                        savedPerson));
                                              } catch (IOException e) {
                                                  return CompletableFuture.failedFuture(e);
                                              }
                                          });
                }));
    }

//
//    public Structure getDeviceStructure() throws AlreadyExistsException, IOException {
//        Structure structure = new Structure();
//        structure.setName("Device-" + System.currentTimeMillis());
//        structure.setNamespace("org.kinotic.");
//        structure.setDescription("Defines an Device");
//
//        Optional<Trait> vpnIpOptional = traitService.getTraitByName("VpnIp");
//        Optional<Trait> ipOptional = traitService.getTraitByName("Ip");
//        Optional<Trait> macOptional = traitService.getTraitByName("Mac");
//        Optional<Trait> textOptional = traitService.getTraitByName("KeywordString");
//
//        structure.getTraits().put("vpnIp", vpnIpOptional.get());
//        structure.getTraits().put("ip", ipOptional.get());
//        structure.getTraits().put("mac", macOptional.get());
//        structure.getTraits().put("label", textOptional.get());
//        structure.getTraits().put("description", textOptional.get());
//
//        Structure fresh = structureService.save(structure);
//        structureService.publish(fresh.getId());
//        return fresh;
//    }
//
//    public Structure getComputerStructure(String namespace) throws AlreadyExistsException, IOException {
//        Structure structure = new Structure();
//        structure.setName("Computer-" + System.currentTimeMillis());
//        structure.setNamespace(namespace);
//        structure.setDescription("Defines an Computer");
//
//        Optional<Trait> vpnIpOptional = traitService.getTraitByName("VpnIp");
//        Optional<Trait> ipOptional = traitService.getTraitByName("Ip");
//        Optional<Trait> macOptional = traitService.getTraitByName("Mac");
//
//        structure.getTraits().put("vpnIp", vpnIpOptional.get());
//        structure.getTraits().put("ip", ipOptional.get());
//        structure.getTraits().put("mac", macOptional.get());
//
//        Structure fresh = structureService.save(structure);
//        structureService.publish(fresh.getId());
//        return structure;
//    }
//
//    public Structure getOfficeStructure(String namespace) throws AlreadyExistsException, IOException {
//        Structure structure = new Structure();
//        structure.setName("Office-" + System.currentTimeMillis());
//        structure.setNamespace(namespace);
//        structure.setDescription("Defines a Office");
//
//        Optional<Trait> textOptional = traitService.getTraitByName("TextString");
//        Optional<Trait> keywordOptional = traitService.getTraitByName("KeywordString");
//
//        structure.getTraits().put("partNumber", textOptional.get());
//        structure.getTraits().put("computer", keywordOptional.get());
//        structure.getTraits().put("device1", keywordOptional.get());
//        structure.getTraits().put("device2", keywordOptional.get());
//
//        Structure fresh = structureService.save(structure);
//        structureService.publish(fresh.getId());
//        return structure;
//    }
}
