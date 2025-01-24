// @ts-ignore for some reason intellij is complaining about this even though esModuleInterop is enabled
import path from 'node:path'
import * as compose from 'docker-compose'

const composeFilePath = '.'

// Run once before all tests
export async function setup() {
    console.log('Starting Structures...')
    const resolvedPath = path.resolve(composeFilePath)

    await compose.pullAll({cwd: resolvedPath, log: true})

    await compose.upAll({
                            cwd: resolvedPath,
                            log: true
                        })
    console.log('Structures started.')
}

// Run once after all tests
export async function teardown() {
    console.log('Shutting down Structures...')
    await compose.down({cwd: path.resolve(composeFilePath), log: true})
    console.log('Structures shut down.')
}
