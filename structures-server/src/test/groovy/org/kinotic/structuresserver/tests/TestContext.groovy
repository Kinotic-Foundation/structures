package org.kinotic.structuresserver.tests


import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension.class)
@SpringBootTest
class TestContext extends TestBase {

//    @Autowired
//    StructureService structureService
//
//    @Autowired
//    TraitService traitService
//
//    @Autowired
//    ItemService itemService
//
//    @Autowired
//    private RestHighLevelClient highLevelClient
//
//    @Test
//    void contextLoads() {
//        Assert.assertTrue(true)
//    }
//
//    @Test
//    void weHaveCustomTrait() {
//        List<Trait> traits = traitService.getAllSystemManaged()
//        boolean hasCustomTrait = false
//        for(Trait t : traits){
//            if(t.getName().equalsIgnoreCase("custom")){
//                hasCustomTrait = true
//                break
//            }
//        }
//        Assert.assertTrue(hasCustomTrait)
//    }
//
//    @Test
//    void attemptToAddStructureWithItem_shouldHaveCustomTrait(){
//
//        StructureHolder structureHolder = getPerson()
//
//        HashMap<String, Object> customRed = new HashMap<String, Object>()
//        customRed.put("custom", "red")
//
//        HashMap<String, Object> customBlue = new HashMap<String, Object>()
//        customBlue.put("custom", "blue")
//
//        // now we can create an item with the above fields
//        TypeCheckMap obj = new TypeCheckMap()
//        obj.put("state", "Nevada")
//        obj.put("city", "Las Vegas")
//        obj.put("address", "111 Las Vegas Blvd")
//        obj.put("firstName", "Marco")
//        obj.put("lastName", "Polo")
//        obj.put("id", "nevada-las_vegas-111_las_vegas_blvd")
//
//        TypeCheckMap saved = itemService.upsertItem(structureHolder.getStructure().getId(), obj, customRed)
//
//        try {
//            Thread.sleep(1000)
//
//            Optional<TypeCheckMap> freshOpt = itemService.getItemById(structureHolder.getStructure().getId(), "nevada-las_vegas-111_las_vegas_blvd", customRed)
//
//            if(freshOpt.isEmpty()){
//                throw new IllegalStateException("Composite Primary Key was not saved as expected")
//            }
//
//            TypeCheckMap fresh = freshOpt.get()
//
//            if (!fresh.getString("custom").equals("red")) {
//                throw new IllegalStateException("Custom Default Trait was not used properly")
//            }
//
//        } catch (AlreadyExistsException e) {
//            throw e
//        } finally {
//            itemService.delete(structureHolder.getStructure().getId(), saved.getString("id"), customRed)
//
//            Thread.sleep(1000)
//
//            structureService.delete(structureHolder.getStructure().getId())
//        }
//    }
//
//    @Test
//    void attemptToAddStructureWithItem_AttemptGettingWithNewContext(){
//
//        StructureHolder structureHolder = getPerson()
//
//        HashMap<String, Object> customRed = new HashMap<String, Object>()
//        customRed.put("custom", "red")
//
//        HashMap<String, Object> customBlue = new HashMap<String, Object>()
//        customBlue.put("custom", "blue")
//
//        // now we can create an item with the above fields
//        TypeCheckMap obj = new TypeCheckMap()
//        obj.put("state", "Nevada")
//        obj.put("city", "Las Vegas")
//        obj.put("address", "111 Las Vegas Blvd")
//        obj.put("firstName", "Marco")
//        obj.put("lastName", "Polo")
//        obj.put("id", "nevada-las_vegas-111_las_vegas_blvd")
//
//        TypeCheckMap saved = itemService.upsertItem(structureHolder.getStructure().getId(), obj, customRed)
//
//        try {
//            Thread.sleep(1000)
//
//            Optional<TypeCheckMap> freshOpt = itemService.getItemById(structureHolder.getStructure().getId(), "nevada-las_vegas-111_las_vegas_blvd", customBlue)
//
//            Assertions.assertTrue(freshOpt.isEmpty())
//
//        } catch (AlreadyExistsException e) {
//            throw e
//        } finally {
//            itemService.delete(structureHolder.getStructure().getId(), saved.getString("id"), customRed)
//
//            Thread.sleep(1000)
//
//            structureService.delete(structureHolder.getStructure().getId())
//        }
//    }
//
//    @Test
//    void bulkUpsertTest_ValidateCustomTraitSearch(){
//
//        StructureHolder structureHolder = getPerson()
//
//        // we have a default trait that is called custom and uses the context to set the value
//        // we will filter the values automatically
//        HashMap<String, Object> hometown = new HashMap<String, Object>()
//        hometown.put("custom", "New Mexico")
//
//        HashMap<String, Object> visiting = new HashMap<String, Object>()
//        visiting.put("custom", "New York")
//
//        try {
//            itemService.requestBulkUpdatesForStructure(structureHolder.getStructure().getId())
//            for(int i = 0; i < 6000; i++){
//                TypeCheckMap obj = new TypeCheckMap()
//                obj.put("state", "State-${i%10}".toString())
//                obj.put("city", "City-${i%100}".toString())
//                obj.put("address", "111 Main Blvd")
//                obj.put("firstName", "Marco-${i}".toString())
//                obj.put("lastName", "Polo-${i}".toString())
//                try {
//                    itemService.pushItemForBulkUpdate(structureHolder.getStructure().getId(), obj, i%2 == 0 ? hometown : visiting)
//                } catch (Exception e) {
//                    // FIXME: how to handle this, we will not know where we had issues.. we could capture all the ones that errored out
//                    //  and pass them back - or we fail fast and just respond with the id or some other identifiable info for debugging
//                    throw new RuntimeException(e)
//                }
//            }
//        } finally {
//            itemService.flushAndCloseBulkUpdate(structureHolder.getStructure().getId())
//        }
//
//        Thread.sleep(10000)
//
//        SearchHits hits = itemService.getAll(structureHolder.getStructure().getId(), 100, 0, hometown)
//        Assertions.assertEquals(100, hits.getHits().length)
//        Assertions.assertEquals(3000, hits.getTotalHits().value)
//
//        try {
//            // run through deletion
//            DeleteByQueryRequest deleteByQueryRequest = new DeleteByQueryRequest(structureHolder.getStructure().getItemIndex())
//            deleteByQueryRequest.setBatchSize(3000)
//            deleteByQueryRequest.setQuery(new MatchAllQueryBuilder())
//            BulkByScrollResponse response = highLevelClient.deleteByQuery(deleteByQueryRequest, RequestOptions.DEFAULT)
//
//            Assertions.assertEquals(response.getStatus().getDeleted(), 6000)
//
//            Thread.sleep(10000)
//        }catch(Exception e){
//            throw e
//        }finally{
//            // hopefully all went well and we can delete
//            structureService.delete(structureHolder.getStructure().getId())
//        }
//    }
//
//    StructureHolder getPerson(){
//        StructureHolder structureHolder = new StructureHolder()
//        Structure structure = new Structure()
//        structure.setName("Person-" + System.currentTimeMillis())
//        structure.setNamespace("org.kinotic.")
//        structure.setDescription("Defines an Person")
//
//        Optional<Trait> keywordStringOptional = traitService.getTraitByName("KeywordString")
//
//        ArrayList<TraitHolder> traits = new ArrayList<>()
//        traits.add(new TraitHolder(0, "state", keywordStringOptional.get()))
//        traits.add(new TraitHolder(1, "city", keywordStringOptional.get()))
//        traits.add(new TraitHolder(2, "address", keywordStringOptional.get()))
//        traits.add(new TraitHolder(3, "firstName", keywordStringOptional.get()))
//        traits.add(new TraitHolder(4, "lastName", keywordStringOptional.get()))
//
//        structureHolder.setTraits(traits)
//        structureHolder.setStructure(structure)
//
//        structureHolder = structureService.save(structureHolder)
//        structureService.publish(structureHolder.getStructure().getId())
//        structureHolder
//    }
}
