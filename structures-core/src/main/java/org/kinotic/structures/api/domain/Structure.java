/*
 *
 * Copyright 2008-2021 Kinotic and the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kinotic.structures.api.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.kinotic.continuum.api.Identifiable;
import org.kinotic.continuum.idl.api.schema.ObjectC3Type;
import org.kinotic.structures.api.domain.idl.decorators.EntityType;
import org.kinotic.structures.api.domain.idl.decorators.MultiTenancyType;
import org.kinotic.structures.internal.idl.converters.common.DecoratedProperty;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class Structure implements Identifiable<String> {

    private String id = null; // do not ever set, system managed

    private String name = null;
    /**
     * The id of the application that this structure belongs to.
     * All application ids are unique throughout the entire system.
     */
    private String applicationId = null;

    /**
     * The id of the project that this structure belongs to.
     * All project ids are unique throughout the entire system.
     */
    private String projectId = null;

    private String description = null;

    private MultiTenancyType multiTenancyType = null;

    private EntityType entityType = null;

    private ObjectC3Type entityDefinition = null;

    private Date created = null; // do not ever set, system managed

    private Date updated = null; // do not ever set, system managed

    private boolean published = false; // do not ever set, system managed

    private Date publishedTimestamp = null; // do not ever set, system managed

    private String itemIndex = null; // do not ever set, system managed

    private List<DecoratedProperty> decoratedProperties = new ArrayList<>(); // do not ever set, system managed

    /**
     * The name of the field that will be used for optimistic locking
     * or null if optimistic locking is not enabled
     */
    private String versionFieldName = null; // do not ever set, system managed

    /**
     * The name of the field that will be used to hold the tenant id for an entity.
     * If this is set then Structures will provide "Admin" services to access Entities for multiple tenants.
     */
    private String tenantIdFieldName = null; // do not ever set, system managed

    /**
     * The name of the field that will be used to hold the time reference for an entity.
     */
    private String timeReferenceFieldName = null; // do not ever set, system managed

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Structure structure = (Structure) o;

        return new EqualsBuilder().append(id, structure.id).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id).toHashCode();
    }

    public boolean isOptimisticLockingEnabled(){
        return versionFieldName != null;
    }

    public boolean isMultiTenantSelectionEnabled() {
        return tenantIdFieldName != null;
    }

    public boolean isStream() {
        return timeReferenceFieldName != null;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("name", name)
                .append("applicationId", applicationId)
                .append("projectId", projectId)
                .append("description", description)
                .append("multiTenancyType", multiTenancyType)
                .toString();
    }
}
