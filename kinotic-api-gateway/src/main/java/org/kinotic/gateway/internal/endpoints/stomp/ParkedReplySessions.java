package org.kinotic.gateway.internal.endpoints.stomp;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Session;
import io.vertx.ext.web.sstore.SessionStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kinotic.core.api.event.CRI;
import org.kinotic.core.api.security.ConnectedInfo;
import org.kinotic.gateway.api.config.ApiGatewayProperties;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The reply states of this node's sticky sessions whose connections closed, held for the configured window
 * so the same client reconnecting to this node resumes every call it had in flight. A state is indexed by
 * the reply destinations it listens on, which are stable for the life of the client process, so two
 * connections sharing one browser session keep their own states apart.
 *
 * Created by Navíd Mitchell 🤪 on 9/9/26.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ParkedReplySessions {

    private final ApiGatewayProperties apiGatewayProperties;
    private final Vertx vertx;
    private final SessionStore sessionStore;
    // reply destination -> the state currently serving it, connected or parked
    private final ConcurrentHashMap<String, ReplySessionState> serving = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<ReplySessionState, Parked> parked = new ConcurrentHashMap<>();

    /**
     * Records that a connection's state now serves a reply destination.
     */
    public void serve(CRI replyDestination, ReplySessionState state) {
        serving.put(replyDestination.raw(), state);
    }

    /**
     * Hands a reconnecting connection the state that was serving one of its reply destinations: one parked
     * by its previous connection, or one that connection still holds because its close has not been
     * processed yet. Either way the previous state is emptied by the caller's adoption.
     * @return the state to adopt, or null when nothing served the destination
     */
    public ReplySessionState claim(CRI replyDestination, ReplySessionState claimant) {
        ReplySessionState previous = serving.get(replyDestination.raw());
        ReplySessionState ret = null;
        if (previous != null && previous != claimant) {
            Parked entry = parked.remove(previous);
            if (entry != null) {
                vertx.cancelTimer(entry.timer());
            }
            previous.replyDestinations().forEach(cri -> serving.remove(cri, previous));
            ret = previous;
        }
        return ret;
    }

    /**
     * Keeps a closed connection's state for the window, unless a newer connection has taken its reply
     * destinations over already, in which case the state is disposed.
     */
    public void park(ReplySessionState state, Session session, ConnectedInfo connectedInfo) {
        Set<String> destinations = state.replyDestinations();
        boolean stillServing = !destinations.isEmpty()
                && destinations.stream().allMatch(cri -> serving.get(cri) == state);
        if (stillServing) {
            long timer = vertx.setTimer(apiGatewayProperties.getReplyBufferWindow(), _ -> end(state, "the window expired"));
            parked.put(state, new Parked(timer, session, connectedInfo));
            state.park(() -> end(state, "the buffered replies exceeded the budget"));
        } else {
            state.dispose();
        }
    }

    // Both exits: the state is disposed and the session's reply destination rotated, so the client's next
    // CONNECT hands it a new one and its reply-destination-changed path fails the calls it was waiting on
    private void end(ReplySessionState state, String reason) {
        Parked entry = parked.remove(state);
        if (entry != null) {
            vertx.cancelTimer(entry.timer());
            state.replyDestinations().forEach(cri -> serving.remove(cri, state));
            state.dispose();
            log.debug("Disposed the parked reply state of session {}: {}", entry.session().id(), reason);
            entry.connectedInfo().setReplyToId(UUID.randomUUID().toString());
            entry.session().put(ConnectedInfo.SESSION_KEY, entry.connectedInfo());
            sessionStore.put(entry.session())
                        .onFailure(throwable -> log.warn("Session {} could not store its rotated reply destination", entry.session().id(), throwable));
        }
    }

    private record Parked(long timer, Session session, ConnectedInfo connectedInfo) {}
}
