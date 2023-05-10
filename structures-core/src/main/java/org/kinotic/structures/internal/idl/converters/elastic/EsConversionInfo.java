package org.kinotic.structures.internal.idl.converters.elastic;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created by Navíd Mitchell 🤪 on 5/9/23.
 */
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class EsConversionInfo {

    private final DecoratorInfo decoratorInfo = new DecoratorInfo();

}
