package org.kinotic.structures.api.domain.idl;

import org.kinotic.continuum.idl.api.schema.ArrayC3Type;
import org.kinotic.continuum.idl.api.schema.StringC3Type;

/**
 * Represents the TenantSelection Typescript type
 * Created By Navíd Mitchell 🤪on 2/21/25
 */
public class TenantSelectionC3Type extends ArrayC3Type {

    public TenantSelectionC3Type() {
        super(new StringC3Type());
    }
}
