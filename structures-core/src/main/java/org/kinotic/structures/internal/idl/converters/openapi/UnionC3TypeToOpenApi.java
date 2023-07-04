package org.kinotic.structures.internal.idl.converters.openapi;

import io.swagger.v3.oas.models.media.ObjectSchema;
import io.swagger.v3.oas.models.media.Schema;
import org.kinotic.continuum.idl.api.converter.C3ConversionContext;
import org.kinotic.continuum.idl.api.converter.SpecificC3TypeConverter;
import org.kinotic.continuum.idl.api.schema.C3Type;
import org.kinotic.continuum.idl.api.schema.ObjectC3Type;
import org.kinotic.continuum.idl.api.schema.UnionC3Type;

import java.util.Set;

/**
 * {@link UnionC3TypeToOpenApi} is a {@link SpecificC3TypeConverter} that converts a {@link UnionC3Type} to a {@link Schema<?>}.
 * Created by Navíd Mitchell 🤪 on 5/27/23.
 */
public class UnionC3TypeToOpenApi implements SpecificC3TypeConverter<Schema<?>, UnionC3Type, OpenApiConversionState> {

    private static final Set<Class<? extends C3Type>> supports = Set.of(UnionC3Type.class);

    @Override
    public Schema<?> convert(UnionC3Type c3Type,
                             C3ConversionContext<Schema<?>, OpenApiConversionState> conversionContext) {

        ObjectSchema unionSchema = new ObjectSchema();

        for(ObjectC3Type objectC3Type : c3Type.getTypes()){

            Schema<?> objectSchema = conversionContext.convert(objectC3Type);

            conversionContext.state().addReferenceSchema(objectC3Type.getName(), objectSchema);

            String refPath = "#/components/schemas/"+objectC3Type.getName();

            unionSchema.addOneOfItem(new Schema<>().$ref(refPath));
        }

        return unionSchema;
    }

    @Override
    public Set<Class<? extends C3Type>> supports() {
        return supports;
    }
}
