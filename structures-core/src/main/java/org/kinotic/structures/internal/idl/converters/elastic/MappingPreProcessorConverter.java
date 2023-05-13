package org.kinotic.structures.internal.idl.converters.elastic;

import co.elastic.clients.elasticsearch._types.mapping.Property;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.tuple.Pair;
import org.kinotic.continuum.idl.api.converter.C3ConversionContext;
import org.kinotic.continuum.idl.api.converter.GenericC3TypeConverter;
import org.kinotic.continuum.idl.api.schema.C3Type;
import org.kinotic.continuum.idl.api.schema.decorators.C3Decorator;
import org.kinotic.structures.api.decorators.runtime.MappingContext;
import org.kinotic.structures.api.decorators.runtime.MappingPreProcessor;
import org.kinotic.structures.api.domain.Structure;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Adapter that allows {@link MappingPreProcessor} to be used as a {@link GenericC3TypeConverter}
 * Created by Navíd Mitchell 🤪 on 5/12/23.
 */
public class MappingPreProcessorConverter implements GenericC3TypeConverter<Property, C3Type, ElasticConversionState> {

    private final Map<Class<C3Decorator>, MappingPreProcessor<C3Decorator>> mappingPreProcessors;

    public MappingPreProcessorConverter(List<MappingPreProcessor<?>> mappingPreProcessors) {
        this.mappingPreProcessors = new HashMap<>(mappingPreProcessors.size());

        for(MappingPreProcessor<?> mappingPreProcessor : mappingPreProcessors){

            if(this.mappingPreProcessors.containsKey(mappingPreProcessor.implementsDecorator())){
                throw new IllegalArgumentException("Duplicate MappingPreProcessor for decorator: " + mappingPreProcessor.implementsDecorator());
            }

            //noinspection unchecked
            this.mappingPreProcessors.put((Class<C3Decorator>) mappingPreProcessor.implementsDecorator(),
                                          (MappingPreProcessor<C3Decorator>) mappingPreProcessor);
        }
    }

    @Override
    public Property convert(C3Type c3Type, C3ConversionContext<Property, ElasticConversionState> conversionContext) {
        Pair<C3Decorator, MappingPreProcessor<C3Decorator>> pair = findForC3Type(c3Type);
        Validate.notNull(pair, "No MappingPreProcessor found for C3Type: " + c3Type);

        Structure structure = conversionContext.state().getStructureBeingConverted();
        String fieldName = conversionContext.state().getCurrentFieldName();
        C3Decorator decorator = pair.getLeft();
        MappingPreProcessor<C3Decorator> mappingPreProcessor = pair.getRight();

        return mappingPreProcessor.process(structure, fieldName, decorator,
                                           new BasicMappingContext(c3Type, conversionContext));

    }

    @Override
    public boolean supports(C3Type c3Type) {
        boolean ret = false;
        if(c3Type.hasDecorators()){
            ret = findForC3Type(c3Type) != null;
        }
        return ret;
    }

    private Pair<C3Decorator, MappingPreProcessor<C3Decorator>> findForC3Type(C3Type c3Type){
        Pair<C3Decorator, MappingPreProcessor<C3Decorator>> ret = null;
        for(C3Decorator decorator : c3Type.getDecorators()){
            MappingPreProcessor<C3Decorator> processor = mappingPreProcessors.get(decorator.getClass());
            if(processor != null){
                ret = Pair.of(decorator, processor);
                break;
            }
        }
        return ret;
    }

    private static class BasicMappingContext implements MappingContext {
        private final C3Type c3Type;
        private final C3ConversionContext<Property, ElasticConversionState> conversionContext;

        public BasicMappingContext(C3Type c3Type, C3ConversionContext<Property, ElasticConversionState> conversionContext) {
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
