package org.kinotic.structures.util;

import org.kinotic.structures.api.domain.AlreadyExistsException;
import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.api.domain.Trait;
import org.kinotic.structures.api.services.StructureService;
import org.kinotic.structures.api.services.TraitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
public class StructureTestHelper {

    @Autowired
    private TraitService traitService;
    @Autowired
    private StructureService structureService;

    public Structure getSimpleItemStructure() throws AlreadyExistsException, IOException {

        Structure structure = new Structure();
        structure.setName("Item1-" + System.currentTimeMillis());
        structure.setNamespace("org_kinotic_");
        structure.setDescription("Defines an Item1");

        Optional<Trait> vpnIpOptional = traitService.getTraitByName("VpnIp");// already not required
        Optional<Trait> ipOptional = traitService.getTraitByName("Ip");
        Optional<Trait> macOptional = traitService.getTraitByName("Mac");
        Trait description = traitService.getTraitByName("TextString").get();
        description.setRequired(false);// ensure not required
        Trait id = traitService.getTraitByName("Id").get();
        id.setRequired(false);

        structure.getTraits().put("id", id);
        structure.getTraits().put("vpnIp", vpnIpOptional.get());
        structure.getTraits().put("ip", ipOptional.get());
        structure.getTraits().put("mac", macOptional.get());
        structure.getTraits().put("description", description);

        Structure fresh = structureService.save(structure);
        structureService.publish(fresh.getId());
        return fresh;
    }

    public Structure getDeviceStructure() throws AlreadyExistsException, IOException {
        Structure structure = new Structure();
        structure.setName("Device-" + System.currentTimeMillis());
        structure.setNamespace("org.kinotic.");
        structure.setDescription("Defines an Device");

        Optional<Trait> vpnIpOptional = traitService.getTraitByName("VpnIp");
        Optional<Trait> ipOptional = traitService.getTraitByName("Ip");
        Optional<Trait> macOptional = traitService.getTraitByName("Mac");
        Optional<Trait> textOptional = traitService.getTraitByName("KeywordString");

        structure.getTraits().put("vpnIp", vpnIpOptional.get());
        structure.getTraits().put("ip", ipOptional.get());
        structure.getTraits().put("mac", macOptional.get());
        structure.getTraits().put("label", textOptional.get());
        structure.getTraits().put("description", textOptional.get());

        Structure fresh = structureService.save(structure);
        structureService.publish(fresh.getId());
        return fresh;
    }

    public Structure getComputerStructure(String namespace) throws AlreadyExistsException, IOException {
        Structure structure = new Structure();
        structure.setName("Computer-" + System.currentTimeMillis());
        structure.setNamespace(namespace);
        structure.setDescription("Defines an Computer");

        Optional<Trait> vpnIpOptional = traitService.getTraitByName("VpnIp");
        Optional<Trait> ipOptional = traitService.getTraitByName("Ip");
        Optional<Trait> macOptional = traitService.getTraitByName("Mac");

        structure.getTraits().put("vpnIp", vpnIpOptional.get());
        structure.getTraits().put("ip", ipOptional.get());
        structure.getTraits().put("mac", macOptional.get());

        Structure fresh = structureService.save(structure);
        structureService.publish(fresh.getId());
        return structure;
    }

    public Structure getOfficeStructure(String namespace) throws AlreadyExistsException, IOException {
        Structure structure = new Structure();
        structure.setName("Office-" + System.currentTimeMillis());
        structure.setNamespace(namespace);
        structure.setDescription("Defines a Office");

        Optional<Trait> textOptional = traitService.getTraitByName("TextString");
        Optional<Trait> keywordOptional = traitService.getTraitByName("KeywordString");

        structure.getTraits().put("partNumber", textOptional.get());
        structure.getTraits().put("computer", keywordOptional.get());
        structure.getTraits().put("device1", keywordOptional.get());
        structure.getTraits().put("device2", keywordOptional.get());

        Structure fresh = structureService.save(structure);
        structureService.publish(fresh.getId());
        return structure;
    }
}
