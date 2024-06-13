package org.kinotic.structures.internal.endpoints.graphql;

import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLInputType;
import lombok.RequiredArgsConstructor;
import org.kinotic.continuum.idl.api.schema.FunctionDefinition;
import org.kinotic.continuum.idl.api.schema.ParameterDefinition;
import org.kinotic.structures.api.domain.idl.PageableC3Type;
import org.kinotic.structures.internal.idl.converters.graphql.GqlTypeHolder;

import static graphql.schema.GraphQLArgument.newArgument;
import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;
import static graphql.schema.GraphQLNonNull.nonNull;

/**
 * This class is responsible for creating a {@link GraphQLFieldDefinition} for a query function
 * Created by Navíd Mitchell 🤪 on 6/13/24.
 */
@RequiredArgsConstructor
public class QueryGqlFieldDefinitionFunction implements GqlFieldDefinitionFunction {

    private final FunctionDefinition queryDefinition;

    @Override
    public GraphQLFieldDefinition apply(GqlFieldDefinitionData data) {
        GraphQLFieldDefinition.Builder builder
                = newFieldDefinition().name(queryDefinition.getName() + data.getStructureName());

        GqlTypeHolder retTypeHolder = data.getConverter().convert(queryDefinition.getReturnType());
        builder.type(retTypeHolder.getOutputType());

        for(ParameterDefinition parameter : queryDefinition.getParameters()){

            GraphQLInputType inputType;
            if(!(parameter.getType() instanceof PageableC3Type)) {
                GqlTypeHolder paramType = data.getConverter().convert(parameter.getType());
                inputType = paramType.getInputType();
            }else{
                inputType = data.getPageableReference();
            }
            builder.argument(newArgument().name(parameter.getName())
                                          .type(nonNull(inputType)));
        }

        return builder.build();
    }
}

