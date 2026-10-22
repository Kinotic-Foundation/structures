package org.kinotic.domain.api.utils;

import com.github.slugify.Slugify;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.kinotic.authz.api.model.Grant;
import org.kinotic.authz.api.model.RelationshipTuple;
import org.kinotic.authz.api.model.Resource;
import org.kinotic.authz.api.model.RoleDefinition;
import org.kinotic.authz.api.model.Subject;
import org.kinotic.authz.api.model.SubjectKind;
import org.kinotic.core.api.security.Participant;
import org.kinotic.core.api.security.ParticipantConstants;
import org.kinotic.core.api.utils.ZoneUtil;
import org.kinotic.domain.api.model.ApplicationKey;
import org.kinotic.domain.api.model.OrganizationScoped;
import org.kinotic.domain.api.model.persistence.EntityDefinition;
import org.kinotic.domain.api.model.persistence.idl.decorators.MultiTenancyType;
import org.kinotic.domain.api.model.security.identity.DelegatingParticipantIdentity;
import org.kinotic.domain.api.model.security.identity.MachineParticipantIdentity;
import org.kinotic.domain.api.model.security.identity.ApplicationParticipantIdentity;
import org.kinotic.domain.api.model.security.identity.OrganizationMembership;
import org.kinotic.domain.api.model.security.identity.OrganizationParticipantIdentity;
import org.kinotic.domain.api.model.security.identity.ParticipantIdentity;
import org.kinotic.domain.api.model.security.identity.ParticipantIdentityType;
import org.kinotic.domain.api.model.security.identity.ScopedParticipantIdentity;
import org.kinotic.domain.api.model.security.identity.UserParticipantIdentity;
import org.kinotic.domain.api.model.security.participant.ApplicationParticipant;
import org.kinotic.domain.api.model.security.participant.DefaultApplicationParticipant;
import org.kinotic.domain.api.model.security.participant.DefaultOrganizationParticipant;
import org.kinotic.domain.api.model.security.participant.DefaultSystemParticipant;
import org.kinotic.domain.api.model.security.participant.ParticipantScope;
import org.kinotic.domain.api.model.security.participant.ScopedParticipant;
import org.kinotic.idl.api.utils.AuthzUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.HexFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Predicate;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Created By Navíd Mitchell 🤪on 2/13/26
 */
public class DomainUtil {

    /**
     * The zone for platform services organizations use to manage the system, such as member,
     * application, and entity definition management
     */
    public static final String MANAGEMENT_API_ZONE = "management-api";

    /**
     * The zone for the platform's application facing data services, such as entity persistence
     * and named query execution
     */
    public static final String APP_API_ZONE = "app-api";

    /**
     * The zone for services internal to the platform, only reachable by system participants
     */
    public static final String SYSTEM_API_ZONE = "system-api";

    /**
     * The leading label of application zones, which follow the form app.&lt;organizationId&gt;.&lt;applicationId&gt;
     */
    public static final String APP_ZONE_PREFIX = "app";

    /**
     * The zone an application's own services are addressed in, {@code app.<organizationId>.<applicationId>},
     * which the application's runtimes and users reach and nothing outside the application does.
     *
     * @param organizationId the application's organization
     * @param applicationId  the application
     * @return the zone
     */
    public static String applicationZone(String organizationId, String applicationId) {
        return APP_ZONE_PREFIX + "." + organizationId + "." + applicationId;
    }

    /**
     * The participant metadata key naming the owner a delegate acts for, whose authority the delegate holds.
     */
    public static final String ON_BEHALF_OF_METADATA_KEY = "onBehalfOf";

    // The authorization types whose ids are unique only within an organization, qualified by it in the stores
    private static final Set<String> ORGANIZATION_LOCAL_TYPES = Set.of(AuthzUtil.APPLICATION_TYPE, AuthzUtil.PROJECT_TYPE);

