package org.kinotic.structures.api.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.kinotic.continuum.idl.api.schema.ServiceDefinition;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import java.util.Date;

/**
 * Provides Metadata that represents Named Queries for a namespace
 * Created by Navíd Mitchell 🤪 on 3/18/24.
 */
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@Document(indexName = "service")
@Setting(shards = 2, replicas = 2)
public class QueryService {

    @Id
    private String id = null; // do not ever set, system managed

    @Field(type = FieldType.Keyword)
    private String name = null;

    @Field(type = FieldType.Keyword)
    private String namespace = null;

    @Field(type = FieldType.Text)
    private String description = null;

    @Field(type = FieldType.Flattened)
    private ServiceDefinition serviceDefinition = null;

    @Field(type=FieldType.Date)
    private Date updated = null; // do not ever set, system managed

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        QueryService queryService = (QueryService) o;

        return new EqualsBuilder().append(id, queryService.id).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("name", name)
                .append("namespace", namespace)
                .append("description", description)
                .toString();
    }
}
