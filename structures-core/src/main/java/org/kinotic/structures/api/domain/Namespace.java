package org.kinotic.structures.api.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.kinotic.continuum.api.Identifiable;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class Namespace implements Identifiable<String> {

    private String id = null;
    private String description = null;
    private long updated = 0;

}
