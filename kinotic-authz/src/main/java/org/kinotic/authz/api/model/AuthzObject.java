package org.kinotic.authz.api.model;

import org.apache.commons.lang3.Validate;

import java.util.regex.Pattern;

/**
 * An object in the authorization store: a subject or a resource, by its type and id.
 *
 * @param type the object's type
 * @param id   the object's id, which holds no {@code #}, {@code :} or whitespace
 */
public record AuthzObject(AuthzType type, String id) {

    // the engine splits an object on ':' and a userset on '#', so neither may appear in an id
    private static final Pattern ID = Pattern.compile("[^#:\\s]+");

    public AuthzObject {
        Validate.notNull(type, "type must not be null");
        Validate.isTrue(id != null && ID.matcher(id).matches(), "'%s' is not a valid %s id", id, type.wireName());
    }

    /**
     * The object as the engine names it, {@code type:id}.
     */
    @Override
    public String toString() {
        return type.wireName() + ":" + id;
    }
}
