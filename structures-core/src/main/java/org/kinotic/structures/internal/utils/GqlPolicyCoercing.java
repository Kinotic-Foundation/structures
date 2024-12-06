package org.kinotic.structures.internal.utils;

import graphql.GraphQLContext;
import graphql.execution.CoercedVariables;
import graphql.language.StringValue;
import graphql.language.Value;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;

import java.util.Locale;

public class GqlPolicyCoercing implements Coercing<String, String> {

    @Override
    public String parseLiteral(Value<?> input, CoercedVariables variables, GraphQLContext graphQLContext, Locale locale) throws CoercingParseLiteralException {
        if (input instanceof StringValue) {
            return ((StringValue) input).getValue();
        }
        throw new CoercingParseLiteralException(
                "Expected a StringValue literal for federation__Policy, but got: " + input.getClass().getName()
        );
    }

    @Override
    public String parseValue(Object input, GraphQLContext graphQLContext, Locale locale) throws CoercingParseValueException {
        if (input instanceof String) {
            return (String) input;
        }
        throw new CoercingParseValueException(
                "Expected a String for federation__Policy input value, but got: " + input.getClass().getName()
        );
    }

    @Override
    public String serialize(Object dataFetcherResult, GraphQLContext graphQLContext, Locale locale) throws CoercingSerializeException {
        if (dataFetcherResult instanceof String) {
            return (String) dataFetcherResult;
        }
        throw new CoercingSerializeException(
                "Expected a String for federation__Policy, but got: " + dataFetcherResult.getClass().getName()
        );
    }
}
