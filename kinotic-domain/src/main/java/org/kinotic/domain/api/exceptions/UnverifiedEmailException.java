package org.kinotic.domain.api.exceptions;

/**
 * A user would be created under an email the provider's claims do not prove belongs to the person signing in, such as
 * a Microsoft Entra email that the administrator of the person's directory set without verifying it. Nothing is
 * created; the person signs up another way, or an administrator invites them.
 */
public class UnverifiedEmailException extends IllegalArgumentException {

    public UnverifiedEmailException(String message) {
        super(message);
    }
}
