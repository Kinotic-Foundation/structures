package org.kinotic.gateway.internal.endpoints.stomp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * A forwarded request still awaiting its reply, as one gateway node hands it to another with the reply
 * state it belongs to.
 *
 * Created by Navíd Mitchell 🤪 on 9/9/26.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PendingRequestRecord {
    private String correlationId;
    /** The node the request is pinned to, null when its acknowledgement had not arrived. */
    private String nodeId;
    private Map<String, String> replyMetadata;
}
