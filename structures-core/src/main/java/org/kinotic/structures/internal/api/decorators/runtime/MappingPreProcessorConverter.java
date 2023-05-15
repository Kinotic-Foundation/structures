package org.kinotic.structures.internal.api.decorators.runtime;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.tuple.Pair;
import org.kinotic.continuum.idl.api.converter.C3ConversionContext;
import org.kinotic.continuum.idl.api.converter.GenericC3TypeConverter;
import org.kinotic.continuum.idl.api.schema.C3Type;
import org.kinotic.continuum.idl.api.schema.decorators.C3Decorator;
import org.kinotic.structures.api.decorators.runtime.DecoratorProcessor;
import org.kinotic.structures.api.decorators.runtime.MappingContext;
import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.internal.idl.converters.common.BaseConversionState;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Adapter that allows {@link DecoratorProcessor} to be used as a {@link GenericC3TypeConverter}
 * Created by Navíd Mitchell 🤪 on 5/12/23.
 */
public class MappingPreProcessorConverter<R, S extends BaseConversionState> implements GenericC3TypeConverter<R, C3Type, S> {

    private final Map<Class<C3Decorator>, DecoratorProcessor<C3Decorator, R, MappingContext<R>>> preProcessors;

    public MappingPreProcessorConverter(List<DecoratorProcessor<C3Decorator, R, MappingContext<R>>> preProcessors) {
        this.preProcessors = new HashMap<>(preProcessors.size());

        for(DecoratorProcessor<C3Decorator, R, MappingContext<R>> preProcessor : preProcessors){

            if(this.preProcessors.containsKey(preProcessor.implementsDecorator())){
                throw new IllegalArgumentException("Duplicate Mapping PreProcessor for decorator: " + preProcessor.implementsDecorator());
            }

            this.preProcessors.put(preProcessor.implementsDecorator(), preProcessor);
        }
    }

    @Override
    public R convert(C3Type c3Type, C3ConversionContext<R, S> conversionContext) {
        Pair<C3Decorator, DecoratorProcessor<C3Decorator, R, MappingContext<R>>> pair = findForC3Type(c3Type);
        // Sanity Check, should never happen since supports should be called before calling this method
        Validate.notNull(pair, "No Mapping PreProcessor found for C3Type: " + c3Type);

        Structure structure = conversionContext.state().getStructureBeingConverted();
        String fieldName = conversionContext.state().getCurrentFieldName();
        C3Decorator decorator = pair.getLeft();
        DecoratorProcessor<C3Decorator, R, MappingContext<R>> preProcessor = pair.getRight();

        return preProcessor.process(structure, fieldName, decorator,
                                    new BasicMappingContext<>(c3Type, conversionContext));
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
     * Finds the first {@link C3Decorator} that has a {@link DecoratorProcessor} registered for it
     * NOTE: This should be reliable since we currently only allow one decorator per type.
     *       If we need more than one decorator per type we will need to change this method to return a list.
     *       Additionally, ordering of decorators would need to be supported.
     *
     * @param c3Type to find a {@link DecoratorProcessor} for
     * @return the first {@link C3Decorator} that has a {@link DecoratorProcessor} registered for it or null if none found
     */
    private Pair<C3Decorator, DecoratorProcessor<C3Decorator, R, MappingContext<R>>> findForC3Type(C3Type c3Type){
        Pair<C3Decorator, DecoratorProcessor<C3Decorator, R, MappingContext<R>>> ret = null;
        for(C3Decorator decorator : c3Type.getDecorators()){
            DecoratorProcessor<C3Decorator, R, MappingContext<R>> processor = preProcessors.get(decorator.getClass());
            if(processor != null){
                ret = Pair.of(decorator, processor);
                break;
            }
        }
        return ret;
    }

    private static class BasicMappingContext<R, S> implements MappingContext<R> {
        private final C3Type c3Type;
        private final C3ConversionContext<R, S> conversionContext;

        public BasicMappingContext(C3Type c3Type, C3ConversionContext<R, S> conversionContext) {
            this.c3Type = c3Type;
            this.conversionContext = conversionContext;
        }

        @Override
        public C3Type value() {
            return c3Type;
        }

        @Override
        public R convertInternal(C3Type c3Type) {
            return conversionContext.convert(c3Type);
        }
    }

}
