package org.kinotic.structures.internal.endpoints.graphql;

import graphql.TypeResolutionEnvironment;
import graphql.schema.GraphQLObjectType;
import graphql.schema.TypeResolver;
import lombok.RequiredArgsConstructor;

import java.util.Map;

/**
 * This class is used to resolve the type of field based on a discriminator field.
 * Created by Navíd Mitchell 🤪 on 6/18/24.
 */
@RequiredArgsConstructor
public class DiscriminatorTypeResolver implements TypeResolver {
    private final String discriminatorField;

    @Override
    public GraphQLObjectType getType(TypeResolutionEnvironment env) {
        if(env.getObject() instanceof Map){
            //noinspection unchecked
            Object discriminatorValue = ((Map<String, Object>) env.getObject()).get(discriminatorField);
            if(discriminatorValue instanceof String){
                String typeName = discriminatorValue.toString();
                return env.getSchema().getObjectType(typeName);

            }else {
                throw new IllegalStateException("Discriminator field must be a string");
            }
        }else{
            throw new IllegalStateException("DiscriminatorTypeResolver can only be used with Map objects");
        }
    }
}
