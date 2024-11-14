package org.kinotic.structures.internal.idl.converters.graphql;

import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLUnionType;
import org.apache.commons.lang3.tuple.Pair;
import org.kinotic.continuum.idl.api.converter.C3ConversionContext;
import org.kinotic.continuum.idl.api.converter.C3TypeConverter;
import org.kinotic.continuum.idl.api.converter.Cacheable;
import org.kinotic.continuum.idl.api.schema.C3Type;
import org.kinotic.continuum.idl.api.schema.ObjectC3Type;
import org.kinotic.continuum.idl.api.schema.UnionC3Type;
import org.kinotic.structures.api.domain.idl.decorators.DiscriminatorDecorator;
import org.kinotic.structures.internal.endpoints.graphql.DiscriminatorTypeResolver;
import org.kinotic.structures.internal.endpoints.graphql.NoOpTypeResolver;

import static graphql.schema.GraphQLTypeReference.typeRef;

/**
 * Created by Navíd Mitchell 🤪 on 5/27/23.
 */
public class UnionC3TypeToGql implements C3TypeConverter<GqlTypeHolder, UnionC3Type, GqlConversionState>, Cacheable {

    @Override
    public GqlTypeHolder convert(UnionC3Type c3Type,
                                 C3ConversionContext<GqlTypeHolder, GqlConversionState> conversionContext) {

        GraphQLUnionType.Builder unionBuilder = GraphQLUnionType.newUnionType();
        unionBuilder.name(c3Type.getName());

        for(ObjectC3Type objectC3Type : c3Type.getTypes()){
            GqlTypeHolder typeHolder = conversionContext.convert(objectC3Type);
            if(typeHolder.getOutputType() instanceof GraphQLObjectType objectType){
                String objectTypeName = objectType.getName();

                conversionContext.state().getReferencedTypes().put(objectTypeName, objectType);
                unionBuilder.possibleType(typeRef(objectTypeName));
            }else{
                throw new RuntimeException("Union types can only contain Object types");
            }
        }

        GraphQLUnionType unionType = unionBuilder.build();

        // For union types the DiscriminatorDecorator can be on the type so capture that
        DiscriminatorDecorator discriminatorDecorator = c3Type.findDecorator(DiscriminatorDecorator.class);
        if(discriminatorDecorator != null && discriminatorDecorator.getPropertyName() != null){
            conversionContext.state().getUnionTypes().put(unionType.getName(),
                                                          Pair.of(unionType, new DiscriminatorTypeResolver(discriminatorDecorator.getPropertyName())));
        }else{
            //  we set a no op resolver and if this is an Object property that has a Discriminator that will take precedence
            conversionContext.state().getUnionTypes().put(unionType.getName(),
                                                          Pair.of(unionType, new NoOpTypeResolver()));
        }

        return new GqlTypeHolder(null, unionType);
    }

    @Override
    public boolean supports(C3Type c3Type) {
        return c3Type instanceof UnionC3Type;
    }

}
