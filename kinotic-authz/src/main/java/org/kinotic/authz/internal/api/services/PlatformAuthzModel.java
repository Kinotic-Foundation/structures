package org.kinotic.authz.internal.api.services;

import dev.openfga.sdk.api.model.Metadata;
import dev.openfga.sdk.api.model.ObjectRelation;
import dev.openfga.sdk.api.model.RelationMetadata;
import dev.openfga.sdk.api.model.RelationReference;
import dev.openfga.sdk.api.model.TupleToUserset;
import dev.openfga.sdk.api.model.TypeDefinition;
import dev.openfga.sdk.api.model.Userset;
import dev.openfga.sdk.api.model.Usersets;
import dev.openfga.sdk.api.model.WriteAuthorizationModelRequest;
import org.kinotic.authz.api.model.AuthzRelation;
import org.kinotic.authz.api.model.AuthzType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.kinotic.authz.api.model.AuthzRelation.ADMIN;
import static org.kinotic.authz.api.model.AuthzRelation.CAN_DELETE;
import static org.kinotic.authz.api.model.AuthzRelation.CAN_EDIT;
import static org.kinotic.authz.api.model.AuthzRelation.CAN_READ;
import static org.kinotic.authz.api.model.AuthzRelation.CAN_REGISTER_SERVICES;
import static org.kinotic.authz.api.model.AuthzRelation.EDITOR;
import static org.kinotic.authz.api.model.AuthzRelation.MEMBER;
import static org.kinotic.authz.api.model.AuthzRelation.RUNTIME;
import static org.kinotic.authz.api.model.AuthzRelation.VIEWER;
import static org.kinotic.authz.api.model.AuthzType.APPLICATION;
import static org.kinotic.authz.api.model.AuthzType.MACHINE;
import static org.kinotic.authz.api.model.AuthzType.MEMBERSHIP;
import static org.kinotic.authz.api.model.AuthzType.ORGANIZATION;
import static org.kinotic.authz.api.model.AuthzType.PLATFORM;
import static org.kinotic.authz.api.model.AuthzType.PROJECT;

/**
 * The platform's authorization model, in OpenFGA's DSL:
 * <pre>
 * model
 *   schema 1.1
 *
 * type membership
 * type machine
 *
 * type platform
 *   relations
 *     define admin: [membership]
 *
 * type organization
 *   relations
 *     define platform: [platform]
 *     define admin: [membership] or admin from platform
 *     define member: [membership] or admin
 *     define can_read: member
 *     define can_edit: admin
 *     define can_delete: admin
 *
 * type application
 *   relations
 *     define organization: [organization]
 *     define admin: [membership] or admin from organization
 *     define editor: [membership] or admin
 *     define viewer: [membership] or editor or member from organization
 *     define runtime: [machine]
 *     define can_read: viewer
 *     define can_edit: editor
 *     define can_delete: admin
 *     define can_register_services: runtime or editor
 *
 * type project
 *   relations
 *     define application: [application]
 *     define admin: [membership] or admin from application
 *     define editor: [membership, machine] or admin or editor from application
 *     define viewer: [membership] or editor or viewer from application
 *     define can_read: viewer
 *     define can_edit: editor
 *     define can_delete: admin
 * </pre>
 */
final class PlatformAuthzModel {

    private static final String SCHEMA_VERSION = "1.1";

