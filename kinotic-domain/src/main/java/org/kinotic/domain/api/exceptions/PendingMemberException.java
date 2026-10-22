package org.kinotic.domain.api.exceptions;

/**
 * A sign-up was made for an identity or email whose organization user an administrator added as a member and that
 * no sign-in method signs in yet. Nothing is consumed; the person gets in through the invitation emailed to them, or
 * through the organization's own sign-in provider, either of which claims the organization user.
 */
public class PendingMemberException extends IllegalArgumentException {

    public PendingMemberException(String message) {
        super(message);
    }
}
