package org.kinotic.structures.item;

import org.junit.jupiter.api.extension.ExtendWith;
import org.kinotic.structures.ElasticsearchTestBase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class BulkUpdateTests extends ElasticsearchTestBase {

//    @Autowired
//    private ItemServiceInternal itemService;
//    @Autowired
//    private StructureTestHelper structureTestHelper;
//    @Autowired
//    private StructureServiceInternal structureService;
//    @Autowired
//    private RestHighLevelClient highLevelClient;
//
//    @Test
//    public void bulkUpdateTest() throws Exception {
//
//        Structure structure = structureTestHelper.getDeviceStructure();
//        itemService.requestBulkUpdatesForStructure(structure.getId());
//
//        for(int i = 0; i < 6000; i++){
//            TypeCheckMap obj = new TypeCheckMap();
//            obj.put("id", String.valueOf(i));
//            obj.put("ip", "192.168.0.123");
//            obj.put("mac", "000000000001");
//            obj.put("label", "Device-"+i);
//            obj.put("description", "This is a description for device "+i);
//            itemService.pushItemForBulkUpdate(structure.getId(), obj, null);
//        }
//
//        Thread.sleep(10000);// give time for ES to flush the new item
//
//        long firstCount = itemService.count(structure.getId(), null);
//        Assert.isTrue(firstCount == 5000, "Bulk upload didn't complete properly before flush");
//
//        itemService.flushAndCloseBulkUpdate(structure.getId());
//
//        Thread.sleep(5000);
//
//        long secondCount = itemService.count(structure.getId(), null);
//        Assert.isTrue(secondCount == 6000, "Bulk upload didn't complete properly after flush");
//
//        // run through deletion
//        DeleteByQueryRequest deleteByQueryRequest = new DeleteByQueryRequest(structure.getItemIndex());
//        deleteByQueryRequest.setBatchSize(3000);
//        deleteByQueryRequest.setQuery(new MatchAllQueryBuilder());
//        BulkByScrollResponse response = highLevelClient.deleteByQuery(deleteByQueryRequest, RequestOptions.DEFAULT);
//
//        Assertions.assertEquals(response.getStatus().getDeleted(), 6000);
//
//        Thread.sleep(10000);
//
//        structureService.delete(structure.getId());
//
//    }

}
