package org.kinotic.structures_autoconfig;

import org.kinotic.structures.StructuresCoreLibrary;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Import;

/**
 * This is the autoconfiguration class for this library
 * It is defined in a separate package because it must not be scanned by the spring context
 */
@AutoConfiguration
@Import(StructuresCoreLibrary.class)
public class StructuresCoreAutoConfiguration {

}
