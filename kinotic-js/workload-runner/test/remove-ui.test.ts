import { describe, expect, it } from 'bun:test'
import { spawnSync } from 'node:child_process'
import { join } from 'node:path'
import { directorySas, ENDPOINT, gatewayUp, putBlob, readBlob, siteUrl } from './sites-gateway.ts'

const REMOVE = join(import.meta.dir, '..', 'src', 'remove-ui.ts')

const gateway = await gatewayUp()

async function seed(path: string, body: string): Promise<void> {
    expect((await putBlob(path, body)).status).toBe(201)
}

function remove(removalUrl: string) {
    return spawnSync('bun', [REMOVE], { env: { ...process.env, KINOTIC_UI_REMOVAL_URL: removalUrl }, encoding: 'utf-8' })
}

describe.skipIf(!gateway)('remove-ui entrypoint (against Azurite through the local edge\'s storage gateway)', () => {

    it('deletes every file of the site\'s directory and leaves other sites alone', async () => {
        const site = `removed-${crypto.randomUUID()}.apps.kinotic.test`
        const other = `kept-${crypto.randomUUID()}.apps.kinotic.test`
        await seed(`${site}/index.html`, '<html>removed</html>')
        await seed(`${site}/assets/deep/app.js`, 'console.log("removed")')
        await seed(`${other}/index.html`, '<html>kept</html>')

        const result = remove(siteUrl(site, 'dl'))

        expect(result.stderr).toBe('')
        expect(result.status).toBe(0)
        expect((await readBlob(`${site}/index.html`)).status).toBe(404)
        expect((await readBlob(`${site}/assets/deep/app.js`)).status).toBe(404)
        expect(await (await readBlob(`${other}/index.html`)).text()).toBe('<html>kept</html>')
    }, 60_000)

    it('succeeds for a site with no files', () => {
        const result = remove(siteUrl('never-published.apps.kinotic.test', 'dl'))

        expect(result.stderr).toBe('')
        expect(result.status).toBe(0)
    }, 60_000)

    it('deletes nothing through a URL signed for another directory, or one that has expired', async () => {
        const own = `own-${crypto.randomUUID()}.apps.kinotic.test`
        const other = `other-${crypto.randomUUID()}.apps.kinotic.test`
        await seed(`${other}/index.html`, '<html>other</html>')
        await seed(`${own}/index.html`, '<html>own</html>')

        const crossed = remove(`${ENDPOINT}/sites/${other}?${directorySas(own, 'dl')}`)
        const expired = remove(`${ENDPOINT}/sites/${own}?${directorySas(own, 'dl', new Date(Date.now() - 60_000))}`)

        expect(crossed.status).not.toBe(0)
        expect(crossed.stderr).toContain('403')
        expect(expired.status).not.toBe(0)
        expect(expired.stderr).toContain('403')
        expect(await (await readBlob(`${other}/index.html`)).text()).toBe('<html>other</html>')
        expect(await (await readBlob(`${own}/index.html`)).text()).toBe('<html>own</html>')
    }, 60_000)
})
