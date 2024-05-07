package org.kinotic.structures.internal.idl.converters.elastic;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.kinotic.structures.api.idl.decorators.MultiTenancyType;
import org.kinotic.structures.internal.idl.converters.common.BaseConversionState;

/**
 * Created by Navíd Mitchell 🤪 on 5/9/23.
 */
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class ElasticConversionState extends BaseConversionState {

    /**
     * The {@link MultiTenancyType} detected while converting the structure
     */
    private MultiTenancyType multiTenancyType;

}
