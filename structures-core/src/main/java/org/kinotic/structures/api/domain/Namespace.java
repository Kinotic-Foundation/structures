package org.kinotic.structures.api.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.kinotic.continuum.api.Identifiable;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.util.Date;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@Document(indexName = "namespace")
@Setting(shards = 2, replicas = 2)
public class Namespace implements Identifiable<String> {

    @Id
    @Field(type = FieldType.Keyword)
    private String id = null;

    @Field(type = FieldType.Text)
    private String description = null;

    @Field(type=FieldType.Date)
    private Date updated = null;

}
