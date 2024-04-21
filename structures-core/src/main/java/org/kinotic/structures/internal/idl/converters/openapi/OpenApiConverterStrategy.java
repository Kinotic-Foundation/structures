package org.kinotic.structures.internal.idl.converters.openapi;

import io.swagger.v3.oas.models.media.*;
import org.kinotic.continuum.idl.api.converter.C3TypeConverter;
import org.kinotic.continuum.idl.api.converter.C3TypeConverterContainer;
import org.kinotic.continuum.idl.api.converter.IdlConverterStrategy;
import org.kinotic.continuum.idl.api.schema.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Set;

/**
 * Created by Navíd Mitchell 🤪 on 5/14/23.
 */
@Component
public class OpenApiConverterStrategy implements IdlConverterStrategy<Schema<?>, OpenApiConversionState> {

    private static final Schema<?> BOOL = new BooleanSchema();
    private static final Schema<?> BYTE = new NumberSchema().minimum(BigDecimal.valueOf(Byte.MIN_VALUE))
                                                            .maximum(BigDecimal.valueOf(Byte.MAX_VALUE));
    private static final Schema<?> CHAR = new StringSchema().minLength(1).maxLength(1);
    private static final Schema<?> DATE = new StringSchema().format("date-time");
    private static final Schema<?> DOUBLE = new NumberSchema().format("double");
    private static final Schema<?> FLOAT = new NumberSchema().format("float");
    private static final Schema<?> INT = new IntegerSchema().format("int32");
    private static final Schema<?> LONG = new IntegerSchema().format("int64");
    private static final Schema<?> SHORT = new NumberSchema().minimum(BigDecimal.valueOf(Short.MIN_VALUE))
                                                             .maximum(BigDecimal.valueOf(Short.MAX_VALUE));
    private static final Schema<?> STRING = new StringSchema();
    private final Set<C3TypeConverter<Schema<?>, ? extends C3Type, OpenApiConversionState>> converters;

    public OpenApiConverterStrategy() {
        // Basic types
        C3TypeConverterContainer<Schema<?>, OpenApiConversionState> container = new C3TypeConverterContainer<>();
        container.addConverter(BooleanC3Type.class, (c3Type, context) -> BOOL)
                 .addConverter(ByteC3Type.class, (c3Type, context) -> BYTE)
                 .addConverter(CharC3Type.class, (c3Type, context) -> CHAR)
                 .addConverter(DoubleC3Type.class, (c3Type, context) -> DOUBLE)
                 .addConverter(FloatC3Type.class, (c3Type, context) -> FLOAT)
                 .addConverter(IntC3Type.class, (c3Type, context) -> INT)
                 .addConverter(LongC3Type.class, (c3Type, context) -> LONG)
                 .addConverter(ShortC3Type.class, (c3Type, context) -> SHORT)
                 .addConverter(StringC3Type.class, (c3Type, context) -> STRING)
                 .addConverter(DateC3Type.class, (c3Type, context) -> DATE)
                 // Enum Type
                 .addConverter(EnumC3Type.class, (c3Type, context) -> {
                     StringSchema stringSchema = new StringSchema();
                     stringSchema.setEnum(c3Type.getValues());
                     context.state().addReferencedSchema(c3Type.getName(), stringSchema);
                     return new Schema<>().$ref("#/components/schemas/"+c3Type.getName());
                 })
                 // Array Type
                .addConverter(ArrayC3Type.class, (c3Type, context) ->{
                    // byte arrays are a special case per the open api spec
                    // https://swagger.io/docs/specification/data-models/data-types/#string
                    if(c3Type.getContains() instanceof ByteC3Type){
                        return new StringSchema().format("binary");
                    } else {

                        Schema<?> schema = context.convert(c3Type.getContains());

                        if(c3Type.getContains() instanceof ObjectC3Type){
                            ObjectC3Type objectC3Type = (ObjectC3Type) c3Type.getContains();
                            context.state().addReferencedSchema(objectC3Type.getName(), schema);
                            schema = new Schema<>().$ref("#/components/schemas/"+objectC3Type.getName());
                        }

                        return new ArraySchema().items(schema);
                    }
                });

        converters = Set.of(container, new ObjectC3TypeToOpenApi(), new UnionC3TypeToOpenApi());
    }

    @Override
    public Set<C3TypeConverter<Schema<?>, ? extends C3Type, OpenApiConversionState>> converters() {
        return converters;
    }

    @Override
    public OpenApiConversionState initialState() {
        return new OpenApiConversionState();
    }

    @Override
    public boolean shouldCache() {
        return true;
    }

}