    /**
     * The user a participant acts as in the authorization stores, in {@code user:id} form. An organization user acts
     * as the membership it signed in through, {@link #authzMember}, so a grant made in one organization never applies
     * in another; a delegate acts as its owner would; a system user, a machine and an application's user act as
     * themselves.
     *
     * @param participant the participant making a call
     * @return the user whose grants answer for it
     */
    public static String authzUser(Participant participant) {
        Map<String, String> metadata = participant.getMetadata() != null ? participant.getMetadata() : Map.of();
        String owner = metadata.get(ON_BEHALF_OF_METADATA_KEY);
        String id = owner != null ? owner : participant.getId();
        String ret;
        String organizationId = scopeOf(participant).organizationId();
        if (organizationId == null
                || participant instanceof ApplicationParticipant
                || ParticipantConstants.PARTICIPANT_TYPE_MACHINE.equals(metadata.get(ParticipantConstants.PARTICIPANT_TYPE_METADATA_KEY))) {
            ret = AuthzUtil.object(AuthzUtil.USER_TYPE, id);
        } else {
            ret = authzMember(id, organizationId);
        }
        return ret;
    }

    /**
     * The user an organization user's membership is in the authorization stores,
     * {@code user:<organizationUserId>@<organizationId>}.
     *
     * @param organizationUserId the organization user
     * @param organizationId     the organization it is a member of
     * @return the membership's user
     */
    public static String authzMember(String organizationUserId, String organizationId) {
        Validate.notBlank(organizationUserId, "organizationUserId cannot be blank");
        Validate.notBlank(organizationId, "organizationId cannot be blank");
        return AuthzUtil.object(AuthzUtil.USER_TYPE, organizationUserId + "@" + organizationId);
    }

    /**
     * The identity a user id of the authorization stores names: the organization user of a membership's
     * {@code <organizationUserId>@<organizationId>}, and any other user id as it is.
     *
     * @param userId the id of a {@code user} object
     * @return the identity's id
     */
    public static String identityOf(String userId) {
        int at = userId.indexOf('@');
        return at < 0 ? userId : userId.substring(0, at);
    }

    /**
     * The id an object of the type has in the authorization stores. An application's and a project's id is unique
     * only within its organization, so theirs is qualified as {@code <organizationId>.<id>}; every other type's id
     * is unique on its own and kept as it is.
     *
     * @param type           the object's type
     * @param organizationId the organization the object belongs to; required for an application or a project
     * @param id             the object's id as its organization knows it
     * @return the object's id in the stores
     * @throws IllegalArgumentException when an application or a project is named without an organization
     */
    public static String authzId(String type, String organizationId, String id) {
        Validate.notBlank(id, "id cannot be blank");
        String ret = id;
        if (ORGANIZATION_LOCAL_TYPES.contains(type)) {
            Validate.isTrue(StringUtils.isNotBlank(organizationId), "an organization is required to name the %s %s", type, id);
            ret = organizationId + "." + id;
        }
        return ret;
    }

    /**
     * The id an organization knows an object of the type by, given its id in the authorization stores: the
     * inverse of {@link #authzId}.
     *
     * @param type    the object's type
     * @param authzId the object's id in the stores
     * @return the object's id within its organization
     */
    public static String localId(String type, String authzId) {
        // an organization id holds no '.', so the first one ends it
        return ORGANIZATION_LOCAL_TYPES.contains(type) ? authzId.substring(authzId.indexOf('.') + 1) : authzId;
    }

    /**
     * The id an application has in the authorization stores, which also names the application's own store.
     *
     * @param organizationId the application's organization
     * @param applicationId  the application's id within it
     * @return {@code <organizationId>.<applicationId>}
     */
    public static String authzApplicationId(String organizationId, String applicationId) {
        return authzId(AuthzUtil.APPLICATION_TYPE, organizationId, applicationId);
    }

