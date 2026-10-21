package org.kinotic.authz.api.model;

import java.util.Locale;

/**
 * The relations of the platform's authorization model. The containment relations, the roles and the machine
 * relations are written as relationships; the permissions are derived from them and are what a check asks for.
 */
public enum AuthzRelation {

    /** Contains an organization in the platform. */
    PLATFORM,
    /** Contains an application in its organization. */
    ORGANIZATION,
    /** Contains a project in its application. */
    APPLICATION,

    /** Holds every permission on the resource and on everything inside it. */
    ADMIN,
    /** Reads and changes the resource and what is inside it, without deleting it. */
    EDITOR,
    /** Reads the resource and what is inside it. */
    VIEWER,
    /** Belongs to an organization, and so views its applications and their projects. */
    MEMBER,
    /** A machine running an application's microservices. */
    RUNTIME,

    CAN_READ,
    CAN_EDIT,
    CAN_DELETE,
    /** Registers services in the application's zone and its service directory entries. */
    CAN_REGISTER_SERVICES;

    /**
     * The relation's name in the engine: the lowercase enum name, such as {@code can_read}.
     */
    public String wireName() {
        return name().toLowerCase(Locale.ROOT);
    }
}
