package org.kinotic.structures.internal.api.decorators;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * {@link EntityHolder} holds the data and the id for an entity
 * Created by Navíd Mitchell 🤪 on 5/11/23.
 */
@Getter
@RequiredArgsConstructor
public class EntityHolder<T> {

    private final String id;

    private final T entity;

}
