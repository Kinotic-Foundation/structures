package org.kinotic.structures.internal.sample;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created by Navíd Mitchell 🤪on 6/29/23.
 */
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@EqualsAndHashCode
public class Car {

    private String id;
    private String make;
    private String model;
    private String year;
    private Person owner;


}
