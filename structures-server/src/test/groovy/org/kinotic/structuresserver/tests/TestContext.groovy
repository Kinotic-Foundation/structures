package org.kinotic.structuresserver.tests

import org.junit.Assert
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.kinotic.structures.api.domain.AlreadyExistsException
import org.kinotic.structures.api.domain.Structure
import org.kinotic.structures.api.domain.StructureHolder
import org.kinotic.structures.api.domain.Trait
import org.kinotic.structures.api.domain.TraitHolder
import org.kinotic.structures.api.domain.TypeCheckMap
import org.kinotic.structures.api.services.ItemService
import org.kinotic.structures.api.services.StructureService
import org.kinotic.structures.api.services.TraitService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension.class)
@SpringBootTest
class TestContext extends TestBase {

    @Autowired
    StructureService structureService

    @Autowired
    TraitService traitService

    @Autowired
    ItemService itemService

    @Test
    void contextLoads() {
        Assert.assertTrue(true)
    }

    @Test
    void weHaveCustomTrait() {
        List<Trait> traits = traitService.getAllSystemManaged()
        boolean hasCustomTrait = false
        for(Trait t : traits){
            if(t.getName().equalsIgnoreCase("custom")){
                hasCustomTrait = true
                break
            }
        }
        Assert.assertTrue(hasCustomTrait)
    }

    @Test
    void attemptToAddStructureWithItem_shouldHaveCustomTrait(){

        StructureHolder structureHolder = new StructureHolder()
        Structure structure = new Structure()
        structure.setName("Person-" + System.currentTimeMillis())
        structure.setNamespace("org.kinotic.")
        structure.setDescription("Defines an Person")

        Optional<Trait> keywordStringOptional = traitService.getTraitByName("KeywordString")

        ArrayList<TraitHolder> traits = new ArrayList<>()
        traits.add(new TraitHolder(0, "state", keywordStringOptional.get()))
        traits.add(new TraitHolder(1, "city", keywordStringOptional.get()))
        traits.add(new TraitHolder(2, "address", keywordStringOptional.get()))
        traits.add(new TraitHolder(3, "firstName", keywordStringOptional.get()))
        traits.add(new TraitHolder(4, "lastName", keywordStringOptional.get()))

        structureHolder.setTraits(traits)
        structureHolder.setStructure(structure)

        structureHolder = structureService.save(structureHolder)
        structureService.publish(structureHolder.getStructure().getId())

        // now we can create an item with the above fields
        TypeCheckMap obj = new TypeCheckMap()
        obj.put("state", "Nevada")
        obj.put("city", "Las Vegas")
        obj.put("address", "111 Las Vegas Blvd")
        obj.put("firstName", "Marco")
        obj.put("lastName", "Polo")
        obj.put("id", "nevada-las_vegas-111_las_vegas_blvd")

        TypeCheckMap saved = itemService.upsertItem(structureHolder.getStructure().getId(), obj)

        try {
            Thread.sleep(1000)

            Optional<TypeCheckMap> freshOpt = itemService.getItemById(structure.getId(), "nevada-las_vegas-111_las_vegas_blvd")

            if(freshOpt.isEmpty()){
                throw new IllegalStateException("Composite Primary Key was not saved as expected")
            }

            TypeCheckMap fresh = freshOpt.get()

            if (!fresh.getString("custom").equals("custom")) {
                throw new IllegalStateException("Custom Default Trait was not used properly")
            }

        } catch (AlreadyExistsException e) {
            throw e
        } finally {
            itemService.delete(structureHolder.getStructure().getId(), saved.getString("id"))

            Thread.sleep(1000)

            structureService.delete(structureHolder.getStructure().getId())
        }
    }
}
