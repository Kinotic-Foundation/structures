package org.kinotic.appserver.api.config;

import lombok.Getter;
import lombok.Setter;

import java.util.regex.Pattern;

/**
 * The UIs the app server admits beyond an application's published sites, {@code kinotic.appServer.*}, set per
 * deployment. The URL the app server is reached at is {@code kinotic.domain.appApiBaseUrl}.
 */
@Getter
@Setter
public class AppServerProperties {

    /**
     * Origins admitted as a UI of every application, for UIs a developer serves from their own machine, such as
     * {@code http://localhost:\d+}. When unset, an application's login is made only from its published sites.
     */
    private Pattern localUiOriginPattern;
}
