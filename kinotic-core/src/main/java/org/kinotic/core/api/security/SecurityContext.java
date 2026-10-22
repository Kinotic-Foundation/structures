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
     * Requires that no {@link Participant} is bound to the current Vert.x context: the call being served was sent for
     * no participant, as {@link #callWithoutParticipant(Supplier)} sends one.
     *
     * @throws AuthorizationException if a participant is bound to the current Vert.x context
     */
    public void requireNoParticipant() {
        if (currentParticipant() != null) {
            throw new AuthorizationException("Access denied");
        }
    }

    /**
     * Sets the {@link Participant} on the given Vert.x context.
     */
    public void setParticipant(Context context, Participant participant) {
        context.putLocal(PARTICIPANT_LOCAL, participant);
    }

    /**
     * Runs {@code call} on a Vert.x context with {@code participant} bound, so code {@code call} runs synchronously
     * reads that participant from {@link #currentParticipant()}, and every service proxy invocation {@code call}
     * starts synchronously sends the call as that participant. A proxy invocation started from a callback, such as
     * one composed on another invocation's result, runs on the context that completed that result, which need not
     * carry {@code participant}, so each invocation that must act as {@code participant} is started by its own
     * {@code callAs}. Use it to act for
     * a participant from a thread that has no Vert.x context, or whose context is bound to another participant.
     * <p>
     * A remote service trusts the participant a cluster member sends, so {@code participant} must be one this
     * member authenticated, such as the participant of a service invocation it received.
     *
     * @param participant the participant to act as
     * @param call        starts the work and returns its completion
     * @param <T>         the type of the call's result
     * @return a future completed with the call's result or failure, on whichever thread completes the call; a caller
     *         on a Vert.x context bridges it with {@code Future.fromCompletionStage(stage, context)}
     */
    public <T> CompletableFuture<T> callAs(Participant participant, Supplier<? extends CompletionStage<T>> call) {
        Validate.notNull(participant, "participant must not be null");
        Validate.notNull(call, "call must not be null");
        ContextInternal context = newContext();
        setParticipant(context, participant);
        return callOn(context, call);
    }

    /**
     * Runs {@code call} on a Vert.x context with no participant bound, so every service proxy invocation {@code call}
     * starts synchronously is sent as this cluster member itself, for no participant, whatever participant the
     * calling context carries. As with {@link #callAs(Participant, Supplier)}, each invocation that must be sent this
     * way is started by its own {@code callWithoutParticipant}.
     *
     * @param call starts the work and returns its completion
     * @param <T>  the type of the call's result
     * @return a future completed with the call's result or failure, on whichever thread completes the call; a caller
     *         on a Vert.x context bridges it with {@code Future.fromCompletionStage(stage, context)}
     */
    public <T> CompletableFuture<T> callWithoutParticipant(Supplier<? extends CompletionStage<T>> call) {
        Validate.notNull(call, "call must not be null");
        return callOn(newContext(), call);
    }

    // duplicate() starts with empty locals, so the new context carries no participant whatever the current one binds
    private ContextInternal newContext() {
        return ((ContextInternal) vertx.getObject().getOrCreateContext()).duplicate();
    }

    private <T> CompletableFuture<T> callOn(ContextInternal context, Supplier<? extends CompletionStage<T>> call) {
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
