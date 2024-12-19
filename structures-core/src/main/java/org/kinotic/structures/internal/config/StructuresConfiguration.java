package org.kinotic.structures.internal.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Enables component scanning for structures classes when {@link org.kinotic.continuum.api.annotations.EnableContinuum} is present
 * NOTE: do not define any beans here, since they should not be loaded first necessarily
 * Created by Navíd Mitchell 🤪 on 5/30/23.
 */
@Configuration
@EnableConfigurationProperties
@ComponentScan(basePackages = "org.kinotic.structures")
public class StructuresConfiguration {


}
