package org.kinotic.structures.item;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kinotic.structures.ElasticsearchTestBase;
import org.kinotic.structures.api.domain.AlreadyExistsException;
import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.api.domain.Trait;
import org.kinotic.structures.api.domain.TypeCheckMap;
import org.kinotic.structures.api.services.ItemService;
import org.kinotic.structures.api.services.StructureService;
import org.kinotic.structures.api.services.TraitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class BulkUpdateTests extends ElasticsearchTestBase {

    @Autowired
    private ItemService itemService;
    @Autowired
    private TraitService traitService;
    @Autowired
    private StructureService structureService;


    @Test
    public void bulkUpdateTest() throws Exception {

        Structure structure = getDeviceStructure();
        itemService.requestBulkUpdatesForStructure(structure.getId());

        for(int i = 0; i < 6000; i++){
            TypeCheckMap obj = new TypeCheckMap();
            obj.put("id", String.valueOf(i));
            obj.put("mac", "000000000001");
            obj.put("label", "Device-"+i);
            obj.put("description", "This is a description for device "+i);
            itemService.pushItemForBulkUpdate(structure.getId(), obj);
        }

        Thread.sleep(1000);// give time for ES to flush the new item

        long firstCount = itemService.count(structure.getId());
        Assert.isTrue(firstCount == 5000, "Bulk upload didn't complete properly before flush");

        itemService.flushAndCloseBulkUpdate(structure.getId());

        Thread.sleep(1000);

        long secondCount = itemService.count(structure.getId());
        Assert.isTrue(secondCount == 6000, "Bulk upload didn't complete properly after flush");

        Thread.sleep(1000);

        // run through deletion
        for(int i = 0; i < 6000; i++){
            itemService.delete(structure.getId(), String.valueOf(i));
        }

        Thread.sleep(1000);

        structureService.delete(structure.getId());

    }

    public Structure getDeviceStructure() throws AlreadyExistsException, IOException {
        Structure structure = new Structure();
        structure.setId("Device-" + System.currentTimeMillis());
        structure.setDescription("Defines an Device");

        Optional<Trait> macOptional = traitService.getTraitByName("Mac");
        Optional<Trait> keywordOptional = traitService.getTraitByName("KeywordString");

        structure.getTraits().put("mac", macOptional.get());
        structure.getTraits().put("label", keywordOptional.get());
        structure.getTraits().put("description", keywordOptional.get());

        structureService.save(structure);
        structureService.publish(structure.getId());
        return structure;
    }

}
