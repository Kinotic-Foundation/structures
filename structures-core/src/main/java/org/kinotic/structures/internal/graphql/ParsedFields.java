package org.kinotic.structures.internal.graphql;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Navíd Mitchell 🤪 on 12/14/23.
 */
@NoArgsConstructor
@Getter
class ParsedFields {
    private final List<String> contentFields = new ArrayList<>();
    private final List<String> nonContentFields = new ArrayList<>();
}
