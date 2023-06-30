package org.kinotic.structures.internal.api.decorators;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * {@link EntityHolder} holds the data and the id for an entity
 * Created by Navíd Mitchell 🤪 on 5/11/23.
 */
@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class EntityHolder {

    private String id;

    private Object entity;

}
