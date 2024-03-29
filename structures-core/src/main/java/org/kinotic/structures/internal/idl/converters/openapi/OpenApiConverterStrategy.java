package org.kinotic.structures.internal.idl.converters.openapi;

import io.swagger.v3.oas.models.media.Schema;
import org.kinotic.continuum.idl.api.converter.SpecificC3TypeConverter;
import org.kinotic.continuum.idl.internal.api.converter.AbstractIdlConverterStrategy;
import org.kinotic.structures.internal.idl.converters.common.DecoratorPreProcessorConverter;
import org.kinotic.structures.internal.idl.converters.openapi.decorators.OpenApiDecoratorMappingProcessor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Navíd Mitchell 🤪 on 5/14/23.
 */
@Component
public class OpenApiConverterStrategy extends AbstractIdlConverterStrategy<Schema<?>, OpenApiConversionState> {

    private final static List<SpecificC3TypeConverter<Schema<?>, ?, OpenApiConversionState>> specificTypeConverters = List.of(
        new PrimitiveC3TypeToOpenApi(),
        new ArrayC3TypeToOpenApi(),
        new DateC3TypeToOpenApi(),
        new UnionC3TypeToOpenApi(),
        new EnumC3TypeToOpenApi(),
        new ObjectC3TypeToOpenApi()
    );

    @SuppressWarnings({"unchecked", "rawtypes"})
    public OpenApiConverterStrategy(List<OpenApiDecoratorMappingProcessor<?>> openApiMappingPreProcessors) {
        super(specificTypeConverters, List.of(new DecoratorPreProcessorConverter(openApiMappingPreProcessors)));
    }

    @Override
    public OpenApiConversionState initialState() {
        return new OpenApiConversionState();
    }

    @Override
    public boolean shouldCache() {
        return true;
    }

    @Override
    protected boolean shouldCheckGenericConvertersFirst() {
        return true;
    }
}
