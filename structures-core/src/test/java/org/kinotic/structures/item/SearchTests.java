/*
 *
 * Copyright 2008-2021 Kinotic and the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kinotic.structures.item;

import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kinotic.structures.api.domain.*;
import org.kinotic.structures.api.services.ItemService;
import org.kinotic.structures.api.services.StructureService;
import org.kinotic.structures.api.services.TraitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class SearchTests {

    @Autowired
    private ItemService itemService;
    @Autowired
    private TraitService traitService;
    @Autowired
    private StructureService structureService;

    @BeforeEach
    public void init() throws IOException, PermenentTraitException, AlreadyExistsException {
        Optional<Trait> ipOptional = traitService.getTraitByName("VpnIp");
        if(ipOptional.isEmpty()){
            Trait temp = new Trait();
            temp.setName("VpnIp");
            temp.setDescribeTrait("VpnIp address that the devices should be provided on the VLAN.");
            temp.setSchema("{ \"type\": \"string\", \"format\": \"ipv4\" }");
            temp.setEsSchema("{ \"type\": \"ip\" }");
            temp.setRequired(true);
            traitService.save(temp);
        }
    }

    @Test
    public void tryCreateFiveItemsAndGetAll() throws Exception {

        Structure structure = new Structure();
        structure.setPrimaryKey(new LinkedList<String>(Collections.singleton("id")));
        structure.setId("Search1-" + System.currentTimeMillis());
        structure.setDescription("Defines an Item1");


        Optional<Trait> vpnIpOptional = traitService.getTraitByName("VpnIp");
        Optional<Trait> ipOptional = traitService.getTraitByName("Ip");
        Optional<Trait> macOptional = traitService.getTraitByName("Mac");

        structure.getTraits().put("vpnIp", vpnIpOptional.get());
        structure.getTraits().put("ip", ipOptional.get());
        structure.getTraits().put("mac", macOptional.get());
        // should also get createdTime, updateTime, and deleted by default

        structureService.save(structure);
        structureService.publish(structure.getId());

        int index = 0;
        while (index < 5) {

            // now we can create an item with the above fields
            TypeCheckMap obj = new TypeCheckMap();
            obj.put("vpnIp", "10.0." + index + ".11");
            obj.put("ip", "192.0." + index + ".11");
            obj.put("mac", "00000000000" + index);

            TypeCheckMap saved = itemService.upsertItem(structure.getId(), obj);

            Thread.sleep(1000);// give time for ES to flush the new item

            index++;
        }


        final SearchHits hits = itemService.getAll(structure.getId(), 100, 0);

        for (SearchHit item : hits.getHits()) {
            if (item != null) {
                itemService.delete(structure.getId(), item.getId());

                Thread.sleep(1000);
            }

        }
        
        structureService.delete(structure.getId());

        if (hits.getTotalHits().value != 5) {
            throw new IllegalStateException("Added 5 Items and expected 5 items returned by getAll() - got " + hits.getTotalHits().value);
        }


    }

    @Test
    public void createFiveItemsAndSearchExact() throws Exception {

        Structure structure = new Structure();
        structure.setPrimaryKey(new LinkedList<String>(Collections.singleton("id")));
        structure.setId("Search2-" + System.currentTimeMillis());
        structure.setDescription("Defines an Item1");


        Optional<Trait> ipOptional = traitService.getTraitByName("Ip");
        Optional<Trait> macOptional = traitService.getTraitByName("Mac");

        structure.getTraits().put("ip", ipOptional.get());
        structure.getTraits().put("mac", macOptional.get());
        // should also get createdTime, updateTime, and deleted by default

        structureService.save(structure);
        structureService.publish(structure.getId());

        int index = 0;
        while (index < 5) {

            // now we can create an item with the above fields
            TypeCheckMap obj = new TypeCheckMap();
            obj.put("ip", "192.0." + index + ".11");
            obj.put("mac", "00000000000" + index);

            TypeCheckMap saved = itemService.upsertItem(structure.getId(), obj);

            Thread.sleep(1000);// give time for ES to flush the new item

            index++;
        }


        final SearchHits zeroDotEleven = itemService.searchTerms(structure.getId(), 100, 0, "ip", "192.0.0.11");

        final SearchHits twoMacs = itemService.searchTerms(structure.getId(), 100, 0, "mac", "000000000000", "000000000001");

        final SearchHits all = itemService.getAll(structure.getId(), 100, 0);

        for (SearchHit item : all.getHits()) {
            if (item != null) {
                itemService.delete(structure.getId(), item.getId());

                Thread.sleep(1000);
            }

        }


        structureService.delete(structure.getId());

        if (zeroDotEleven.getTotalHits().value != 1) {
            throw new IllegalStateException("Queried IP = 192.0.0.11, expected 1 but got " + zeroDotEleven.getTotalHits().value);
        }


        if (twoMacs.getTotalHits().value != 2) {
            throw new IllegalStateException("Queried MAC = 000000000000 and 000000000001, expected 2 but got " + twoMacs.getTotalHits().value);
        }


        if (all.getTotalHits().value != 5) {
            throw new IllegalStateException("Added 5 Items and expected 5 items returned by getAll() - got " + all.getTotalHits().value);
        }

    }

    @Test
    public void createFiveItemsAndSearchForText() throws Exception {

        Structure structure = new Structure();
        structure.setPrimaryKey(new LinkedList<String>(Collections.singleton("id")));
        structure.setId("Search3-" + System.currentTimeMillis());
        structure.setDescription("Defines an Item1");

        Trait vpnIpOptional = traitService.getTraitByName("VpnIp").get();
        vpnIpOptional.setRequired(false);
        Optional<Trait> ipOptional = traitService.getTraitByName("Ip");
        Optional<Trait> macOptional = traitService.getTraitByName("Mac");
        Optional<Trait> descriptionOptional = traitService.getTraitByName("TextString");

        structure.getTraits().put("vpnIp", vpnIpOptional);
        structure.getTraits().put("ip", ipOptional.get());
        structure.getTraits().put("mac", macOptional.get());
        structure.getTraits().put("description", descriptionOptional.get());
        // should also get createdTime, updateTime, and deleted by default

        structureService.save(structure);
        structureService.publish(structure.getId());

        int index = 0;
        while (index < 5) {

            // now we can create an item with the above fields
            TypeCheckMap obj = new TypeCheckMap();
            if(index % 2 == 0) obj.put("vpnIp", "172.16." + index + ".11");
            obj.put("ip", "192.0." + index + ".11");
            obj.put("mac", "00000000000" + index);
            if (index == 0) {
                obj.put("description", "zero one two three four five six seven eight nine ten");
            } else if (index == 1) {
                obj.put("description", "ten eleven twelve thirteen fourteen fifteen sixteen seventeen eighteen nineteen twenty");
            } else if (index == 2) {
                obj.put("description", "twenty twenty-one twenty-two twenty-three twenty-four twenty-five twenty-six twenty-seven twenty-eight twenty-nine thirty");
            } else if (index == 3) {
                obj.put("description", "thirty thirty-one thirty-two thirty-three thirty-four thirty-five thirty-six thirty-seven thirty-eight thirty-nine forty");
            } else if (index == 4) {
                obj.put("description", "forty forty-one forty-two forty-three forty-four forty-five forty-six forty-seven forty-eight forty-nine fifty");
            }


            TypeCheckMap saved = itemService.upsertItem(structure.getId(), obj);

            Thread.sleep(1000);// give time for ES to flush the new item

            index++;
        }


        final SearchHits oneItemUsingText = itemService.searchFullText(structure.getId(), 100, 0, "zero", "description");

        final SearchHits twoItemsUsingOr = itemService.searchFullText(structure.getId(), 100, 0, "zero|fifty", "description");

        final SearchHits twoItemsUsingSameText = itemService.searchFullText(structure.getId(), 100, 0, "twenty", "description");

        final SearchHits all = itemService.getAll(structure.getId(), 100, 0);

        for (SearchHit item : all.getHits()) {
            if (item != null) {
                itemService.delete(structure.getId(), item.getId());

                Thread.sleep(1000);
            }

        }


        structureService.delete(structure.getId());

        if (oneItemUsingText.getTotalHits().value != 1) {
            throw new IllegalStateException("Queried Description and zero, expected 1 but got " + oneItemUsingText.getTotalHits().value);
        }


        if (twoItemsUsingOr.getTotalHits().value != 2) {
            throw new IllegalStateException("Queried Description and zero|fifty, expected 2 but got " + twoItemsUsingOr.getTotalHits().value);
        }


        if (twoItemsUsingSameText.getTotalHits().value != 2) {
            throw new IllegalStateException("Queried Description and twenty, expected 2 but got " + twoItemsUsingSameText.getTotalHits().value);
        }


        if (all.getTotalHits().value != 5) {
            throw new IllegalStateException("Added 5 Items and expected 5 items returned by getAll() - got " + all.getTotalHits().value);
        }

    }

    @Test
    public void createFiveItemsAndSearchUsingLuceneSyntax() throws Exception {

        Structure structure = new Structure();
        structure.setPrimaryKey(new LinkedList<String>(Collections.singleton("id")));
        structure.setId("Search4-" + System.currentTimeMillis());
        structure.setDescription("Defines an Item1");

        Optional<Trait> vpnIpOptional = traitService.getTraitByName("VpnIp");
        Optional<Trait> ipOptional = traitService.getTraitByName("Ip");
        Optional<Trait> macOptional = traitService.getTraitByName("Mac");
        Trait descriptionOptional = traitService.getTraitByName("TextString").get();
        descriptionOptional.setRequired(false);

        structure.getTraits().put("vpnIp", vpnIpOptional.get());
        structure.getTraits().put("ip", ipOptional.get());
        structure.getTraits().put("mac", macOptional.get());
        structure.getTraits().put("description", descriptionOptional);
        // should also get createdTime, updateTime, and deleted by default

        structureService.save(structure);
        structureService.publish(structure.getId());

        int index = 0;
        while (index < 5) {

            // now we can create an item with the above fields
            TypeCheckMap obj = new TypeCheckMap();
            obj.put("vpnIp", "172.16." + index + ".11");
            obj.put("ip", "192.0." + index + ".11");
            obj.put("mac", "00000000000" + index);
            if (index == 0) {
                obj.put("description", "zero one two three four five six seven eight nine ten");
            } else if (index == 1) {
                obj.put("description", "ten eleven twelve thirteen fourteen fifteen sixteen seventeen eighteen nineteen twenty");
            } else if (index == 2) {
                obj.put("description", "twenty twenty-one twenty-two twenty-three twenty-four twenty-five twenty-six twenty-seven twenty-eight twenty-nine thirty");
            } else if (index == 3) {
                obj.put("description", "thirty thirty-one thirty-two thirty-three thirty-four thirty-five thirty-six thirty-seven thirty-eight thirty-nine forty");
            } else if (index == 4) {
                obj.put("description", "forty forty-one forty-two forty-three forty-four forty-five forty-six forty-seven forty-eight forty-nine fifty");
            }


            TypeCheckMap saved = itemService.upsertItem(structure.getId(), obj);

            Thread.sleep(1000);// give time for ES to flush the new item

            index++;
        }


        SearchHits oneRetured = itemService.search(structure.getId(), "(ip:192.0.0.11 OR ip:192.0.1.11) AND (mac:000000000003 OR mac:000000000000)", 100, 0);

        final SearchHits all = itemService.getAll(structure.getId(), 100, 0);

        for (SearchHit item : all.getHits()) {
            if (item != null) {
                itemService.delete(structure.getId(), item.getId());

                Thread.sleep(1000);
            }

        }


        structureService.delete(structure.getId());

        if (oneRetured.getTotalHits().value != 1) {
            throw new IllegalStateException("Queried ((ip:192.0.0.11 OR ip:192.0.1.11) AND (mac:000000000003 OR mac:000000000000)), expected 1 - got " + oneRetured.getTotalHits().value);
        }


        if (all.getTotalHits().value != 5) {
            throw new IllegalStateException("Added 5 Items and expected 5 items returned by getAll() - got " + all.getTotalHits().value);
        }

    }

    @Test
    public void tryCreate5ItemsThenDelete2AndPerformGetAll() throws Exception {

        Structure structure = new Structure();
        structure.setPrimaryKey(new LinkedList<String>(Collections.singleton("id")));
        structure.setId("Search5-" + System.currentTimeMillis());
        structure.setDescription("Defines an Item1");


        Optional<Trait> ipOptional = traitService.getTraitByName("Ip");
        Optional<Trait> macOptional = traitService.getTraitByName("Mac");

        structure.getTraits().put("ip", ipOptional.get());
        structure.getTraits().put("mac", macOptional.get());
        // should also get createdTime, updateTime, and deleted by default

        structureService.save(structure);
        structureService.publish(structure.getId());

        int index = 0;
        String delete1Id = "";
        String delete2Id = "";
        while (index < 5) {

            // now we can create an item with the above fields
            TypeCheckMap obj = new TypeCheckMap();
            obj.put("ip", "192.0." + index + ".11");
            obj.put("mac", "00000000000" + index);

            TypeCheckMap saved = itemService.upsertItem(structure.getId(), obj);

            if (index == 1) {
                delete1Id = saved.getString("id");
            } else if (index == 3) {
                delete2Id = saved.getString("id");
            }


            Thread.sleep(1000);// give time for ES to flush the new item

            index++;
        }


        itemService.delete(structure.getId(), delete1Id);
        itemService.delete(structure.getId(), delete2Id);
        Thread.sleep(1000);

        final SearchHits hits = itemService.getAll(structure.getId(), 100, 0);

        for (SearchHit item : hits.getHits()) {
            if (item != null) {
                itemService.delete(structure.getId(), item.getId());

                Thread.sleep(1000);
            }

        }


        structureService.delete(structure.getId());

        if (hits.getTotalHits().value != 3) {
            throw new IllegalStateException("Added 5 Items, then deleted 2. Expected 3 items returned by getAll() - got " + hits.getTotalHits().value);
        }

    }

    @Test
    public void tryCreate5ItemsThenDelete2AndPerformSearchExact() throws Exception {

        Structure structure = new Structure();
        structure.setPrimaryKey(new LinkedList<String>(Collections.singleton("id")));
        structure.setId("Search6-" + System.currentTimeMillis());
        structure.setDescription("Defines an Item1");


        Optional<Trait> vpnIpOptional = traitService.getTraitByName("VpnIp");
        Optional<Trait> ipOptional = traitService.getTraitByName("Ip");
        Optional<Trait> macOptional = traitService.getTraitByName("Mac");

        structure.getTraits().put("vpnIp", vpnIpOptional.get());
        structure.getTraits().put("ip", ipOptional.get());
        structure.getTraits().put("mac", macOptional.get());
        // should also get createdTime, updateTime, and deleted by default

        structureService.save(structure);
        structureService.publish(structure.getId());

        int index = 0;
        String delete1Id = "";
        String delete2Id = "";
        while (index < 5) {

            // now we can create an item with the above fields
            TypeCheckMap obj = new TypeCheckMap();
            obj.put("vpnIp", "172.16." + index + ".11");
            obj.put("ip", "192.0." + index + ".11");
            obj.put("mac", "00000000000" + index);

            TypeCheckMap saved = itemService.upsertItem(structure.getId(), obj);

            if (index == 1) {
                delete1Id = saved.getString("id");
            } else if (index == 3) {
                delete2Id = saved.getString("id");
            }


            Thread.sleep(1000);// give time for ES to flush the new item

            index++;
        }


        itemService.delete(structure.getId(), delete1Id);
        itemService.delete(structure.getId(), delete2Id);
        Thread.sleep(1000);

        final SearchHits oneDotEleven = itemService.searchTerms(structure.getId(), 100, 0, "ip", "192.0.1.11");

        final SearchHits oneMacs = itemService.searchTerms(structure.getId(), 100, 0, "mac", "000000000003", "000000000004");

        final SearchHits all = itemService.getAll(structure.getId(), 100, 0);

        for (SearchHit item : all.getHits()) {
            if (item != null) {
                itemService.delete(structure.getId(), item.getId());

                Thread.sleep(1000);
            }

        }


        structureService.delete(structure.getId());

        if (oneDotEleven.getTotalHits().value != 0) {
            throw new IllegalStateException("Queried IP = 192.0.1.11, was deleted and expected 0 but got " + oneDotEleven.getTotalHits().value);
        }


        if (oneMacs.getTotalHits().value != 1) {
            throw new IllegalStateException("Queried MAC = 000000000003 and 000000000004, 3 was deleted and so expected 1 but got " + oneMacs.getTotalHits().value);
        }


        if (all.getTotalHits().value != 3) {
            throw new IllegalStateException("Added 5 Items, then deleted 2. Expected 3 items returned by getAll() - got " + all.getTotalHits().value);
        }

    }

    @Test
    public void tryCreate5ItemsThenDelete2AndPerformSearchText() throws Exception {

        Structure structure = new Structure();
        structure.setPrimaryKey(new LinkedList<String>(Collections.singleton("id")));
        structure.setId("Search7-" + System.currentTimeMillis());
        structure.setDescription("Defines an Item1");

        Optional<Trait> ipOptional = traitService.getTraitByName("Ip");
        Optional<Trait> macOptional = traitService.getTraitByName("Mac");
        Trait descriptionOptional = traitService.getTraitByName("TextString").get();
        descriptionOptional.setRequired(false);

        structure.getTraits().put("ip", ipOptional.get());
        structure.getTraits().put("mac", macOptional.get());
        structure.getTraits().put("description", descriptionOptional);
        // should also get createdTime, updateTime, and deleted by default

        structureService.save(structure);
        structureService.publish(structure.getId());

        int index = 0;
        String delete1Id = "";
        String delete2Id = "";
        while (index < 5) {

            //NOTE: when ES destructures the data, it must break at - and space.. so that thrity-one becomes thirty and one
            // if you search over thirteen you get what you expect, but this is just how to structure the query against
            // the data.

            // now we can create an item with the above fields
            TypeCheckMap obj = new TypeCheckMap();
            obj.put("ip", "192.0." + index + ".11");
            obj.put("mac", "00000000000" + index);
            if (index == 0) {
                obj.put("description", "zero one two three four five six seven eight nine ten");
            } else if (index == 1) {
                obj.put("description", "ten eleven twelve thirteen fourteen fifteen sixteen seventeen eighteen nineteen twenty");
            } else if (index == 2) {
                obj.put("description", "twenty twenty-one twenty-two twenty-three twenty-four twenty-five twenty-six twenty-seven twenty-eight twenty-nine thirty");
            } else if (index == 3) {
                obj.put("description", "thirty thirty-one thirty-two thirty-three thirty-four thirty-five thirty-six thirty-seven thirty-eight thirty-nine forty");
            } else if (index == 4) {
                obj.put("description", "forty forty-one forty-two forty-three forty-four forty-five forty-six forty-seven forty-eight forty-nine fifty");
            }


            TypeCheckMap saved = itemService.upsertItem(structure.getId(), obj);

            if (index == 1) {
                delete1Id = saved.getString("id");
            } else if (index == 3) {
                delete2Id = saved.getString("id");
            }


            Thread.sleep(1000);// give time for ES to flush the new item

            index++;
        }


        itemService.delete(structure.getId(), delete1Id);
        itemService.delete(structure.getId(), delete2Id);
        Thread.sleep(2000);

        final SearchHits zeroItemUsingText = itemService.searchFullText(structure.getId(), 100, 0, "thirteen", "description");

        final SearchHits oneItemsUsingSameText = itemService.searchFullText(structure.getId(), 100, 0, "twenty", "description");

        final SearchHits oneItemsUsingOr = itemService.searchFullText(structure.getId(), 100, 0, "thirty|thirteen", "description");

        final SearchHits all = itemService.getAll(structure.getId(), 100, 0);

        for (SearchHit item : all.getHits()) {
            if (item != null) {
                itemService.delete(structure.getId(), item.getId());

                Thread.sleep(1000);
            }

        }


        structureService.delete(structure.getId());

        if (zeroItemUsingText.getTotalHits().value != 0) {
            throw new IllegalStateException("Queried Description and thirty-one, but was deleted, expected 0 but got " + zeroItemUsingText.getTotalHits().value);
        }


        if (oneItemsUsingSameText.getTotalHits().value != 1) {
            throw new IllegalStateException("Queried Description and twenty, one deleted, expected 1 but got " + oneItemsUsingSameText.getTotalHits().value);
        }


        if (oneItemsUsingOr.getTotalHits().value != 1) {
            throw new IllegalStateException("Queried Description and twenty|thirty-one, one was deleted, expected 1 but got " + oneItemsUsingOr.getTotalHits().value);
        }


        if (all.getTotalHits().value != 3) {
            throw new IllegalStateException("Added 5 Items, then deleted 2. Expected 3 items returned by getAll() - got " + all.getTotalHits().value);
        }

    }

    @Test
    public void tryCreate5ItemsThenDelete2AndPerformSearchLucene() throws Exception {

        Structure structure = new Structure();
        structure.setPrimaryKey(new LinkedList<String>(Collections.singleton("id")));
        structure.setId("Search8-" + System.currentTimeMillis());
        structure.setDescription("Defines an Item1");

        Optional<Trait> vpnIpOptional = traitService.getTraitByName("VpnIp");
        Optional<Trait> ipOptional = traitService.getTraitByName("Ip");
        Optional<Trait> macOptional = traitService.getTraitByName("Mac");
        Trait descriptionOptional = traitService.getTraitByName("TextString").get();
        descriptionOptional.setRequired(false);

        structure.getTraits().put("vpnIp", vpnIpOptional.get());
        structure.getTraits().put("ip", ipOptional.get());
        structure.getTraits().put("mac", macOptional.get());
        structure.getTraits().put("description", descriptionOptional);
        // should also get createdTime, updateTime, and deleted by default

        structureService.save(structure);
        structureService.publish(structure.getId());

        int index = 0;
        String delete1Id = "";
        String delete2Id = "";
        while (index < 5) {

            // now we can create an item with the above fields
            TypeCheckMap obj = new TypeCheckMap();
            obj.put("vpnIp", "10.0." + index + ".11");
            obj.put("ip", "192.0." + index + ".11");
            obj.put("mac", "00000000000" + index);
            if (index == 0) {
                obj.put("description", "zero one two three four five six seven eight nine ten");
            } else if (index == 1) {
                obj.put("description", "ten eleven twelve thirteen fourteen fifteen sixteen seventeen eighteen nineteen twenty");
            } else if (index == 2) {
                obj.put("description", "twenty twenty-one twenty-two twenty-three twenty-four twenty-five twenty-six twenty-seven twenty-eight twenty-nine thirty");
            } else if (index == 3) {
                obj.put("description", "thirty thirty-one thirty-two thirty-three thirty-four thirty-five thirty-six thirty-seven thirty-eight thirty-nine forty");
            } else if (index == 4) {
                obj.put("description", "forty forty-one forty-two forty-three forty-four forty-five forty-six forty-seven forty-eight forty-nine fifty");
            }


            TypeCheckMap saved = itemService.upsertItem(structure.getId(), obj);

            if (index == 1) {
                delete1Id = saved.getString("id");
            } else if (index == 3) {
                delete2Id = saved.getString("id");
            }


            Thread.sleep(1000);// give time for ES to flush the new item

            index++;
        }


        itemService.delete(structure.getId(), delete1Id);
        itemService.delete(structure.getId(), delete2Id);
        Thread.sleep(1000);

        final SearchHits zeroRetured = itemService.search(structure.getId(), "(ip:192.0.1.11 OR ip:192.0.3.11) AND (mac:000000000001 OR mac:000000000003)", 100, 0);

        final SearchHits all = itemService.getAll(structure.getId(), 100, 0);

        for (SearchHit item : all.getHits()) {
            if (item != null) {
                itemService.delete(structure.getId(), item.getId());

                Thread.sleep(1000);
            }

        }


        structureService.delete(structure.getId());

        if (zeroRetured.getTotalHits().value != 0) {
            throw new IllegalStateException("Queried ((ip:192.0.1.11 OR ip:192.0.3.11) AND (mac:000000000001 OR mac:000000000003)), both were deleted, expected 0 - got " + zeroRetured.getTotalHits().value);
        }


        if (all.getTotalHits().value != 3) {
            throw new IllegalStateException("Added 5 Items, then deleted 2. Expected 3 items returned by getAll() - got " + all.getTotalHits().value);
        }

    }

}
