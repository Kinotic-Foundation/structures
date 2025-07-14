package org.kinotic.structures.api.config;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class OidcAuthVerifierProperties {

    private boolean enabled = false;

    private List<String> allowedIssuers;

    private List<String> authorizationAudiences;
    
}