    /**
     * The model as the engine writes it.
     */
    static final WriteAuthorizationModelRequest DEFINITION = new WriteAuthorizationModelRequest()
            .schemaVersion(SCHEMA_VERSION)
            .typeDefinitions(List.of(
                    type(MEMBERSHIP),
                    type(MACHINE),
                    type(PLATFORM,
                         relation(ADMIN, List.of(MEMBERSHIP))),
                    type(ORGANIZATION,
                         relation(AuthzRelation.PLATFORM, List.of(PLATFORM)),
                         relation(ADMIN, List.of(MEMBERSHIP), from(AuthzRelation.PLATFORM, ADMIN)),
                         relation(MEMBER, List.of(MEMBERSHIP), computed(ADMIN)),
                         relation(CAN_READ, computed(MEMBER)),
                         relation(CAN_EDIT, computed(ADMIN)),
                         relation(CAN_DELETE, computed(ADMIN))),
                    type(APPLICATION,
                         relation(AuthzRelation.ORGANIZATION, List.of(ORGANIZATION)),
                         relation(ADMIN, List.of(MEMBERSHIP), from(AuthzRelation.ORGANIZATION, ADMIN)),
                         relation(EDITOR, List.of(MEMBERSHIP), computed(ADMIN)),
                         relation(VIEWER, List.of(MEMBERSHIP), computed(EDITOR), from(AuthzRelation.ORGANIZATION, MEMBER)),
                         relation(RUNTIME, List.of(MACHINE)),
                         relation(CAN_READ, computed(VIEWER)),
                         relation(CAN_EDIT, computed(EDITOR)),
                         relation(CAN_DELETE, computed(ADMIN)),
                         relation(CAN_REGISTER_SERVICES, computed(RUNTIME), computed(EDITOR))),
                    type(PROJECT,
                         relation(AuthzRelation.APPLICATION, List.of(APPLICATION)),
                         relation(ADMIN, List.of(MEMBERSHIP), from(AuthzRelation.APPLICATION, ADMIN)),
                         relation(EDITOR, List.of(MEMBERSHIP, MACHINE), computed(ADMIN), from(AuthzRelation.APPLICATION, EDITOR)),
                         relation(VIEWER, List.of(MEMBERSHIP), computed(EDITOR), from(AuthzRelation.APPLICATION, VIEWER)),
                         relation(CAN_READ, computed(VIEWER)),
                         relation(CAN_EDIT, computed(EDITOR)),
                         relation(CAN_DELETE, computed(ADMIN)))));

    private PlatformAuthzModel() {
    }

    // A relation: assigned to the subject types it lists, or derived from the given usersets, or both
    private record Relation(AuthzRelation name, List<AuthzType> assignableTo, List<Userset> derivedFrom) {
    }

    private static Relation relation(AuthzRelation name, List<AuthzType> assignableTo, Userset... derivedFrom) {
        return new Relation(name, assignableTo, Arrays.asList(derivedFrom));
    }

    private static Relation relation(AuthzRelation name, Userset... derivedFrom) {
        return new Relation(name, List.of(), Arrays.asList(derivedFrom));
    }

    private static TypeDefinition type(AuthzType type, Relation... relations) {
        TypeDefinition ret = new TypeDefinition().type(type.wireName());
        if (relations.length > 0) {
            Map<String, Userset> rewrites = new LinkedHashMap<>();
            Map<String, RelationMetadata> metadata = new LinkedHashMap<>();
            for (Relation relation : relations) {
                List<Userset> sources = new ArrayList<>();
                List<RelationReference> directTypes = new ArrayList<>();
                if (!relation.assignableTo().isEmpty()) {
                    sources.add(new Userset()._this(Map.of()));
                    for (AuthzType subject : relation.assignableTo()) {
                        directTypes.add(new RelationReference().type(subject.wireName()));
                    }
                }
                sources.addAll(relation.derivedFrom());
                // the engine rejects a union of one, so a relation with a single source is that source itself
                rewrites.put(relation.name().wireName(),
                             sources.size() == 1 ? sources.getFirst() : new Userset().union(new Usersets().child(sources)));
                metadata.put(relation.name().wireName(), new RelationMetadata().directlyRelatedUserTypes(directTypes));
            }
            ret.relations(rewrites).metadata(new Metadata().relations(metadata));
        }
        return ret;
    }

    private static Userset computed(AuthzRelation relation) {
        return new Userset().computedUserset(new ObjectRelation()._object("").relation(relation.wireName()));
    }

    // The relation held on the object the tupleset relation points at, such as admin of the organization
    private static Userset from(AuthzRelation tupleset, AuthzRelation relation) {
        return new Userset().tupleToUserset(new TupleToUserset()
                                                    .tupleset(new ObjectRelation()._object("").relation(tupleset.wireName()))
                                                    .computedUserset(new ObjectRelation()._object("").relation(relation.wireName())));
    }
}
