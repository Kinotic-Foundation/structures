package org.kinotic.structures.api.domain;

import java.util.Date;
import org.kinotic.continuum.api.Identifiable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class Project implements Identifiable<String> {

    private String id;
    private String applicationId;
    private String name;
    private String description;
    private Date updated;

}
