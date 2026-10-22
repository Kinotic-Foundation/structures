package org.kinotic.system.internal.api.services;

import io.vertx.core.Future;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.Validate;
import org.kinotic.authz.api.model.AccessExplanation;
import org.kinotic.authz.api.model.AuthzModel;
import org.kinotic.authz.api.model.Consistency;
import org.kinotic.authz.api.model.Grant;
import org.kinotic.authz.api.model.RelationshipTuple;
import org.kinotic.authz.api.model.Resource;
import org.kinotic.authz.api.model.RoleDefinition;
import org.kinotic.authz.api.model.Subject;
import org.kinotic.authz.api.model.SubjectType;
import org.kinotic.authz.api.services.AuthzModelGenerator;
import org.kinotic.authz.api.services.AuthzStoreService;
import org.kinotic.authz.api.services.RelationshipService;
import org.kinotic.core.api.directory.ServiceDirectory;
import org.kinotic.domain.api.model.security.identity.MachineParticipantIdentity;
import org.kinotic.domain.api.model.security.identity.SystemParticipantIdentity;
import org.kinotic.domain.api.services.security.ParticipantIdentityService;
import org.kinotic.domain.api.utils.DomainUtil;
import org.kinotic.idl.api.utils.AuthzUtil;
import org.kinotic.system.api.services.SystemAccessService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.kinotic.authz.api.services.AuthzStoreService.SYSTEM;

@Component
@RequiredArgsConstructor
public class DefaultSystemAccessService implements SystemAccessService {

    private static final Resource SYSTEM_RESOURCE = new Resource(AuthzUtil.SYSTEM_TYPE, AuthzUtil.SYSTEM_OBJECT_ID);
    private static final String SYSTEM_OBJECT = AuthzUtil.object(AuthzUtil.SYSTEM_TYPE, AuthzUtil.SYSTEM_OBJECT_ID);

    private final ServiceDirectory directory;
    private final AuthzModelGenerator generator;
    private final AuthzStoreService stores;
    private final RelationshipService relationships;
    private final ParticipantIdentityService identities;

    @Override
    public Future<List<RoleDefinition>> findRoles() {
        return model().map(model -> model.builtInRoles(AuthzUtil.SYSTEM_TYPE));
    }

    @Override
    public Future<List<Grant>> findGrants() {
        return relationships.findGrants(SYSTEM, SYSTEM_OBJECT).map(grants -> grants.stream().map(DomainUtil::localGrant).toList());
    }

    @Override
    public Future<Grant> grant(Subject subject, String roleId) {
        validate(subject);
        Validate.notBlank(roleId, "roleId cannot be blank");
        return model().compose(model -> {
            if (!model.roles().containsKey(roleId) || !model.grantable(roleId, AuthzUtil.SYSTEM_TYPE)) {
                throw new IllegalArgumentException("No role of the system has id " + roleId);
            }
            return requireStaff(subject);
        }).compose(stored -> relationships.bind(SYSTEM, roleId, userOf(stored), SYSTEM_OBJECT))
          .map(bindingId -> new Grant(bindingId, roleId, subject, SYSTEM_RESOURCE));
    }

    @Override
    public Future<Void> revoke(String grantId) {
        Validate.notBlank(grantId, "grantId cannot be blank");
        return relationships.revoke(SYSTEM, grantId, SYSTEM_OBJECT);
    }

    @Override
    public Future<AccessExplanation> explain(Subject subject, String permission) {
        validate(subject);
        Validate.notBlank(permission, "permission cannot be blank");
        String name = AuthzUtil.permissionName(AuthzUtil.SYSTEM_TYPE, permission);
        return requireStaff(subject).compose(stored -> relationships.findGrants(SYSTEM, SYSTEM_OBJECT)
                .compose(grants -> stores.modelId(SYSTEM).compose(modelId -> {
                    RelationshipTuple holds = new RelationshipTuple(userOf(stored), name, SYSTEM_OBJECT);
                    // an administrator asks after changing access, so the answer must not predate the change
                    Future<Boolean> allowed = relationships.check(SYSTEM, modelId, holds, Consistency.HIGHER_CONSISTENCY);
                    List<Future<Boolean>> explains = grants.stream().map(grant -> relationships.explains(SYSTEM, modelId, grant, stored, name)).toList();
                    return Future.all(explains).compose(results -> allowed.map(held -> {
                        List<Grant> through = new ArrayList<>();
                        for (int i = 0; i < grants.size(); i++) {
                            if (results.<Boolean>resultAt(i)) {
                                through.add(DomainUtil.localGrant(grants.get(i)));
                            }
                        }
                        return new AccessExplanation(held, through);
                    }));
                })));
    }

    private Future<AuthzModel> model() {
        return directory.findSystemDefinitions().map(generator::systemModel);
    }

    // The system's staff, a system user or a machine in SYSTEM scope, each held by the store as itself
    private Future<Subject> requireStaff(Subject subject) {
        return identities.findById(subject.id()).map(identity -> {
            boolean staff = identity instanceof SystemParticipantIdentity
                    || (identity instanceof MachineParticipantIdentity machine
                        && machine.getOrganizationId() == null
                        && machine.getApplicationId() == null);
            if (!staff) {
                throw new IllegalArgumentException("No system user or machine of the system has id " + subject.id());
            }
            return subject;
        });
    }

    // The system has no groups, so a subject is one of its identities
    private static void validate(Subject subject) {
        Validate.notNull(subject, "subject cannot be null");
        Validate.isTrue(subject.type() == SubjectType.USER, "a grant on the system is made to a user");
        Validate.notBlank(subject.id(), "subject id cannot be blank");
    }

    private static String userOf(Subject subject) {
        return AuthzUtil.object(AuthzUtil.USER_TYPE, subject.id());
    }
}