    /**
     * The application object of the authorization stores, {@code application:<organizationId>.<applicationId>}.
     *
     * @param organizationId the application's organization
     * @param applicationId  the application's id within it
     * @return the object
     */
    public static String authzApplication(String organizationId, String applicationId) {
        return AuthzUtil.object(AuthzUtil.APPLICATION_TYPE, authzApplicationId(organizationId, applicationId));
    }

    /**
     * The tuple naming an object's parent, which a grant made on the parent reaches the object through:
     * {@code <parent> <parent type> <child>}.
     *
     * @param parent the parent object, in {@code type:id} form
     * @param child  the child object, in {@code type:id} form
     * @return the containment tuple
     */
    public static RelationshipTuple containment(String parent, String child) {
        return new RelationshipTuple(parent, AuthzUtil.typeOf(parent), child);
    }

    /**
     * The tuples an application's user holds in its application's own store: the user is an end user of the
     * application, and, for a user of a tenant, the tenant is placed under the application with the user as a
     * member. The system's store holds the first of them, the end user tuple, alone.
     *
     * @param user an application's user
     * @return the tuples, the end user tuple first
     */
    public static List<RelationshipTuple> applicationUserTuples(ApplicationParticipantIdentity user) {
        String me = AuthzUtil.object(AuthzUtil.USER_TYPE, user.getId());
        String application = authzApplication(user.getOrganizationId(), user.getApplicationId());
        List<RelationshipTuple> ret = new ArrayList<>(List.of(new RelationshipTuple(me, AuthzUtil.END_USER_RELATION, application)));
        if (user.getTenantId() != null) {
            String tenant = AuthzUtil.object(AuthzUtil.TENANT_TYPE, user.getTenantId());
            ret.add(containment(application, tenant));
            ret.add(new RelationshipTuple(me, AuthzUtil.MEMBER_RELATION, tenant));
        }
        return ret;
    }

    /**
     * The two edges of a definition within a tenant, the object a tenant's user is checked on for the definition's
     * rows: the definition's, and the tenant's. No store holds tuples for the object, so a check on it is given
     * the edges.
     *
     * @param definitionId the definition's id
     * @param tenantId     the tenant's id within its application
     * @return the edges to give the check
     */
    public static List<RelationshipTuple> tenantDefinitionEdges(String definitionId, String tenantId) {
        String object = AuthzUtil.object(AuthzUtil.TENANT_DEFINITION_TYPE, AuthzUtil.tenantDefinitionId(definitionId, tenantId));
        return List.of(new RelationshipTuple(AuthzUtil.object(AuthzUtil.ENTITY_DEFINITION_TYPE, definitionId), AuthzUtil.DEFINITION_RELATION, object),
                       new RelationshipTuple(AuthzUtil.object(AuthzUtil.TENANT_TYPE, tenantId), AuthzUtil.TENANT_TYPE, object));
    }

    /**
     * The object a resource named as its organization knows it is in the authorization stores, in {@code type:id}
     * form.
     *
     * @param organizationId the organization the resource belongs to
     * @param resource       the resource
     * @return the object
     */
    public static String authzObject(String organizationId, Resource resource) {
        return AuthzUtil.object(resource.type(), authzId(resource.type(), organizationId, resource.id()));
    }

    /**
     * A grant read from the authorization stores, named as the resource's organization knows it: its resource by
     * the resource's local id, and a member holding it by the member's organization user.
     *
     * @param grant the grant as the stores hold it
     * @return the grant with local ids
     */
    public static Grant localGrant(Grant grant) {
        Resource resource = grant.resource();
        Subject subject = grant.subject();
        if (subject.kind() == SubjectKind.USER) {
            subject = new Subject(SubjectKind.USER, identityOf(subject.id()));
        }
        return new Grant(grant.id(), grant.roleId(), subject, new Resource(resource.type(), localId(resource.type(), resource.id())));
    }

