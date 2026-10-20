import { beforeEach, describe, expect, it } from 'bun:test'
import { spawnSync } from 'node:child_process'
import { join } from 'node:path'
import { azuriteUp, CONTAINER, containerSas, ENDPOINT, readBlob, sharedKeyHeaders } from './azurite.ts'

const REMOVE = join(import.meta.dir, '..', 'src', 'remove-ui.ts')

const azurite = await azuriteUp()

async function putBlob(path: string, body: string): Promise<void> {
    const url = `${ENDPOINT}/${CONTAINER}/${path}`
    const response = await fetch(url, { method: 'PUT', body,
        headers: sharedKeyHeaders('PUT', url, body.length, { 'x-ms-blob-type': 'BlockBlob' }) })
    expect(response.status).toBe(201)
}

describe.skipIf(!azurite)('remove-ui entrypoint (against Azurite)', () => {

    beforeEach(async () => {
        const containerUrl = `${ENDPOINT}/${CONTAINER}?restype=container`
        const created = await fetch(containerUrl, { method: 'PUT', headers: sharedKeyHeaders('PUT', containerUrl) })
        expect([201, 409]).toContain(created.status)
    })

    it('deletes every file of the site\'s directory and leaves other sites alone', async () => {
        const site = `removed-${crypto.randomUUID()}.apps.kinotic.test`
        const other = `kept-${crypto.randomUUID()}.apps.kinotic.test`
        await putBlob(`${site}/index.html`, '<html>removed</html>')
        await putBlob(`${site}/assets/deep/app.js`, 'console.log("removed")')
        await putBlob(`${other}/index.html`, '<html>kept</html>')

        const result = spawnSync('bun', [REMOVE], {
            env: { ...process.env, KINOTIC_UI_REMOVAL_URL: `${ENDPOINT}/${CONTAINER}/${site}?${containerSas()}` },
            encoding: 'utf-8',
        })

        expect(result.stderr).toBe('')
        expect(result.status).toBe(0)
        expect((await readBlob(`${site}/index.html`)).status).toBe(404)
        expect((await readBlob(`${site}/assets/deep/app.js`)).status).toBe(404)
        expect(await (await readBlob(`${other}/index.html`)).text()).toBe('<html>kept</html>')
    }, 60_000)

    it('succeeds for a site with no files', async () => {
        const result = spawnSync('bun', [REMOVE], {
            env: { ...process.env, KINOTIC_UI_REMOVAL_URL: `${ENDPOINT}/${CONTAINER}/never-published.apps.kinotic.test?${containerSas()}` },
            encoding: 'utf-8',
        })

        expect(result.stderr).toBe('')
        expect(result.status).toBe(0)
    }, 60_000)
})
