// @ts-ignore for some reason intellij is complaining about this even though esModuleInterop is enabled
import path from 'node:path'
import {StartedDockerComposeEnvironment, DockerComposeEnvironment, Wait} from 'testcontainers'
import {TestProject} from 'vitest/node.js'

let environment: StartedDockerComposeEnvironment

// Run once before all tests
export async function setup(project: TestProject) {
    console.log('Starting Structures...')

    const resolvedPath = path.resolve('../../')
    environment = await new DockerComposeEnvironment(resolvedPath, ['compose.yml', 'compose.es-transient.yml'])
        .withWaitStrategy('structures-elasticsearch', Wait.forHttp('/_cluster/health', 9200))
        .withWaitStrategy('structures-server', Wait.forHttp('/health', 9090))
        .up(['structures-elasticsearch', 'structures-server'])

    const container = environment.getContainer('structures-server')

    // @ts-ignore
    project.provide('STRUCTURES_HOST', container.getHost())
    // @ts-ignore
    project.provide('STRUCTURES_PORT', container.getMappedPort(58503))

    console.log('Structures started.')
}

// Run once after all tests
export async function teardown() {
    console.log('Shutting down Structures...')
    await environment.down()
    console.log('Structures shut down.')
}
