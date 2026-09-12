import type { IWebSocket } from '@/api/ConnectOptions'
import type { Subscription } from 'rxjs'

/**
 * One activation of the STOMP client: everything a single {@link StompConnectionManager#activate} call
 * owns, so that deactivating it can never touch a later activation. An activation is ended exactly once.
 *
 * @author Navid Mitchell 🤝Grok
 * @since 9/12/2026
 */
export class StompActivation {

    /** True once deactivate() has taken this activation; nothing it started may act afterwards. */
    public ended: boolean = false

    /** A socket produced for stompjs that stompjs has not taken yet; closed if the activation ends first. */
    public preparedSocket: IWebSocket | null = null

    private failPending: ((reason: string) => void) | null = null
    private readonly subscriptions: Subscription[] = []

    /** Registers a listener this activation owns. */
    public own(subscription: Subscription): void {
        this.subscriptions.push(subscription)
    }

    /** Registers how to reject the activate() promise while its socket has not opened. */
    public pending(fail: (reason: string) => void): void {
        this.failPending = fail
    }

    /** The socket is open: the activate() promise can no longer be rejected from here. */
    public established(): void {
        this.failPending = null
    }

    /** Rejects a still-pending activate() with the reason; no-op once established. */
    public failIfPending(reason: string): void {
        const fail = this.failPending
        this.failPending = null
        fail?.(reason)
    }

    /** Tears down every listener this activation owns. */
    public unsubscribeAll(): void {
        for (const subscription of this.subscriptions) {
            subscription.unsubscribe()
        }
        this.subscriptions.length = 0
    }
}
