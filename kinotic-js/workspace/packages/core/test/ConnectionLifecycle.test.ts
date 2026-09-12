import { describe, expect, it } from 'vitest'
import { firstValueFrom, toArray } from 'rxjs'
import { Event, EventConstants, type IEvent, type ConnectOptions, type IWebSocket } from '../src'
import { EventBus } from '../src/api/event/EventBus'
import { FakeStompServer, type FakeFrame, type FakeSocket } from './FakeStompServer'

const SERVICE = 'srv://test~com.example.Service/echo'

function options(server: FakeStompServer, extra: Partial<ConnectOptions> = {}): ConnectOptions {
    return { server: { host: 'fake', useSSL: false }, webSocketFactory: server.factory as () => IWebSocket, ...extra }
}

// answers every SEND with a single-value reply carrying the request's correlation id
function echo(frame: FakeFrame, socket: FakeSocket): void {
    socket.message(frame.headers[EventConstants.REPLY_TO_HEADER]!, {
        [EventConstants.CORRELATION_ID_HEADER]: frame.headers[EventConstants.CORRELATION_ID_HEADER]!,
        [EventConstants.CONTENT_TYPE_HEADER]: EventConstants.CONTENT_JSON,
        [EventConstants.CONTROL_HEADER]: EventConstants.CONTROL_VALUE_COMPLETE
    }, JSON.stringify('echoed'))
}

const sleep = (ms: number) => new Promise(resolve => setTimeout(resolve, ms))

describe('connection lifecycle', () => {

    it('answers a request over a connection and the reply destination names the connection', async () => {
        const server = new FakeStompServer()
        server.onSend = echo
        const bus = new EventBus()
        const connected = await bus.connect(options(server))
        expect(connected.replyToId).toBe('reply-1')

        const reply = await bus.request(new Event(SERVICE))
        expect(JSON.parse(reply.getDataString())).toBe('echoed')
        const send = server.current.received.find(f => f.command === 'SEND')!
        expect(send.headers[EventConstants.REPLY_TO_HEADER]).toMatch(/^reply:\/\/reply-1:[0-9a-f-]{36}@/)
        await bus.disconnect()
    })

    it('fails the calls in flight when the socket drops, reports the loss once, and reconnects on a fresh destination', async () => {
        const server = new FakeStompServer()
        const bus = new EventBus()
        let lost = 0
        bus.connectionLost.subscribe(() => lost++)
        await bus.connect(options(server))
        const firstSocket = server.current

        const pending = bus.request(new Event(SERVICE))
        await sleep(10)
        firstSocket.drop()
        await expect(pending).rejects.toThrow('Connection lost')
        expect(lost).toBe(1)

        // rx-stomp reconnects after its delay plus the client's jitter; the new socket mints a new destination
        server.onSend = echo
        const deadline = Date.now() + 15_000
        while (!bus.isConnected() && Date.now() < deadline) {
            await sleep(50)
        }
        expect(bus.isConnected()).toBe(true)
        expect(server.current).not.toBe(firstSocket)
        await bus.request(new Event(SERVICE))
        const firstReplyTo = firstSocket.received.find(f => f.command === 'SEND')!.headers[EventConstants.REPLY_TO_HEADER]
        const secondReplyTo = server.current.received.find(f => f.command === 'SEND')!.headers[EventConstants.REPLY_TO_HEADER]
        expect(secondReplyTo).not.toBe(firstReplyTo)
        expect(lost).toBe(1)
        await bus.disconnect()
        expect(lost).toBe(2)
    }, 30_000)

    it('rejects a connect still waiting for its socket when disconnect() is called', async () => {
        const server = new FakeStompServer()
        server.silent = true
        const bus = new EventBus()
        const connecting = bus.connect(options(server))
        await sleep(20)
        await bus.disconnect()
        await expect(connecting).rejects.toBe('Deactivated before the connection was established')
        expect(bus.isConnectionActive()).toBe(false)
    })

    it('connects again while the previous activation is still closing after a fatal error', async () => {
        const server = new FakeStompServer()
        server.closeDelayMs = 300
        server.onSend = echo
        const bus = new EventBus()
        await bus.connect(options(server))

        // Two ERROR frames before the socket closes: the second fatal is reported while the first one's
        // teardown is still waiting on the close, and the reconnect is issued from that notification
        let reconnects = 0
        const reconnected = new Promise<void>((resolve, reject) => {
            bus.fatalErrors.subscribe(() => {
                if (reconnects++ === 0) {
                    bus.connect(options(server)).then(() => resolve(), reject)
                }
            })
        })
        server.current.error('rejected by the gateway')
        server.current.refuse('rejected by the gateway again')
        await reconnected

        expect(bus.isConnected()).toBe(true)
        const reply = await bus.request(new Event(SERVICE))
        expect(JSON.parse(reply.getDataString())).toBe('echoed')
        await bus.disconnect()
    })

    it('closes a socket produced for a connect that was deactivated while the socket was being made', async () => {
        const server = new FakeStompServer()
        const bus = new EventBus()
        const slowFactory = async (): Promise<IWebSocket> => {
            await sleep(200)
            return server.factory()
        }
        const connecting = bus.connect(options(server, { webSocketFactory: slowFactory }))
        await sleep(50)
        await bus.disconnect()
        await expect(connecting).rejects.toBe('Deactivated before the connection was established')
        await sleep(300)
        expect(server.sockets.length).toBe(1)
        expect(server.sockets[0]!.closedByClient).toBe(true)
    })

    it('ends a stream on a bodiless completion without emitting it as a value', async () => {
        const server = new FakeStompServer()
        server.onSend = (frame, socket) => {
            const replyTo = frame.headers[EventConstants.REPLY_TO_HEADER]!
            const correlationId = frame.headers[EventConstants.CORRELATION_ID_HEADER]!
            socket.message(replyTo, { [EventConstants.CORRELATION_ID_HEADER]: correlationId,
                                      [EventConstants.CONTENT_TYPE_HEADER]: EventConstants.CONTENT_JSON }, '1')
            socket.message(replyTo, { [EventConstants.CORRELATION_ID_HEADER]: correlationId,
                                      [EventConstants.CONTROL_HEADER]: EventConstants.CONTROL_VALUE_COMPLETE })
        }
        const bus = new EventBus()
        await bus.connect(options(server))
        const values: IEvent[] = await firstValueFrom(bus.requestStream(new Event(SERVICE), true).pipe(toArray()))
        expect(values.map(v => v.getDataString())).toEqual(['1'])
        await bus.disconnect()
    })

    it('refuses a request while the connection is down instead of caching a stale reply destination', async () => {
        const server = new FakeStompServer()
        server.onSend = echo
        const bus = new EventBus()
        await bus.connect(options(server))
        server.current.drop()
        await sleep(10)
        await expect(bus.request(new Event(SERVICE))).rejects.toThrow('not connected')
        await bus.disconnect()
    })
})
