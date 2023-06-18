package org.kinotic.structures.internal.sample;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.kinotic.continuum.core.api.security.MetadataConstants;
import org.kinotic.continuum.core.api.security.Participant;

import java.util.List;
import java.util.Map;

/**
 * Created by Navíd Mitchell 🤪on 6/18/23.
 */
@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class DummyParticipant implements Participant {

    private String tenantId = "kinotic";
    private String id = "dummy";
    private Map<String, String> metadata = Map.of(MetadataConstants.TYPE_KEY, "user");
    private List<String> roles = List.of("DUMMY");

    public DummyParticipant(String tenantId, String id) {
        this.tenantId = tenantId;
        this.id = id;
    }
}
