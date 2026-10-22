package org.kinotic.domain.api.exceptions;

/**
 * A sign-up, an invitation or a creation was made for an identity or email that already has a user of the scope,
 * by a method that does not prove the user is the person's: a sign-up, which founds one organization user at most;
 * a password other than the user's; or a provider identity the user is not bound to. Nothing is consumed; the
 * person signs in as that user instead.
 */
public class ExistingUserException extends IllegalArgumentException {

    public ExistingUserException(String message) {
        super(message);
    }
}
