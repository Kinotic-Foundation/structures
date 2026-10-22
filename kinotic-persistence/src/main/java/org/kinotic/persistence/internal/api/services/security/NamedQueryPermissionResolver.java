package org.kinotic.persistence.internal.api.services.security;

import io.vertx.core.Future;
import lombok.RequiredArgsConstructor;
import org.kinotic.domain.api.model.security.participant.ScopedParticipant;
import org.kinotic.domain.api.services.security.RequestPermissionResolver;
import org.kinotic.idl.api.schema.decorators.AuthzPermissionChoice;
import org.kinotic.idl.api.utils.AuthzConstants;
import org.kinotic.persistence.internal.api.services.EntityServiceCache;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Decides a named query's permission from its statement: {@code can_read} for a SELECT or an aggregate,
 * {@code can_create} for an INSERT, {@code can_edit} for an UPDATE and {@code can_delete} for a DELETE, on the
 * entity definition the query is defined for. A definition or query that does not exist decides nothing.
 */
@Component
@RequiredArgsConstructor
public class NamedQueryPermissionResolver implements RequestPermissionResolver {

    private static final List<AuthzPermissionChoice> POSSIBLE_PERMISSIONS =
            List.of(new AuthzPermissionChoice(AuthzConstants.CAN_READ, "the query reads rows"),
                    new AuthzPermissionChoice(AuthzConstants.CAN_CREATE, "the query is an INSERT"),
                    new AuthzPermissionChoice(AuthzConstants.CAN_EDIT, "the query is an UPDATE"),
                    new AuthzPermissionChoice(AuthzConstants.CAN_DELETE, "the query is a DELETE"));

    private final EntityServiceCache entityServiceCache;

    @Override
    public String name() {
        return AuthzConstants.NAMED_QUERY_PERMISSION_RESOLVER;
    }

    @Override
    public List<String> parameters() {
        return List.of("entityDefinitionId", "queryName");
    }

    @Override
    public List<AuthzPermissionChoice> possiblePermissions() {
        return POSSIBLE_PERMISSIONS;
    }

    @Override
    public Future<AuthzPermissionChoice> resolve(ScopedParticipant participant, List<String> arguments) {
        String entityDefinitionId = arguments.get(0);
        String queryName = arguments.get(1);
        return entityServiceCache.get(participant.getScope().organizationId(), entityDefinitionId)
                                 .compose(entityService -> entityService.findNamedQueryPermission(queryName))
                                 .map(permission -> POSSIBLE_PERMISSIONS.stream()
                                                                        .filter(choice -> choice.getPermission().equals(permission))
                                                                        .findFirst()
                                                                        .orElseThrow());
    }
}
