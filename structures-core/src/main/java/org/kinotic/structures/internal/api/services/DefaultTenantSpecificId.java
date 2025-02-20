package org.kinotic.structures.internal.api.services;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.kinotic.structures.api.domain.TenantSpecificId;

/**
 * Created By Navíd Mitchell 🤪on 2/20/25
 */
public record DefaultTenantSpecificId(@JsonProperty("entityId") String entityId,
                                      @JsonProperty("tenantId") String tenantId) implements TenantSpecificId {

}
