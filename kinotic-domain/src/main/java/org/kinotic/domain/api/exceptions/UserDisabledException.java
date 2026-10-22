package org.kinotic.domain.api.exceptions;

/**
 * A user that may not sign in to an organization tried to: the organization user is disabled, or its membership of
 * the organization is. An invitation accepted this way is not consumed.
 */
public class UserDisabledException extends IllegalArgumentException {

    public UserDisabledException(String message) {
        super(message);
    }
}
