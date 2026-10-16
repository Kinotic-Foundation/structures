package org.kinotic.domain.api.exceptions;

/**
 * An invitation was accepted for an email that already has an account, by a method that does not prove the
 * account is the acceptor's: a password other than the account's, or a provider identity the account is not
 * bound to. The invitation is not consumed; the invitee signs in as the account to accept.
 */
public class ExistingAccountException extends IllegalArgumentException {

    public ExistingAccountException(String message) {
        super(message);
    }
}
