// @ts-ignore for some reason intellij is complaining about this even though esModuleInterop is enabled
import path from 'node:path'
import {DockerComposeEnvironment, Wait} from 'testcontainers'

const composeFilePath = '.'

// Run once before all tests
export async function setup() {
    console.log('Starting Structures...')
    const resolvedPath = path.resolve(composeFilePath)

    globalThis.environment = await new DockerComposeEnvironment('.', 'compose.yml')
        .withWaitStrategy('structures-elasticsearch-e2e', Wait.forHttp('/_cluster/health', 9200))
        .withWaitStrategy('structures-server-e2e', Wait.forHttp('/health', 9090))
        .up()

    console.log('Structures started.')
}

// Run once after all tests
export async function teardown() {
    console.log('Shutting down Structures...')
    await globalThis.environment?.down()
    console.log('Structures shut down.')
}
