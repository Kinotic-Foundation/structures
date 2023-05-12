package org.kinotic.structures.internal.idl.converters.elastic;

import co.elastic.clients.elasticsearch._types.mapping.Property;
import org.apache.commons.lang3.Validate;
import org.kinotic.continuum.idl.api.converter.C3ConversionContext;
import org.kinotic.continuum.idl.api.converter.GenericC3TypeConverter;
import org.kinotic.continuum.idl.api.schema.C3Type;
import org.kinotic.continuum.idl.api.schema.decorators.C3Decorator;
import org.kinotic.structures.api.decorators.runtime.MappingContext;
import org.kinotic.structures.api.decorators.runtime.MappingPreProcessor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Navíd Mitchell 🤪 on 5/12/23.
 */
public class MappingPreProcessorConverter implements GenericC3TypeConverter<Property, C3Type, EsConversionState> {

    private final Map<Class<? extends C3Decorator>, MappingPreProcessor<?>> mappingPreProcessors;

    public MappingPreProcessorConverter(List<MappingPreProcessor<?>> mappingPreProcessors) {
        this.mappingPreProcessors = new HashMap<>(mappingPreProcessors.size());

        for(MappingPreProcessor<?> mappingPreProcessor : mappingPreProcessors){

            if(this.mappingPreProcessors.containsKey(mappingPreProcessor.implementsDecorator())){
                throw new IllegalArgumentException("Duplicate MappingPreProcessor for decorator: " + mappingPreProcessor.implementsDecorator());
            }

            this.mappingPreProcessors.put(mappingPreProcessor.implementsDecorator(), mappingPreProcessor);
        }
    }

    @Override
    public Property convert(C3Type c3Type, C3ConversionContext<Property, EsConversionState> conversionContext) {
        MappingPreProcessor<?> mappingPreProcessor = findForC3Type(c3Type);
        Validate.notNull(mappingPreProcessor, "No MappingPreProcessor found for C3Type: " + c3Type);
       // mappingPreProcessor.process(new BasicMappingContext(c3Type, conversionContext));

        return null;
    }

    @Override
    public boolean supports(C3Type c3Type) {
        boolean ret = false;
        if(c3Type.hasDecorators()){
            ret = findForC3Type(c3Type) != null;
        }
        return ret;
    }

    private MappingPreProcessor<?> findForC3Type(C3Type c3Type){
        MappingPreProcessor<?> ret = null;
        for(C3Decorator decorator : c3Type.getDecorators()){
            // FIXME: without additional validation the first implementation will win, should we support multiple?
            ret = mappingPreProcessors.get(decorator.getClass());
            if(ret != null){
                break;
            }
        }
        return ret;
    }

    private class BasicMappingContext implements MappingContext {
        private final C3Type c3Type;
        private final C3ConversionContext<Property, EsConversionState> conversionContext;

        public BasicMappingContext(C3Type c3Type, C3ConversionContext<Property, EsConversionState> conversionContext) {
            this.c3Type = c3Type;
            this.conversionContext = conversionContext;
        }

        @Override
        public C3Type value() {
            return c3Type;
        }

        @Override
        public Property convertInternal(C3Type c3Type) {
            return conversionContext.convert(c3Type);
        }
    }

}
