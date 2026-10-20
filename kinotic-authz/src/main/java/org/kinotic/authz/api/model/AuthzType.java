package org.kinotic.authz.api.model;

import java.util.Locale;

/**
 * The types of object the platform's authorization model holds: the subjects a relationship is held by, and the
 * resources it is held on, each resource contained in the one before it.
 */
public enum AuthzType {

    /**
     * A person's membership of one organization, or of the platform: the subject a person holds every
     * relationship as, so a grant made in one organization never applies in another.
     */
    MEMBERSHIP,

    /**
     * A machine identity, such as a project's sync workload or a microservice's runtime.
     */
    MACHINE,

    /**
     * The platform itself, the root every organization is contained in.
     */
    PLATFORM,

    ORGANIZATION,

    APPLICATION,

    PROJECT;

    /**
     * The type's name in the engine: the lowercase enum name, such as {@code organization}.
     */
    public String wireName() {
        return name().toLowerCase(Locale.ROOT);
    }
}
