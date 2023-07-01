package org.kinotic.structures.entity;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.elasticsearch._types.Refresh;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.core.UpdateRequest;
import co.elastic.clients.elasticsearch.core.UpdateResponse;
import co.elastic.clients.util.BinaryData;
import co.elastic.clients.util.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kinotic.structures.ElasticsearchTestBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.nio.charset.StandardCharsets;

/**
 * Created by Navíd Mitchell 🤪on 7/1/23.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ElasticBinaryDataTests extends ElasticsearchTestBase {

    @Autowired
    private ElasticsearchAsyncClient esAsyncClient;

    //@Test
    public void testBinaryDataIngestion() throws Exception {
        String index = "binary-ingestion-test";
        String id = "foo-bar";

        BinaryData data = BinaryData.of("{\"foo\":\"bar\"}".getBytes(), ContentType.APPLICATION_JSON);

        UpdateResponse<BinaryData> response = esAsyncClient.update(UpdateRequest.of(u -> u
                .index(index)
                .id(id)
                .doc(data)
                .docAsUpsert(true)
                .refresh(Refresh.True)), BinaryData.class).get();

        Assertions.assertEquals(response.result().toString(), "Created");

        GetResponse<BinaryData> getResponse =
                esAsyncClient.get(g -> g.index(index)
                                        .id(id)
                        ,BinaryData.class
                ).get();

        Assertions.assertEquals(id, getResponse.id());
        Assertions.assertEquals(
                "{\"foo\":\"bar\"}",
                new String(getResponse.source().asByteBuffer().array(), StandardCharsets.UTF_8)
        );
    }

}
