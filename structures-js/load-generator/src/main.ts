import {LoadGenerationService} from '@/services/LoadGenerationService.js'
import {WebSocket} from 'ws'

// This is required when running Continuum from node
Object.assign(global, { WebSocket})

process
    .on('unhandledRejection', (reason, p) => {
        console.error(reason, 'Unhandled Rejection at Promise', p);
    })
    .on('uncaughtException', err => {
        console.error(err, 'Uncaught Exception thrown');
        process.exit(1);
    });

console.log("Starting Load Generation")
const loadService = new LoadGenerationService({
                                                  host          : 'host.docker.internal',
                                                  port          : 58503,
                                                  maxConnectionAttempts: 3,
                                                  connectHeaders: {login: 'admin', passcode: 'structures'}
                                              })
let connected = false
try {
    console.log("Connecting")
    await loadService.connect()
    console.log("Connected to Structures")
    connected = true

    await loadService.persistFakeData(100)

    console.log("Disconnecting")
    await loadService.disconnect()
    connected = false
    console.log("Disconnected")

    console.log("Done")
} catch (e: any) {
    console.error(e, 'Exception thrown');
    if(connected){
        await loadService.disconnect()
    }
}

