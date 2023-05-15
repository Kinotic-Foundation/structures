package org.kinotic.structures.internal.api.services;

import io.swagger.v3.oas.models.media.ObjectSchema;
import io.swagger.v3.oas.models.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

/**
 * Created by Navíd Mitchell 🤪 on 5/15/23.
 */
@Getter
@RequiredArgsConstructor
public class OpenApiConversionResult {

    /**
     * The root {@link ObjectSchema} that represents the converted C3Type
     */
    private final ObjectSchema objectSchema;

    /**
     * A map of all {@link ObjectSchema} that were created during the conversion process
     * and were used internally as references
     */
    private final Map<String, Schema<?>> referenceSchemas;

}
