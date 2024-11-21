package org.kinotic.structures.internal.idl.converters.graphql;

import graphql.schema.GraphQLAppliedDirective;
import graphql.schema.GraphQLType;
import graphql.schema.GraphQLUnionType;
import graphql.schema.TypeResolver;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.tuple.Pair;
import org.kinotic.structures.api.config.StructuresProperties;
import org.kinotic.structures.internal.idl.converters.common.BaseConversionState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Navíd Mitchell 🤪 on 5/14/23.
 */
@Getter
@Setter
@Accessors(chain = true)
public class GqlConversionState extends BaseConversionState {

    private final Map<String, GraphQLType> referencedTypes = new HashMap<>();

    /**
     * Union types keyed by name with the value being a pair of the GraphQLUnionType and the TypeResolver
     */
    private Map<String, Pair<GraphQLUnionType, TypeResolver>> unionTypes = new HashMap<>();

    public GqlConversionState(StructuresProperties structuresProperties) {
        super(structuresProperties);
    }
}
