package org.kinotic.structures.internal.endpoints.graphql;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.kinotic.continuum.api.security.Participant;

import java.util.Map;

/**
 * Created by Navíd Mitchell 🤪 on 12/14/23.
 */
@RequiredArgsConstructor
@Getter
public class GqlOperationArguments {
    private final String namespace;
    private final String operationName;
    private final ParsedFields parsedFields;
    private final Participant participant;
    private final Map<String, Object> variables;

}
