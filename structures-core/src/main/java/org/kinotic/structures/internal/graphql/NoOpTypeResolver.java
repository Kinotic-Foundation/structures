package org.kinotic.structures.internal.graphql;

import graphql.TypeResolutionEnvironment;
import graphql.schema.GraphQLObjectType;
import graphql.schema.TypeResolver;

/**
 * A no-op type resolver that always returns null
 * This is used since we do not actually use the GraphQL api to execute operations, only to define and parse the schema.
 * And Type Resolvers are required to be defined for interfaces and unions.
 * Created by Navíd Mitchell 🤪 on 2/4/24.
 */
public class NoOpTypeResolver implements TypeResolver {
    @Override
    public GraphQLObjectType getType(TypeResolutionEnvironment env) {
        return null;
    }
}