    /**
     * Confirms a role is among those a grant can name.
     *
     * @param roles  the roles a grant can name
     * @param roleId the role asked for
     * @throws IllegalArgumentException when none of the roles has the id
     */
    public static void requireRole(List<RoleDefinition> roles, String roleId) {
        if (roles.stream().noneMatch(role -> role.id().equals(roleId))) {
            throw new IllegalArgumentException("No role a grant here can name has id " + roleId);
        }
    }

    /**
     * The prefix of the Elasticsearch indices the platform creates, including the index that holds
     * the items of each published EntityDefinition
     */
    public static final String INDEX_PREFIX = "kinotic_";

    /** The platform's id wherever a scope's id names an owner, such as a secret scope; no organization may take it. */
    public static final String PLATFORM_ID = "system";

    // no organization carries the platform's name: an id beginning with it would put that name on a customer's
    // hosts, <org>--<app> under the apps-api domain and *.<org> under the sites domain
    private static final String RESERVED_ID_PREFIX = "kinotic";
    // the labels the platform itself holds under its domains, which an organization's subdomain must never shadow;
    // the path segments the organization login's own routes claim under /api/auth/org/login/, which an
    // organization's id would be mistaken for; and the platform's own id
    private static final Set<String> RESERVED_ORGANIZATION_IDS = Set.of("www", "api", "apps-api", "system-api", "portal",
                                                                         "console", "mail", "admin", "login", "status", "docs",
                                                                         "find", "social", "sso", "providers", "choices",
                                                                         PLATFORM_ID);

    // Project ids may start with a digit because they embed application ids, which may
    // themselves start with a digit
    private static final Pattern ProjectIdPattern = Pattern.compile("^[a-z0-9][a-z0-9.-]*$");
    private static final Pattern EntityDefinitionNamePattern = Pattern.compile("^[A-Za-z_][A-Za-z0-9_]*$");
    private static final BCryptPasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    // Dash separator, not underscore: slugified ids become zone labels, and underscores are
    // illegal in a URI host (CRIs are valid URIs by convention)
    private static final Slugify SLUGIFY = Slugify.builder().build();

    /**
     * Validates that the given application id contains only lowercase letters, digits, and
     * interior dashes, never {@code --}, and is not a zone label the platform reserves for itself.
     *
     * @param applicationId to validate
     * @throws IllegalArgumentException if the application id is null, invalid, or reserved
     */
    public static void validateApplicationId(String applicationId) {
        if (applicationId == null) {
            throw new IllegalArgumentException("Application Id must not be null");
        }
        validateZoneLabelId(applicationId);
    }

    /**
     * Validates that the given organization id contains only lowercase letters, digits, and
     * interior dashes, never {@code --}, is not a zone label or host label the platform reserves,
     * and does not begin with the prefix reserved for the platform's own organizations.
     *
     * @param organizationId to validate
     * @throws IllegalArgumentException if the organization id is null, invalid, or reserved
     */
    public static void validateOrganizationId(String organizationId) {
        if (organizationId == null) {
            throw new IllegalArgumentException("Organization Id must not be null");
        }
        validateZoneLabelId(organizationId);
        // an organization id of "xn" would start its applications' host labels with "xn--", which
        // DNS reserves for internationalized labels
        Validate.isTrue(!"xn".equals(organizationId), "Organization Id '%s' is reserved", organizationId);
        Validate.isTrue(!organizationId.startsWith(RESERVED_ID_PREFIX),
                        "Organization Id '%s' is reserved by the platform", organizationId);
        Validate.isTrue(!RESERVED_ORGANIZATION_IDS.contains(organizationId),
                        "Organization Id '%s' is reserved by the platform", organizationId);
    }

    /**
     * Validates that the given UI name, the name of a project's UI package, contains only lowercase
     * letters, digits, and interior dashes, never {@code --}.
     *
     * @param uiName to validate
     * @throws IllegalArgumentException if the UI name is null or invalid
     */
    public static void validateUiName(String uiName) {
        if (uiName == null) {
            throw new IllegalArgumentException("UI name must not be null");
        }
        validateHostLabelPart(uiName);
    }

