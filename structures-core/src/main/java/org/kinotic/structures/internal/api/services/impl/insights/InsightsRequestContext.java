package org.kinotic.structures.internal.api.services.impl.insights;

import io.vertx.core.Context;
import io.vertx.core.Vertx;
import org.kinotic.continuum.api.security.Participant;
import org.springframework.stereotype.Component;

/**
 * Provides Vert.x context-based storage for AI insights operations.
 * This allows tools to access the current participant across async operations
 * without being tied to specific threads.
 */
@Component
public class InsightsRequestContext {

    private static final String PARTICIPANT_KEY = "insights.participant";

    /**
     * Sets the current participant in the Vert.x context.
     * This works across async operations within the same request.
     */
    public void setCurrentParticipant(Participant participant) {
        Context context = Vertx.currentContext();
        if (context != null) {
            context.put(PARTICIPANT_KEY, participant);
        }
    }

    /**
     * Gets the current participant from the Vert.x context.
     * Returns null if no participant has been set or if not in a Vert.x context.
     */
    public Participant getCurrentParticipant() {
        Context context = Vertx.currentContext();
        if (context != null) {
            return context.get(PARTICIPANT_KEY);
        }
        return null;
    }

    /**
     * Clears the current participant from the Vert.x context.
     */
    public void clear() {
        Context context = Vertx.currentContext();
        if (context != null) {
            context.remove(PARTICIPANT_KEY);
        }
    }

    /**
     * Executes the given operation with the participant context set,
     * ensuring proper cleanup afterwards.
     */
    public <T> T executeWithContext(Participant participant, java.util.function.Supplier<T> operation) {
        try {
            setCurrentParticipant(participant);
            return operation.get();
        } finally {
            clear();
        }
    }
}