package org.kinotic.structures.internal.api.services;

import org.kinotic.structures.api.domain.AlreadyExistsException;
import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.api.domain.Trait;
import org.kinotic.structures.api.services.StructureService;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

/**
 * Created by Navíd Mitchell 🤪 on 3/30/23.
 */
public interface StructureServiceInternal extends StructureService {

    Structure save(Structure structure) throws AlreadyExistsException;

    Optional<Structure> getById(String id) throws IOException;

    default String getJsonSchema(Structure structure) {
        StringBuilder ret = new StringBuilder();
        StringBuilder properties = new StringBuilder();
        StringBuilder requires = new StringBuilder();
        StringBuilder systemManaged = new StringBuilder();
        for(Map.Entry<String, Trait> traitEntry : structure.getTraits().entrySet()){
            if(!traitEntry.getValue().isOperational()){// operational traits never get added to schema
                if(properties.length() > 0){
                    properties.append(",");
                }
                properties.append("\"").append(traitEntry.getKey()).append("\":").append(traitEntry.getValue().getSchema());
                if(traitEntry.getValue().isRequired()){
                    if(requires.length() == 0){
                        requires.append("[");
                    }else{
                        requires.append(",");
                    }
                    requires.append("\"").append(traitEntry.getKey()).append("\"");
                }
                if(traitEntry.getValue().isSystemManaged()){
                    if(systemManaged.length() == 0){
                        systemManaged.append("[");
                    }else{
                        systemManaged.append(",");
                    }
                    systemManaged.append("\"").append(traitEntry.getKey()).append("\"");
                }
            }
        }
        properties.append("}");// end properties

        ret.append("{\"$schema\": \"http://json-schema.org/draft-07/schema#\",\"type\": \"object\",\"structure\": \""+structure.getId()+"\",\"properties\": {");
        ret.append(properties);
        if(requires.length() > 0){
            requires.append("]");
            ret.append(",\"required\":");
            ret.append(requires);
        }else{
            ret.append(",\"required\":[]");
        }
        if(systemManaged.length() > 0){
            systemManaged.append("]");
            ret.append(",\"systemManaged\":");
            ret.append(systemManaged);
        }else{
            ret.append(",\"systemManaged\":[]");
        }
        ret.append("}");// end schema

        return ret.toString();
    }

    default String getElasticSearchBaseMapping(Structure structure) {
        StringBuilder ret = new StringBuilder();
        StringBuilder properties = new StringBuilder();
        for (Map.Entry<String, Trait> traitEntry : structure.getTraits().entrySet()) {
            if (!traitEntry.getValue().isOperational()) {// operational traits never get added to schema
                if (properties.length() == 0) {
                    properties.append("\"properties\": {");
                } else {
                    properties.append(",");
                }
                properties.append("\"").append(traitEntry.getKey()).append("\":").append(traitEntry.getValue().getEsSchema());
            }
        }
        properties.append("}");// end properties

        ret.append("{ \"dynamic\": \"strict\", ");// start object
        ret.append(properties);
        ret.append("}");// end object
        return ret.toString();
    }

}