    /**
     * Validates that the given tenant id contains only lowercase letters, digits, and interior dashes,
     * never {@code --}, and is at most {@link HostLabelUtil#MAX_TENANT_ID_LENGTH} characters, since it is the
     * first name of the tenant's site labels.
     *
     * @param tenantId to validate
     * @throws IllegalArgumentException if the tenant id is null, invalid, or too long
     */
    public static void validateTenantId(String tenantId) {
        if (tenantId == null) {
            throw new IllegalArgumentException("Tenant Id must not be null");
        }
        validateHostLabelPart(tenantId);
        Validate.isTrue(tenantId.length() <= HostLabelUtil.MAX_TENANT_ID_LENGTH,
                        "Tenant Id '%s' is longer than %d characters; shorten the tenant name", tenantId, HostLabelUtil.MAX_TENANT_ID_LENGTH);
    }

    private static void validateZoneLabelId(String id) {
        validateHostLabelPart(id);
        Validate.isTrue(!SYSTEM_API_ZONE.equals(id), "Id '%s' is reserved by the platform", id);
    }

    // ids and UI names are joined with the separator into one host label, so a name holding it would let
    // two applications form the same label: org "a" with app "b--c" and org "a--b" with app "c"
    private static void validateHostLabelPart(String name) {
        ZoneUtil.validateLabel(name);
        Validate.isTrue(!name.contains(HostLabelUtil.HOST_LABEL_SEPARATOR), "'%s' must not contain '%s'", name, HostLabelUtil.HOST_LABEL_SEPARATOR);
    }

    public static void validateProjectId(String projectId){
        if(projectId == null){
            throw new IllegalArgumentException("Project Id must not be null");
        }
        if (!ProjectIdPattern.matcher(projectId).matches()){
            throw new IllegalArgumentException("Kinotic Project Id Invalid, first character must be a " +
                                                       "letter or number. And contain only letters, numbers, periods or dashes. Got "+ projectId);
        }
    }

    /**
     * Creates the id of an {@link EntityDefinition}, {@code <organizationId>.<applicationId>.<name>} lowercased.
     *
     * @param applicationKey       the application the definition belongs to
     * @param entityDefinitionName the definition's name
     * @return the {@link EntityDefinition} id
     */
    public static String createEntityDefinitionId(ApplicationKey applicationKey, String entityDefinitionName){
        return (applicationKey.organizationId() + "." + applicationKey.applicationId() + "." + entityDefinitionName).toLowerCase();
    }

    /**
     * The Elasticsearch {@code _id} an entity's item is stored under. A {@link MultiTenancyType#SHARED} entity
     * prefixes the item's id with its tenant, so two tenants may each hold an item of the same id.
     *
     * @param multiTenancyType how the entity separates its tenants
     * @param tenantId         the item's tenant, required for a {@link MultiTenancyType#SHARED} entity
     * @param id               the item's id
     * @return the document id
     */
    public static String createEntityDocumentId(MultiTenancyType multiTenancyType, String tenantId, String id){
        return multiTenancyType == MultiTenancyType.SHARED ? tenantId + "-" + id : id;
    }

    /**
     * Function will validate a {@link EntityDefinition}
     *
     * @param entityDefinition to validate
     * @throws IllegalArgumentException will be thrown if the {@link EntityDefinition} is invalid
     */
    public static void validateEntityDefinition(EntityDefinition entityDefinition){

        validateEntityDefinitionName(entityDefinition.getName());

        validateApplicationId(entityDefinition.getApplicationId());

        validateProjectId(entityDefinition.getProjectId());

        if (entityDefinition.getSchema() == null) {
            throw new IllegalArgumentException("EntityDefinition schema must not be null");
        }
    }

