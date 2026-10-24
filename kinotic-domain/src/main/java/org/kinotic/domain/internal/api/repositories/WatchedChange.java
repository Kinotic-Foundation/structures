package org.kinotic.domain.internal.api.repositories;

import org.kinotic.domain.api.model.WatchEventType;

/**
 * What a write to a watched record was, for the ledger: the caller's half of a
 * {@link org.kinotic.domain.api.model.WatchEvent}, the rest being read from the record and the server.
 *
 * @param eventType what happened
 * @param source    what caused it
 * @param message   why, for a system user
 * @param value     what was written, as an object
 */
public record WatchedChange(WatchEventType eventType, String source, String message, Object value) {
}
