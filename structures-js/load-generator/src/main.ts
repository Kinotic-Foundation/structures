import './instrumentation.js'
import {LoadGenerationConfig} from '@/config/LoadGenerationConfig.js'
import {nodeSdk} from '@/instrumentation.js'
import {LoadGenerationService} from '@/services/LoadGenerationService.js'
import {formatDuration} from '@/utils/DataUtil.js'

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

    const start = performance.now()
    await loadService.start()
    console.log('Load Generation Started')

    await loadService.waitForCompletion()

    console.log('Load Generation Completed')
    const end = performance.now();    // Record end time
    const duration = end - start;     // Calculate the duration

    console.log(`Load Generation took ${formatDuration(duration)}.`);

    await nodeSdk.shutdown().catch(console.error)

} catch (e: any) {
    console.error(e, 'Exception thrown')
    await loadService.stop()
    await nodeSdk.shutdown().catch(console.error)
}