    /**
     * Function will validate the {@link EntityDefinition} name
     *
     * @param entityDefinitionName to validate
     * @throws IllegalArgumentException will be thrown if the {@link EntityDefinition} name is invalid
     */
    public static void validateEntityDefinitionName(String entityDefinitionName){
        if(entityDefinitionName == null){
            throw new IllegalArgumentException("EntityDefinition name must not be null");
        }
        if (!EntityDefinitionNamePattern.matcher(entityDefinitionName).matches()){
            throw new IllegalArgumentException("EntityDefinition Name Invalid, first character must be a " +
                                               "letter, number or underscore. And contain only letters, numbers or underscores. Got "+ entityDefinitionName);
        }
    }

    /**
     * Function will validate the property name
     *
     * @param propertyName to validate
     * @throws IllegalArgumentException will be thrown if the property name is invalid
     */
    public static void validatePropertyName(String propertyName){
        if(propertyName == null){
            throw new IllegalArgumentException("Property Name must not be null");
        }
        if(propertyName.length() > 255){
            throw new IllegalArgumentException("Property Name cannot have more than 255 characters");
        }
        if(!EntityDefinitionNamePattern.matcher(propertyName).matches()){
            throw new IllegalArgumentException("Property Name Invalid, first character must be a " +
                                               "letter, number or underscore. And contain only letters, numbers or underscores. Got "+ propertyName);
        }
    }

    /**
     * Derives an id from the given text, slugified to lowercase letters, digits, and interior
     * dashes.
     *
     * @param text to derive the id from
     * @return the derived id
     * @throws IllegalArgumentException if the text is blank or has no usable characters
     */
    public static String slugifyId(String text) {
        Validate.notBlank(text, "Cannot derive an id from a blank value");
        // Slugify keeps separators it produced at the edges ("Acme Inc." -> "acme-inc-"), but
        // the id must start and end alphanumeric
        String id = StringUtils.strip(SLUGIFY.slugify(text), "-");
        Validate.notEmpty(id, "Cannot derive an id from '%s', it has no usable characters", text);
        // Guards against a Slugify behavior change; the version in use only emits lowercase
        // letters, digits, and dashes
        ZoneUtil.validateLabel(id);
        return id;
    }

    /**
     * Normalizes an email address to its canonical stored form: trimmed and lowercased
     * with {@link Locale#ROOT} (locale-sensitive lowercasing corrupts emails under e.g.
     * a Turkish default locale). Emails are matched with exact-match term filters, so
     * every write and every lookup must agree on this one form.
     *
     * @param email to normalize, must not be blank
     * @return the normalized email
     * @throws IllegalArgumentException if {@code email} is blank
     * @throws NullPointerException if {@code email} is null
     */
    public static String normalizeEmail(String email) {
        Validate.notBlank(email, "email cannot be blank");
        return email.trim().toLowerCase(Locale.ROOT);
    }

    /**
     * Hashes the given raw password using BCrypt.
     *
     * @param rawPassword to hash
     * @return the BCrypt hash of the given password
     */
    public static String hashPassword(String rawPassword) {
        return PASSWORD_ENCODER.encode(rawPassword);
    }

    /**
     * Verifies that the given raw password matches the given BCrypt hash.
     *
     * @param rawPassword to verify
     * @param hash        the BCrypt hash to verify against
     * @return true if the password matches the hash
     */
    public static boolean verifyPassword(String rawPassword, String hash) {
        return PASSWORD_ENCODER.matches(rawPassword, hash);
    }

