package org.kinotic.structures.internal.idl.converters.openapi;

import io.swagger.v3.oas.models.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.kinotic.structures.internal.idl.converters.common.BaseConversionState;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Navíd Mitchell 🤪 on 5/14/23.
 */
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class OpenApiConversionState extends BaseConversionState {

    private final Map<String, Schema<?>> referenceSchemas = new HashMap<>();

    public OpenApiConversionState addReferenceSchema(String name, Schema<?> schema){
        referenceSchemas.put(name, schema);
        return this;
    }

}
