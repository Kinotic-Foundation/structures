package org.kinotic.structures.internal.api.decorators;

import co.elastic.clients.elasticsearch.core.CountRequest;
import co.elastic.clients.elasticsearch.core.DeleteRequest;
import co.elastic.clients.elasticsearch.core.GetRequest;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import org.apache.commons.lang3.tuple.Triple;
import org.kinotic.continuum.idl.api.schema.decorators.C3Decorator;
import org.kinotic.structures.api.config.StructuresProperties;
import org.kinotic.structures.api.decorators.MultiTenancyType;
import org.kinotic.structures.api.decorators.runtime.crud.*;
import org.kinotic.structures.api.domain.EntityContext;
import org.kinotic.structures.api.domain.Structure;

import java.util.List;

/**
 * Keeps track of all read operations pre-processors for a given structure.
 * This allows for the logic path to only call the pre-processors that are needed for a given operation and structure.
 * Created by Navíd Mitchell 🤪on 6/13/23.
 */
public class DelegatingReadPreProcessor {

    private final StructuresProperties structuresProperties;
    private final List<Triple<String, C3Decorator, CountEntityPreProcessor<C3Decorator>>> countPreProcessors;
    private final List<Triple<String, C3Decorator, DeleteEntityPreProcessor<C3Decorator>>> deletePreProcessors;
    private final List<Triple<String, C3Decorator, FindAllPreProcessor<C3Decorator>>>findAllPreProcessors;
    private final List<Triple<String, C3Decorator, FindByIdPreProcessor<C3Decorator>>>findByIdPreProcessors;
    private final List<Triple<String, C3Decorator, SearchPreProcessor<C3Decorator>>> searchPreProcessors;

    public DelegatingReadPreProcessor(StructuresProperties structuresProperties,
                                      List<Triple<String, C3Decorator, CountEntityPreProcessor<C3Decorator>>> countPreProcessors,
                                      List<Triple<String, C3Decorator, DeleteEntityPreProcessor<C3Decorator>>> deletePreProcessors,
                                      List<Triple<String, C3Decorator, FindAllPreProcessor<C3Decorator>>> findAllPreProcessors,
                                      List<Triple<String, C3Decorator, FindByIdPreProcessor<C3Decorator>>> findByIdPreProcessors,
                                      List<Triple<String, C3Decorator, SearchPreProcessor<C3Decorator>>> searchPreProcessors) {
        this.structuresProperties = structuresProperties;
        this.countPreProcessors = countPreProcessors;
        this.deletePreProcessors = deletePreProcessors;
        this.findAllPreProcessors = findAllPreProcessors;
        this.findByIdPreProcessors = findByIdPreProcessors;
        this.searchPreProcessors = searchPreProcessors;
    }

    public void beforeCount(Structure structure,
                            CountRequest.Builder builder,
                            EntityContext context) {

        // add multi tenancy functionality if needed
        if(structure.getMultiTenancyType() == MultiTenancyType.SHARED){
            builder.routing(context.getParticipant().getTenantId());
        }

        if(countPreProcessors != null && !countPreProcessors.isEmpty()){
            for(Triple<String, C3Decorator, CountEntityPreProcessor<C3Decorator>> tuple : countPreProcessors){
                tuple.getRight().beforeCount(structure, tuple.getLeft(), tuple.getMiddle(), builder, context);
            }
        }
    }

    public void beforeDelete(Structure structure,
                             DeleteRequest.Builder builder,
                             EntityContext context) {

        // add multi tenancy functionality if needed
        if(structure.getMultiTenancyType() == MultiTenancyType.SHARED){
            builder.routing(context.getParticipant().getTenantId());
        }

        if(deletePreProcessors != null && !deletePreProcessors.isEmpty()){
            for(Triple<String, C3Decorator, DeleteEntityPreProcessor<C3Decorator>> tuple : deletePreProcessors){
                tuple.getRight().beforeDelete(structure, tuple.getLeft(), tuple.getMiddle(), builder, context);
            }
        }
    }

    public void beforeFindAll(Structure structure,
                              SearchRequest.Builder builder,
                              EntityContext context) {

        // add multi tenancy functionality if needed
        if(structure.getMultiTenancyType() == MultiTenancyType.SHARED){
            builder.routing(context.getParticipant().getTenantId());
        }

        if(findAllPreProcessors != null && !findAllPreProcessors.isEmpty()){
            for(Triple<String, C3Decorator, FindAllPreProcessor<C3Decorator>> tuple : findAllPreProcessors){
                tuple.getRight().beforeFindAll(structure, tuple.getLeft(), tuple.getMiddle(), builder, context);
            }
        }
    }

    public void beforeFindById(Structure structure,
                               GetRequest.Builder builder,
                               EntityContext context){

        // add multi tenancy functionality if needed
        if(structure.getMultiTenancyType() == MultiTenancyType.SHARED){
            builder.routing(context.getParticipant().getTenantId());
            builder.sourceExcludes(structuresProperties.getTenantIdFieldName());
        }

        if(findByIdPreProcessors != null && !findByIdPreProcessors.isEmpty()){
            for(Triple<String, C3Decorator, FindByIdPreProcessor<C3Decorator>> tuple : findByIdPreProcessors){
                tuple.getRight().beforeFindById(structure, tuple.getLeft(), tuple.getMiddle(), builder, context);
            }
        }
    }

    public void beforeSearch(Structure structure,
                             SearchRequest.Builder builder,
                             EntityContext context) {

        // add multi tenancy functionality if needed
        if(structure.getMultiTenancyType() == MultiTenancyType.SHARED){
            builder.routing(context.getParticipant().getTenantId());
        }

        if(searchPreProcessors != null && !searchPreProcessors.isEmpty()){
            for(Triple<String, C3Decorator, SearchPreProcessor<C3Decorator>> tuple : searchPreProcessors){
                tuple.getRight().beforeSearch(structure, tuple.getLeft(), tuple.getMiddle(), builder, context);
            }
        }
    }

}
