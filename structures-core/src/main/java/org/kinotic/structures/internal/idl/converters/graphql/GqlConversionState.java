package org.kinotic.structures.internal.idl.converters.graphql;

import graphql.schema.GraphQLAppliedDirective;
import graphql.schema.GraphQLType;
import graphql.schema.GraphQLUnionType;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
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

    private List<GraphQLAppliedDirective> outputTypeDirectives = new ArrayList<>();

    private List<GraphQLAppliedDirective> inputTypeDirectives = new ArrayList<>();

    private List<GraphQLUnionType> unionTypes = new ArrayList<>();

    public GqlConversionState(StructuresProperties structuresProperties) {
        super(structuresProperties);
    }
}
