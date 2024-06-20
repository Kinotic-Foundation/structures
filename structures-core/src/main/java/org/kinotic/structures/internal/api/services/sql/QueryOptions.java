package org.kinotic.structures.internal.api.services.sql;

import lombok.Builder;
import lombok.Getter;

/**
 * Created by Navíd Mitchell 🤪 on 6/19/24.
 */
@Builder
@Getter
public class QueryOptions {

    @Builder.Default
    private String timeZone = null;
    @Builder.Default
    private Integer requestTimeout = null;
    @Builder.Default
    private String pageTimeout = null;

}
