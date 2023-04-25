package org.kinotic.structures.internal.config;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * Created by Navíd Mitchell 🤪 on 4/24/23.
 */
@Getter
@Setter
@Accessors(chain = true)
@RequiredArgsConstructor
@AllArgsConstructor
public class ElasticConnectionInfo {

    private String scheme = "http";

    private final String host;

    private final int port;

    public String toHostAndPort(){
        return host + ":" + port;
    }

}
