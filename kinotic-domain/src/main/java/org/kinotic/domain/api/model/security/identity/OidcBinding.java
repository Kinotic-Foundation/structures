package org.kinotic.domain.api.model.security.identity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.kinotic.core.api.crud.Identifiable;

import java.util.Date;

/**
 * A sign-in method of a {@link UserParticipantIdentity}: the identity a provider asserts for the person,
 * the {@code sub} claim under one OIDC configuration, bound to the user it signs in. A user may hold any
 * number of bindings beside its password, and a provider identity is bound to at most one user.
 *
 * <p>The id is derived from the configuration and the subject ({@link #idOf}), so a callback resolves a
 * binding by a get, which sees a binding the moment it is written.
 */
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class OidcBinding implements Identifiable<String> {

    private String id;

    /** The user the provider identity signs in. */
    private String identityId;

    /** The OIDC configuration the provider identity was asserted under. */
    private String oidcConfigId;

    /** The provider's {@code sub} claim for the person. */
    private String subject;

    private Date created;

    /** The id of the binding of {@code subject} under configuration {@code oidcConfigId}. */
    public static String idOf(String oidcConfigId, String subject) {
        return oidcConfigId + ":" + subject;
    }
}
