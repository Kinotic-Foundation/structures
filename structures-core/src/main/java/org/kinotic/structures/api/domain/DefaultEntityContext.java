package org.kinotic.structures.api.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.kinotic.continuum.api.security.Participant;

import java.util.List;

/**
 * Created by Navíd Mitchell 🤪 on 6/8/23.
 */
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class DefaultEntityContext implements EntityContext {

    private Participant participant;

    private List<String> includedFieldsFilter = null;

    public DefaultEntityContext(Participant participant) {
        this.participant = participant;
    }

    @Override
    public boolean hasIncludedFieldsFilter() {
        return includedFieldsFilter != null;
    }
}
