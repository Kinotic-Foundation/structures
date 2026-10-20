package org.kinotic.core.api.security;

import io.vertx.core.Context;
import io.vertx.core.Vertx;
import io.vertx.core.internal.ContextInternal;
import io.vertx.core.spi.context.storage.ContextLocal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;
import org.kinotic.core.api.exceptions.AuthorizationException;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.Supplier;

/**
 * Provides access to the security state associated with the current Vert.x context: the
 * authenticated {@link Participant}.
 * <p>
 * Must only be used as a Spring-managed bean. The underlying {@link ContextLocal} must be
 * registered before any {@link Vertx} instance is created, which is handled by the bean
 * definitions in {@code org.kinotic.core.internal.config.KinoticVertxConfig}.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SecurityContext {

    private static final ContextLocal<Participant> PARTICIPANT_LOCAL = ContextLocal.registerLocal(Participant.class);

    // a provider, since the Vertx bean depends on this one so the ContextLocal above is registered first
    private final ObjectProvider<Vertx> vertx;

    /**
     * Returns the {@link Participant} for the current Vert.x context, or null if none is set.
     */
    public Participant currentParticipant() {
        Context context = Vertx.currentContext();
        if (context != null) {
            return context.getLocal(PARTICIPANT_LOCAL);
        }
        return null;
    }

    /**
     * Returns the current {@link Participant} narrowed to {@code type}.
     *
     * @param type the required {@code Participant} subtype (e.g.
     *             {@code OrganizationParticipant.class})
     * @throws IllegalStateException if no participant is bound to the current Vert.x context
     * @throws AuthorizationException if the current participant is not an instance of {@code type}
     */
    public <T extends Participant> T requireParticipant(Class<T> type) {
        Participant participant = currentParticipant();
        if (participant == null) {
            throw new IllegalStateException("No Participant is bound to the current Vert.x context");
        }
        if (!type.isInstance(participant)) {
            // Log the mismatch server-side for diagnostics; surface only a generic message
            // to the caller so the response can't be probed to discover scope details.
            log.error("Participant type mismatch: {} required, got {} (participant id={})",
                      type.getSimpleName(),
                      participant.getClass().getSimpleName(),
                      participant.getId());
            throw new AuthorizationException("Access denied");
        }
        return type.cast(participant);
    }

    /**
     * Sets the {@link Participant} on the given Vert.x context.
     */
    public void setParticipant(Context context, Participant participant) {
        context.putLocal(PARTICIPANT_LOCAL, participant);
    }

    /**
     * Runs {@code call} on a Vert.x context with {@code participant} bound, so everything the call does reads that
     * participant from {@link #currentParticipant()}, and every service proxy it invokes sends the call as that
     * participant. Use it to act for a participant from a thread that has no Vert.x context, or whose context is
     * bound to another participant.
     * <p>
     * A remote service trusts the participant a cluster member sends, so {@code participant} must be one this
     * member authenticated, such as the participant of a service invocation it received.
     *
     * @param participant the participant to act as
     * @param call        starts the work and returns its completion
     * @param <T>         the type of the call's result
     * @return a future completed with the call's result or failure
     */
    public <T> CompletableFuture<T> callAs(Participant participant, Supplier<? extends CompletionStage<T>> call) {
        Validate.notNull(participant, "participant must not be null");
        Validate.notNull(call, "call must not be null");
        // a duplicated context holds its own locals, so binding the participant leaves the context it came from untouched
        ContextInternal context = ((ContextInternal) vertx.getObject().getOrCreateContext()).duplicate();
        setParticipant(context, participant);
        CompletableFuture<T> ret = new CompletableFuture<>();
        context.runOnContext(v -> {
            try {
                call.get().whenComplete((result, failure) -> {
                    if (failure != null) {
                        ret.completeExceptionally(failure);
                    } else {
                        ret.complete(result);
                    }
                });
            } catch (Throwable t) {
                ret.completeExceptionally(t);
            }
        });
        return ret;
    }

}
