import type {Participant} from '@/api/security/Participant'

/**
 * Contains information about the connection that was established
 */
export class ConnectedInfo {

    public replyToId!: string;

    /**
     * How long, in milliseconds, the server holds this connection's replies after the connection closes. A
     * disconnect that outlasts it leaves nothing to wait for, so the client fails the calls it had in flight.
     */
    public replyBufferWindow!: number;

    public participant!: Participant;

}
