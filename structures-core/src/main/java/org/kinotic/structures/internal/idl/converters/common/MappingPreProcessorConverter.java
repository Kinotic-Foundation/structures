package org.kinotic.structures.internal.idl.converters.common;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.tuple.Pair;
import org.kinotic.continuum.idl.api.converter.C3ConversionContext;
import org.kinotic.continuum.idl.api.converter.GenericC3TypeConverter;
import org.kinotic.continuum.idl.api.schema.C3Type;
import org.kinotic.continuum.idl.api.schema.decorators.C3Decorator;
import org.kinotic.structures.api.decorators.runtime.mapping.MappingContext;
import org.kinotic.structures.api.decorators.runtime.mapping.MappingPreProcessor;
import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.internal.utils.StructuresUtil;

import java.util.List;
import java.util.Map;

/**
 * Adapter that allows {@link MappingPreProcessor} to be used as a {@link GenericC3TypeConverter}
 * Created by Navíd Mitchell 🤪 on 5/12/23.
 */
public class MappingPreProcessorConverter<R, S extends BaseConversionState> implements GenericC3TypeConverter<R, C3Type, S> {

    private final Map<String, MappingPreProcessor<C3Decorator, R, S>> preProcessors;

    public MappingPreProcessorConverter(List<MappingPreProcessor<C3Decorator, R, S>> preProcessors) {
        this.preProcessors = StructuresUtil.listToMap(preProcessors,
                                                      processor -> processor.implementsDecorator().getName());
    }

    @Override
    public R convert(C3Type c3Type, C3ConversionContext<R, S> conversionContext) {
        Pair<C3Decorator, MappingPreProcessor<C3Decorator, R, S>> pair = findForC3Type(c3Type);
        // Sanity Check, should never happen since supports should be called before calling this method
        Validate.notNull(pair, "No MappingPreProcessor found for C3Type: " + c3Type);

        Structure structure = conversionContext.state().getStructureBeingConverted();
        String fieldName = conversionContext.state().getCurrentFieldName();
        C3Decorator decorator = pair.getLeft();
        MappingPreProcessor<C3Decorator, R, S> preProcessor = pair.getRight();

        if(!preProcessor.supportC3Type(c3Type)){
            throw new IllegalArgumentException("Decorator: " + preProcessor.implementsDecorator().getName()
                                               + " does not support C3Type: " + c3Type.getClass().getName()
                                               + " on field: " + fieldName + " of structure: " + structure.getName() + "");
        }

        return preProcessor.process(structure, fieldName, decorator, c3Type,
                                    new BasicMappingContext<>(conversionContext));
    }

    @Override
    public boolean supports(C3Type c3Type) {
        boolean ret = false;
        if(c3Type.hasDecorators()){
            ret = findForC3Type(c3Type) != null;
        }
        return ret;
    }

    /**
     * Finds the first {@link C3Decorator} that has a {@link MappingPreProcessor} registered for it
     * NOTE: We currently only allow one {@link MappingPreProcessor} implementation per concrete {@link C3Decorator}.
     *       If we need more than one decorator per type we will need to change this method to return a list.
     *       Additionally, ordering of decorators would need to be supported.
     *
     * @param c3Type to find a {@link MappingPreProcessor} for
     * @return the first {@link C3Decorator} that has a {@link MappingPreProcessor} registered for it or null if none found
     */
    private Pair<C3Decorator, MappingPreProcessor<C3Decorator, R, S>> findForC3Type(C3Type c3Type){
        Pair<C3Decorator, MappingPreProcessor<C3Decorator, R, S>> ret = null;
        for(C3Decorator decorator : c3Type.getDecorators()){
            MappingPreProcessor<C3Decorator, R, S> processor = preProcessors.get(decorator.getClass().getName());
            if(processor != null){
                ret = Pair.of(decorator, processor);
                break;
            }
        }
        return ret;
    }

    private static class BasicMappingContext<R, S> implements MappingContext<R, S> {
        private final C3ConversionContext<R, S> conversionContext;

        public BasicMappingContext(C3ConversionContext<R, S> conversionContext) {
            this.conversionContext = conversionContext;
        }

        @Override
        public R convertInternal(C3Type c3Type) {
            return conversionContext.convert(c3Type);
        }

        @Override
        public S state() {
            return conversionContext.state();
        }
    }

}
