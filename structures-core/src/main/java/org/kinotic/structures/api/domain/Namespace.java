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
@RequiredArgsConstructor
@Document(indexName = "namespace")
@Setting(shards = 2, replicas = 2)
public class Namespace implements Identifiable<String> {

    @Id
    @Field(type = FieldType.Keyword)
    @NonNull
    private String id;

    @Field(type = FieldType.Text)
    @NonNull
    private String description;

    @Field(type=FieldType.Date)
    private Date updated = null;

}
