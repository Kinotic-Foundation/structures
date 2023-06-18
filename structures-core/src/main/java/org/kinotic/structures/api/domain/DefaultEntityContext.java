package org.kinotic.structures.api.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.kinotic.continuum.core.api.security.Participant;

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


}
