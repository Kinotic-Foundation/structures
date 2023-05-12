package org.kinotic.structures.support;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.kinotic.structures.api.domain.Structure;

/**
 * Created by Navíd Mitchell 🤪 on 5/12/23.
 */
@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class StructureAndPersonHolder {

    private Structure structure;

    private Person person;

}
