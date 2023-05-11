package org.kinotic.structures.support;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created by Navíd Mitchell 🤪 on 5/11/23.
 */
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class Address {

    private String street;
    private String city;
    private String state;
    private String zip;
}
