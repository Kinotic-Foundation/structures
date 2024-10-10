import './instrumentation.js'
import {LoadGenerationConfig} from '@/config/LoadGenerationConfig.js'
import {nodeSdk} from '@/instrumentation.js'
import {LoadGenerationService} from '@/services/LoadGenerationService.js'

import {WebSocket} from 'ws'

// This is required when running Continuum from node
Object.assign(global, { WebSocket})


const loadGenConfg: LoadGenerationConfig = LoadGenerationConfig.fromEnv()
console.log('Load Generation Config:')
loadGenConfg.print()

const loadService = new LoadGenerationService(loadGenConfg)

try {
    process
        .on('unhandledRejection', (reason, p) => {
            console.error(reason, 'Unhandled Rejection at Promise', p)
        })
        .on('uncaughtException', async err => {
            console.error(err, 'Uncaught Exception thrown')
            await loadService.stop()
            await nodeSdk.shutdown().catch(console.error)
            process.exit(1)
        }).on('SIGINT', async () => {
            console.log('SIGINT')
            await loadService.stop()
            await nodeSdk.shutdown().catch(console.error)
        }).on('SIGTERM', async () => {
            console.log('SIGTERM')
            await loadService.stop()
            await nodeSdk.shutdown().catch(console.error)
        })

    await loadService.start()
    console.log('Load Generation Started')

    await loadService.waitForCompletion()

    console.log('Load Generation Completed')
    await nodeSdk.shutdown().catch(console.error)

} catch (e: any) {
    console.error(e, 'Exception thrown')
    await loadService.stop()
    await nodeSdk.shutdown().catch(console.error)
}

