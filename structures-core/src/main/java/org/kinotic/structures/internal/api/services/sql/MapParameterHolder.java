package org.kinotic.structures.internal.api.services.sql;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

/**
 * Created by Navíd Mitchell 🤪 on 5/8/24.
 */
@RequiredArgsConstructor
@Getter
public class MapParameterHolder implements ParameterHolder {

    private final Map<String, Object> parameters;

}
