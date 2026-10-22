package org.kinotic.test.tests.core.authz;

import io.vertx.core.Future;
import org.junit.jupiter.api.Test;
import org.kinotic.authz.api.model.Resource;
import org.kinotic.authz.api.model.Subject;
import org.kinotic.authz.api.model.SubjectType;
import org.kinotic.core.api.exceptions.AuthorizationException;
import org.kinotic.core.api.security.Participant;
import org.kinotic.domain.api.model.persistence.EntityDefinition;
import org.kinotic.domain.api.model.persistence.NamedQueriesDefinition;
import org.kinotic.domain.api.model.persistence.idl.decorators.MultiTenancyType;
import org.kinotic.domain.api.model.persistence.idl.decorators.QueryDecorator;
import org.kinotic.domain.api.model.security.identity.ApplicationParticipantIdentity;
import org.kinotic.domain.api.model.security.participant.ScopedParticipant;
import org.kinotic.domain.api.services.security.ParticipantIdentityService;
import org.kinotic.domain.api.utils.DomainUtil;
import org.kinotic.idl.api.schema.FunctionDefinition;
import org.kinotic.idl.api.schema.StringC3Type;
import org.kinotic.idl.api.utils.AuthzUtil;
import org.kinotic.management.api.services.ApplicationService;
import org.kinotic.management.api.services.EntityDefinitionService;
import org.kinotic.management.api.services.NamedQueriesDefinitionService;
import org.kinotic.management.api.services.security.ApplicationAccessService;
import org.kinotic.persistence.api.model.QueryParameter;
import org.kinotic.persistence.internal.api.model.DefaultEntityContext;
import org.kinotic.persistence.internal.api.services.EntitiesService;
import org.kinotic.persistence.internal.api.services.sql.ListParameterHolder;
import org.kinotic.test.support.kinotic.KinoticTestBase;
import org.kinotic.test.support.sample.TestDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Verifies that a named query is checked for the permission its statement needs: one that reads rows passes the
 * request's check for {@code can_read}, and one that updates rows is refused to a viewer of the definition and
 * runs for an editor of it.
 */
@SpringBootTest
public class NamedQueryAuthorizationTests extends KinoticTestBase {

    private static final String ENTITIES_SERVICE = DomainUtil.APP_API_ZONE + "~org.kinotic.persistence.api.services.JsonEntitiesRepository";
    private static final String RENAME_QUERY = "renamePeople";

    @Autowired
    private ApplicationAccessService access;
    @Autowired
    private ApplicationService applicationService;
    @Autowired
    private EntitiesService entitiesService;
    @Autowired
    private EntityDefinitionService entityDefinitionService;
    @Autowired
    private ParticipantIdentityService identityService;
    @Autowired
    private NamedQueriesDefinitionService namedQueriesDefinitionService;
    @Autowired
    private TestDataService testDataService;

    @Test
    public void aNamedQueryThatUpdatesRowsIsRunOnlyForAnEditor() throws Exception {
        EntityDefinition person = createPersonDefinition();
        createRenameQuery(person);
        String tenantId = "tenant-" + suffix();
        ApplicationParticipantIdentity bob = endUser(tenantId);
        ScopedParticipant caller = applicationParticipant(tenantId, bob.getId());
        Subject subject = new Subject(SubjectType.USER, bob.getId());
        Resource personHere = new Resource(AuthzUtil.TENANT_DEFINITION_TYPE, AuthzUtil.tenantDefinitionId(person.getId(), tenantId));
        List<Object> request = List.of(person.getId(), RENAME_QUERY, List.of());

        await(runAsOrganization(() -> access.grant(TEST_APP_ID, subject, AuthzUtil.roleId(AuthzUtil.ENTITY_DEFINITION_TYPE, AuthzUtil.VIEWER), personHere)));
        // the request's check reads only can_read, which the viewer holds
        assertTrue(awaitUntil(() -> admitted(ENTITIES_SERVICE, "namedQuery", caller, request)), "the viewer was never admitted to the named query");
        Throwable refused = assertThrows(ExecutionException.class, () -> await(rename(person, caller))).getCause();
        assertInstanceOf(AuthorizationException.class, refused);

        await(runAsOrganization(() -> access.grant(TEST_APP_ID, subject, AuthzUtil.roleId(AuthzUtil.ENTITY_DEFINITION_TYPE, AuthzUtil.EDITOR), personHere)));
        assertTrue(awaitUntil(() -> {
            boolean ran;
            try {
                ran = ((Number) await(rename(person, caller)).getFirst().get("count")).longValue() == 0;
            } catch (ExecutionException e) {
                ran = false;
            }
            return ran;
        }), "the editor's update never ran");
    }

    @SuppressWarnings("rawtypes")
    private Future<List<Map>> rename(EntityDefinition person, Participant caller) {
        ListParameterHolder parameters = new ListParameterHolder(List.of(new QueryParameter("oldName", "Nobody-" + suffix()),
                                                                         new QueryParameter("newName", "Renamed")));
        return runAsOrganization(() -> entitiesService.namedQuery(person.getId(), RENAME_QUERY, parameters, Map.class,
                                                                  new DefaultEntityContext((ScopedParticipant) caller)));
    }

    private void createRenameQuery(EntityDefinition definition) throws Exception {
        FunctionDefinition rename = new FunctionDefinition().setName(RENAME_QUERY)
                                                            .addParameter("oldName", new StringC3Type())
                                                            .addParameter("newName", new StringC3Type());
        rename.setDecorators(List.of(new QueryDecorator().setStatements("UPDATE %s SET lastName = :newName WHERE lastName = :oldName"
                                                                                .formatted(definition.getName()))));
        NamedQueriesDefinition namedQueries = new NamedQueriesDefinition();
        namedQueries.setId(definition.getId());
        namedQueries.setOrganizationId(definition.getOrganizationId());
        namedQueries.setApplicationId(definition.getApplicationId());
        namedQueries.setProjectId(definition.getProjectId());
        namedQueries.setEntityDefinitionName(definition.getName());
        namedQueries.setNamedQueries(List.of(rename));
        await(runAsOrganization(() -> namedQueriesDefinitionService.saveSync(namedQueries)));
    }

    private EntityDefinition createPersonDefinition() throws Exception {
        EntityDefinition definition = new EntityDefinition();
        definition.setName("NamedQueryPerson" + suffix());
        definition.setOrganizationId(TestDataService.SAMPLE_ORG_ID);
        definition.setApplicationId(TestDataService.SAMPLE_APP_ID);
        definition.setProjectId(TestDataService.SAMPLE_PROJECT_ID);
        definition.setDescription("Defines a Person that a named query renames");
        definition.setSchema(testDataService.createPersonSchema(MultiTenancyType.SHARED));
        return await(runAsOrganization(() -> applicationService.createApplicationIfNotExist(TestDataService.SAMPLE_APP_ID, "Sample application", null)
                                                                 .compose(v -> entityDefinitionService.create(definition))
                                                                 .compose(saved -> entityDefinitionService.publish(saved.getId()).map(saved))));
    }

    private ApplicationParticipantIdentity endUser(String tenantId) throws Exception {
        ApplicationParticipantIdentity user = new ApplicationParticipantIdentity();
        user.setEmail("end-user-" + suffix() + "@kinotic.test");
        user.setDisplayName("End User");
        user.setOrganizationId(TEST_ORG_ID);
        user.setApplicationId(TEST_APP_ID);
        user.setTenantId(tenantId);
        return await(identityService.createUser(user, "End-User-1"));
    }
}
