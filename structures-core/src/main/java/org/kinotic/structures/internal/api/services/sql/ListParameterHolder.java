package org.kinotic.structures.internal.api.services.sql;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.kinotic.structures.api.domain.QueryParameter;

import java.util.List;

/**
 * Created by Navíd Mitchell 🤪 on 5/8/24.
 */
@RequiredArgsConstructor
@Getter
public class ListParameterHolder implements ParameterHolder {

    private final List<QueryParameter> parameters;

}
