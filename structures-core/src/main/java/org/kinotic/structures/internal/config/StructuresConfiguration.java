package org.kinotic.structures.internal.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Navíd Mitchell 🤪 on 5/30/23.
 */
@Configuration
@EnableConfigurationProperties
@ComponentScan(basePackages = "org.kinotic.structures")
public class StructuresConfiguration {
}
