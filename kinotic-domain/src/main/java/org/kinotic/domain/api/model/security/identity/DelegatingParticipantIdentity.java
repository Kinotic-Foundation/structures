package org.kinotic.domain.api.model.security.identity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.kinotic.domain.api.model.security.DelegateType;

/**
 * A client (a CLI install, an MCP host such as an LLM) a user has authorized to act on
 * their behalf. Carries the scope the user was signed in to when authorizing it, the organization
 * of its membership for an organization user, is unique by {@code (ownerId, clientKey)} within that scope, and authenticates
 * with the tokens minted when its owner authorized it, holding no credential of its own. Disabling
 * a delegate revokes that client's access without touching the owner.
 */
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public final class DelegatingParticipantIdentity extends ParticipantIdentity {

    /**
     * Id of the {@link UserParticipantIdentity} this delegate acts on behalf of; for an organization user, the
     * delegate acts in the one organization its own {@code organizationId} names.
     */
    private String ownerId;

    /**
     * Stable identity of the authorized client, unique per owner: the CIMD {@code client_id}
     * URL for {@link DelegateType#MCP_CLIENT}, a fixed well-known key for
     * {@link DelegateType#CLI}.
     */
    private String clientKey;

    /**
     * The type of client this delegate represents.
     */
    private DelegateType delegateType;

    @Override
    public ParticipantIdentityType getType() {
        return ParticipantIdentityType.DELEGATE;
    }
}
