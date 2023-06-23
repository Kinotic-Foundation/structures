package org.kinotic.structures.internal.config;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * Created by Navíd Mitchell 🤪 on 4/24/23.
 */
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ElasticConnectionInfo {

    private String scheme = "http";

    private String host = "localhost";

    private int port = 9200;

    public String toHostAndPort(){
        return host + ":" + port;
    }

}
