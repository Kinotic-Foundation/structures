package org.kinotic.structures.api.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.kinotic.continuum.api.Identifiable;
import org.kinotic.continuum.idl.api.schema.FunctionDefinition;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import java.util.List;

/**
 * Provides Metadata that represents Named Queries for a Namespace
 * Created by Navíd Mitchell 🤪 on 3/18/24.
 */
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@Document(indexName = "named_query_service_definition")
@Setting(shards = 2, replicas = 2)
public class NamedQueriesDefinition implements Identifiable<String> {

    @Id
    private String id = null;

    @Field(type = FieldType.Keyword)
    private String namespace = null;

    @Field(type = FieldType.Keyword)
    private String structure = null;

    @Field(type = FieldType.Flattened)
    private List<FunctionDefinition> namedQueries = null;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        NamedQueriesDefinition namedQueriesDefinition = (NamedQueriesDefinition) o;

        return new EqualsBuilder().append(id, namedQueriesDefinition.id).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("namespace", namespace)
                .append("structure", structure)
                .toString();
    }
}
