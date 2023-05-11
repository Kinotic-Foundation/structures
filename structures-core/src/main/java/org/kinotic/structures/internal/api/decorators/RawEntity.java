package org.kinotic.structures.internal.api.decorators;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * {@link RawEntity} holds the raw data and the id of an entity
 * Created by Navíd Mitchell 🤪 on 5/11/23.
 */
@Getter
@RequiredArgsConstructor
public class RawEntity {

    private final String id;

    private final byte[] data;

}
