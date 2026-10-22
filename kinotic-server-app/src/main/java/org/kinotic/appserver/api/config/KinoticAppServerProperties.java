package org.kinotic.appserver.api.config;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.kinotic.core.api.config.KinoticProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * Contributes the {@link AppServerProperties} to the kinotic prefix.
 * Configuration is accessible via {@code kinotic.appServer.*}
 */
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@Component
@Validated
public class KinoticAppServerProperties extends KinoticProperties {

    /**
     * The UIs the app server admits beyond an application's published sites.
     */
    @Valid
    private AppServerProperties appServer = new AppServerProperties();

}
