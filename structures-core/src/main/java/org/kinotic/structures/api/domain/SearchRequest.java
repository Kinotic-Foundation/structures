package org.kinotic.structures.api.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.domain.Pageable;

/**
 * Created by Navíd Mitchell 🤪 on 5/29/23.
 */
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class SearchRequest {

    private String search;

    private Pageable pageable;

}
