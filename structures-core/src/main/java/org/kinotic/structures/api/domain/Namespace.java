package org.kinotic.structures.api.domain;

import lombok.*;
import lombok.experimental.Accessors;
import org.kinotic.continuum.api.Identifiable;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import java.util.Date;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@Document(indexName = "namespace")
@Setting(shards = 2, replicas = 2)
public class Namespace implements Identifiable<String> {

    @Id
    private String id;

    @Field(type = FieldType.Text)
    private String description;

    @Field(type=FieldType.Date)
    private Date updated = null;

    @Field(type = FieldType.Boolean)
    private boolean federatedGraphQl = false;

    public Namespace(String id, String description) {
        this.id = id;
        this.description = description;
    }
}
