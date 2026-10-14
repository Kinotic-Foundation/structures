package org.kinotic.domain.api.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.kinotic.core.api.crud.Identifiable;

import java.util.Date;

/**
 * Represents an organization developing applications on the Kinotic OS platform.
 * Organizations provide the boundary for teams, applications, users, and the OIDC providers
 * their sign-ins offer.
 * <p>
 * The {@code id} is auto-generated from the {@code name} on save (slugified) and serves as the URL-safe identifier.
 */
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class Organization implements Identifiable<String> {

    private String id;

    private String name;

    private String description;

    /**
     * User ID of the administrator who created this organization.
     */
    private String createdBy;

    private Date created;

    private Date updated;

}
