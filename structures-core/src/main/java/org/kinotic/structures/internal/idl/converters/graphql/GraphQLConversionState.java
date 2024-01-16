package org.kinotic.structures.internal.idl.converters.graphql;

import graphql.schema.GraphQLAppliedDirective;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.kinotic.structures.internal.idl.converters.common.BaseConversionState;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Navíd Mitchell 🤪 on 5/14/23.
 */
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class GraphQLConversionState extends BaseConversionState {

    private List<GraphQLAppliedDirective> outputTypeDirectives = new ArrayList<>();

    private List<GraphQLAppliedDirective> inputTypeDirectives = new ArrayList<>();
}
