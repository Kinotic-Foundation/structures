package org.kinotic.structures.internal.graphql;

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

    private final String structureId;
    private final Participant participant;
    private final Map<String, Object> variables;
    private final ParsedFields parsedFields;

}