    /**
     * Generates a high-entropy, URL-safe token from {@code numBytes} of secure random data.
     * Used for bearer secrets (device codes, refresh tokens) whose plaintext is shown to a
     * client once and only ever stored as a hash.
     *
     * @param numBytes number of random bytes to draw before base64url encoding
     * @return a base64url-encoded random token without padding
     */
    public static String generateUrlSafeToken(int numBytes) {
        byte[] bytes = new byte[numBytes];
        SECURE_RANDOM.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    /**
     * Returns the SHA-256 hash of {@code value} as a lowercase hex string. Suitable for
     * at-rest storage of high-entropy bearer tokens — the entropy makes a fast unsalted
     * digest safe, and the deterministic output allows lookup by hash.
     *
     * @param value the value to hash
     * @return the SHA-256 digest as lowercase hex
     */
    public static String sha256Hex(String value) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return HexFormat.of().formatHex(digest.digest(value.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 is not available", e);
        }
    }

    /**
     * Builds the {@link Participant} security identity for an authenticated {@link ParticipantIdentity}: a
     * {@link DefaultSystemParticipant} for a system user, and for a {@link ScopedParticipantIdentity} the participant
     * of the layer its scope is:
     * <ul>
     *   <li>SYSTEM → {@link DefaultSystemParticipant}</li>
     *   <li>ORGANIZATION → {@link DefaultOrganizationParticipant}</li>
     *   <li>APPLICATION → {@link DefaultApplicationParticipant}, carrying the {@code tenantId} too</li>
     * </ul>
     * An organization user acts through one of its memberships, by
     * {@link #createParticipant(OrganizationParticipantIdentity, OrganizationMembership)}.
     *
     * @param identity the authenticated identity, other than an organization user
     * @return the typed participant for the given identity
     */
    public static Participant createParticipant(ParticipantIdentity identity) {
        Validate.isTrue(!(identity instanceof OrganizationParticipantIdentity),
                        "An organization user acts through one of its memberships");
        ParticipantScope scope = scopeOf(identity);
        Map<String, String> metadata = participantMetadata(identity);
        Participant ret;
        if (scope.organizationId() == null) {
            ret = DefaultSystemParticipant.builder()
                                          .id(identity.getId())
                                          .metadata(metadata)
                                          .roles(List.of())
                                          .build();
        } else if (scope.applicationId() == null) {
            ret = DefaultOrganizationParticipant.builder()
                                                .id(identity.getId())
                                                .organizationId(scope.organizationId())
                                                .metadata(metadata)
                                                .roles(List.of())
                                                .build();
        } else {
            ret = DefaultApplicationParticipant.builder()
                                               .id(identity.getId())
                                               .organizationId(scope.organizationId())
                                               .applicationId(scope.applicationId())
                                               .tenantId(scope.tenantId())
                                               .metadata(metadata)
                                               .roles(List.of())
                                               .build();
        }
        return ret;
    }

    /**
     * Requires the participant to be a person (participant type {@code user}), rejecting
     * delegates and every other kind. Guards operations that grant or revoke authority —
     * consent, delegate management — which only the human owner may perform.
     *
     * @param participant the calling participant
     * @throws IllegalArgumentException if the participant is not a user
     */
    public static void requireUserParticipant(Participant participant) {
        String type = participant.getMetadata().get(ParticipantConstants.PARTICIPANT_TYPE_METADATA_KEY);
        if (!ParticipantConstants.PARTICIPANT_TYPE_USER.equals(type)) {
            throw new IllegalArgumentException("Only a signed-in user may perform this action");
        }
    }

    /**
     * Narrows a loaded identity to the expected subtype and confirms the caller owns it.
     * Unknown ids, other subtypes, and identities of other owners all fail with the same
     * message — no existence oracle.
     *
     * @param identity        the identity a lookup returned, possibly null
     * @param type            the subtype the operation manages
     * @param ownedByCaller   whether the identity belongs to the caller
     * @param notFoundMessage the single failure message for every miss
     * @return the identity narrowed to {@code type}
     * @throws IllegalArgumentException with {@code notFoundMessage} on any miss
     */
    public static <T extends ParticipantIdentity> T requireOwned(ParticipantIdentity identity,
                                                                 Class<T> type,
                                                                 Predicate<T> ownedByCaller,
                                                                 String notFoundMessage) {
        if (!type.isInstance(identity) || !ownedByCaller.test(type.cast(identity))) {
            throw new IllegalArgumentException(notFoundMessage);
        }
        return type.cast(identity);
    }

    /**
     * Confirms an organization-scoped entity a lookup returned belongs to the caller's
     * organization. A missing entity and another organization's fail with the same message —
     * no existence oracle.
     *
     * @param entity          the entity a lookup returned, possibly null
     * @param organizationId  the caller's organization
     * @param notFoundMessage the single failure message for every miss
     * @return the entity
     * @throws IllegalArgumentException with {@code notFoundMessage} on any miss
     */
    public static <T extends OrganizationScoped<?>> T requireOwned(T entity, String organizationId, String notFoundMessage) {
        if (entity == null || !organizationId.equals(entity.getOrganizationId())) {
            throw new IllegalArgumentException(notFoundMessage);
        }
        return entity;
    }

    /**
     * Renders a structural scope for logs and error messages: {@code SYSTEM},
     * {@code ORGANIZATION/<orgId>}, or {@code APPLICATION/<orgId>/<appId>}.
     */
    public static String describeScope(String organizationId, String applicationId) {
        if (organizationId == null && applicationId == null) return "SYSTEM";
        if (applicationId == null) return "ORGANIZATION/" + organizationId;
        return "APPLICATION/" + organizationId + "/" + applicationId;
    }

    /**
     * The participant organization user {@code user} acts as through {@code membership}: an
     * {@link OrganizationParticipant} of the membership's organization. The participant's id is the user's, so what
     * the person creates is attributed to them whichever organization they act in.
     */
    public static Participant createParticipant(OrganizationParticipantIdentity user, OrganizationMembership membership) {
        Validate.isTrue(user.getId().equals(membership.getOrganizationUserId()), "The membership is not the user's");
        return DefaultOrganizationParticipant.builder()
                                             .id(user.getId())
                                             .organizationId(membership.getOrganizationId())
                                             .metadata(participantMetadata(user))
                                             .roles(List.of())
                                             .build();
    }

    /** The scope a participant acts in: its own for a scoped participant, the platform's (all null) otherwise. */
    public static ParticipantScope scopeOf(Participant participant) {
        return participant instanceof ScopedParticipant scoped ? scoped.getScope() : new ParticipantScope(null, null, null);
    }

    /**
     * The scope ids an identity carries: a {@link ScopedParticipantIdentity}'s scope, and none (all {@code null}) for
     * a system user or an organization user.
     */
    public static ParticipantScope scopeOf(ParticipantIdentity identity) {
        return identity instanceof ScopedParticipantIdentity scoped ? scoped.scope() : new ParticipantScope(null, null, null);
    }

    private static Map<String, String> participantMetadata(ParticipantIdentity identity) {
        Map<String, String> metadata = new HashMap<>();
        metadata.put(ParticipantConstants.PARTICIPANT_TYPE_METADATA_KEY, participantTypeFor(identity.getType()));
        switch (identity) {
            case UserParticipantIdentity user -> {
                metadata.put("email", user.getEmail());
                metadata.put("displayName",
                             user.getDisplayName() != null ? user.getDisplayName() : user.getEmail());
            }
            case DelegatingParticipantIdentity delegate -> {
                metadata.put(ON_BEHALF_OF_METADATA_KEY, delegate.getOwnerId());
                metadata.put("displayName",
                             delegate.getDisplayName() != null ? delegate.getDisplayName() : delegate.getId());
            }
            case MachineParticipantIdentity machine ->
                    metadata.put("displayName", machine.getDisplayName());
        }
        return Map.copyOf(metadata);
    }

    private static String participantTypeFor(ParticipantIdentityType type) {
        return switch (type) {
            case SYSTEM_USER, ORGANIZATION_USER, APPLICATION_USER -> ParticipantConstants.PARTICIPANT_TYPE_USER;
            case DELEGATE -> ParticipantConstants.PARTICIPANT_TYPE_DELEGATE;
            case MACHINE -> ParticipantConstants.PARTICIPANT_TYPE_MACHINE;
        };
    }

}
