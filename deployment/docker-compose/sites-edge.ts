/**
 * The local edge that serves every organization's published sites from Azurite, the way the Cloudflare
 * Worker serves them from the sites storage account: it runs deployment/cloudflare/sites-worker/worker.js
 * itself, with the bindings terraform gives the Worker. On start it ensures the private sites container
 * exists and signs the Worker's read SAS with Azurite's well-known development key, which terraform does
 * for the real account.
 *
 * It is also the storage gateway the publish and removal workloads reach the emulator through, in place of
 * the account's blob endpoint. Azurite honors no directory SAS, so the gateway checks the one each workload's
 * URL carries, which the system server signs with the same well-known key for one site's directory: the
 * signature, the expiry, the permission the operation needs, and that the request stays in the directory.
 * A request that passes goes on to Azurite with the gateway's own container SAS; any other is refused with 403.
 *
 * Environment:
 *   SITES_DOMAIN       the domain every site is under (default sites.localhost)
 *   AZURITE_ENDPOINT   Azurite's blob endpoint, with the account (default http://127.0.0.1:10000/devstoreaccount1)
 *   PORTAL_BASE_URL    where an organization's own host sends the browser (default http://localhost:9090)
 *   PORT               the port the sites are served on (default 8088)
 *   STORAGE_PORT       the port of the storage gateway (default 8089)
 */
import { createHmac, timingSafeEqual } from 'node:crypto'
import worker from '../cloudflare/sites-worker/worker.js'

const ACCOUNT = 'devstoreaccount1'
const ACCOUNT_KEY = 'Eby8vdM02xNOcqFlqUwJPLlmEtlCDXJ1OUzFT50uSRZ6IFsuFq2UVErCz4I6tq/K1SZFPTOtr/KBHBeksoGMGw=='
const CONTAINER = 'sites'
const SAS_VERSION = '2020-12-06'
const EDGE_SAS_LIFETIME_MS = 10 * 365 * 24 * 60 * 60_000
// The SAS parameters the gateway reads and never forwards
const SAS_PARAMETERS = ['sv', 'st', 'se', 'sr', 'sp', 'sdd', 'sig', 'si', 'sip', 'spr', 'ses', 'rscc', 'rscd', 'rsce', 'rscl', 'rsct']

const endpoint = process.env.AZURITE_ENDPOINT ?? `http://127.0.0.1:10000/${ACCOUNT}`
const port = Number(process.env.PORT ?? 8088)
const storagePort = Number(process.env.STORAGE_PORT ?? 8089)

function sign(stringToSign: string): string {
    return createHmac('sha256', Buffer.from(ACCOUNT_KEY, 'base64')).update(stringToSign, 'utf-8').digest('base64')
}

/** Creates the private sites container; one that exists already is left as it is. */
async function ensureContainer(): Promise<void> {
    const url = `${endpoint}/${CONTAINER}?restype=container`
    const date = new Date().toUTCString()
    const canonicalResource = `/${ACCOUNT}${new URL(url).pathname}\nrestype:container`
    const stringToSign = ['PUT', '', '', '', '', '', '', '', '', '', '', '',
                          `x-ms-date:${date}\nx-ms-version:${SAS_VERSION}`, canonicalResource].join('\n')
    const response = await fetch(url, {
        method: 'PUT',
        headers: { 'x-ms-date': date, 'x-ms-version': SAS_VERSION, Authorization: `SharedKey ${ACCOUNT}:${sign(stringToSign)}` },
    })
    if (response.status !== 201 && response.status !== 409) {
        throw new Error(`creating the ${CONTAINER} container failed with ${response.status} ${await response.text()}`)
    }
}

/** A SAS on the whole sites container with the given permissions, which only the edge holds. */
function containerSas(permissions: string): string {
    const expiry = new Date(Date.now() + EDGE_SAS_LIFETIME_MS).toISOString().replace(/\.\d{3}Z$/, 'Z')
    const stringToSign = [permissions, '', expiry, `/blob/${ACCOUNT}/${CONTAINER}`, '', '', 'https,http', SAS_VERSION,
                          'c', '', '', '', '', '', '', ''].join('\n')
    return new URLSearchParams({ sp: permissions, se: expiry, spr: 'https,http', sv: SAS_VERSION, sr: 'c', sig: sign(stringToSign) }).toString()
}

/**
 * The string a directory SAS of service version 2020-12-06 or later is signed over, the layout the Azure SDK signs
 * a shared-key directory SAS with.
 */
function directorySasStringToSign(sas: URLSearchParams, directory: string): string {
    const get = (name: string) => sas.get(name) ?? ''
    return [get('sp'), get('st'), get('se'), `/blob/${ACCOUNT}/${CONTAINER}/${directory}`, get('si'), get('sip'),
            get('spr'), get('sv'), get('sr'), '', get('ses'), get('rscc'), get('rscd'), get('rsce'), get('rscl'),
            get('rsct')].join('\n')
}

function refuse(reason: string): Response {
    return new Response(`AuthorizationFailure: ${reason}`, { status: 403 })
}

/** The permission letter the operation needs, or null for an operation the gateway never admits. */
function requiredPermission(method: string, query: URLSearchParams, isContainer: boolean): string | null {
    let ret: string | null = null
    if (isContainer) {
        if ((method === 'GET' || method === 'HEAD') && query.get('restype') === 'container' && query.get('comp') === 'list') {
            ret = 'l'
        }
    } else if (method === 'PUT' && !query.has('comp')) {
        ret = 'w'
    } else if (method === 'DELETE') {
        ret = 'd'
    } else if ((method === 'GET' || method === 'HEAD') && !query.has('comp')) {
        ret = 'r'
    }
    return ret
}

/**
 * Admits a request to one site's directory when the directory SAS it carries allows it, and passes it on to
 * Azurite. The directory is the first sdd segments of the blob path, or of the listing's prefix.
 */
async function handleStorage(request: Request): Promise<Response> {
    const url = new URL(request.url)
    const containerPath = `/${ACCOUNT}/${CONTAINER}`
    if (url.pathname !== containerPath && !url.pathname.startsWith(containerPath + '/')) {
        return refuse('the request is outside the sites container')
    }
    const isContainer = url.pathname === containerPath
    // the listing's prefix names the directory of a container request; the blob's own path names that of any other
    const target = isContainer ? url.searchParams.get('prefix') ?? '' : decodeURIComponent(url.pathname.slice(containerPath.length + 1))
    const segments = target.split('/')
    const depth = Number(url.searchParams.get('sdd'))
    const permission = requiredPermission(request.method, url.searchParams, isContainer)
    const expiry = Date.parse(url.searchParams.get('se') ?? '')
    const start = url.searchParams.get('st')
    const signature = Buffer.from(url.searchParams.get('sig') ?? '', 'base64')

    let ret: Response
    if (url.searchParams.get('sr') !== 'd' || url.searchParams.has('si') || !Number.isInteger(depth) || depth < 1) {
        ret = refuse('the URL carries no directory SAS')
    } else if (segments.some(segment => segment === '.' || segment === '..') || segments.slice(0, depth).some(segment => !segment)) {
        ret = refuse('the path names no directory')
    } else if (isContainer && segments.length <= depth) {
        ret = refuse('a listing names no prefix under the directory')
    } else if (permission === null || !(url.searchParams.get('sp') ?? '').includes(permission)) {
        ret = refuse(`the SAS does not allow ${request.method} here`)
    } else if (!(expiry > Date.now()) || (start !== null && !(Date.parse(start) <= Date.now()))) {
        ret = refuse('the SAS is not valid at this time')
    } else {
        const directory = segments.slice(0, depth).join('/')
        const expected = Buffer.from(sign(directorySasStringToSign(url.searchParams, directory)), 'base64')
        if (signature.length !== expected.length || !timingSafeEqual(signature, expected)) {
            ret = refuse('the signature does not match')
        } else {
            const forwarded = new URLSearchParams(url.search)
            for (const name of SAS_PARAMETERS) {
                forwarded.delete(name)
            }
            const headers = new Headers(request.headers)
            headers.delete('host')
            headers.delete('authorization')
            const query = [forwarded.toString(), forwardSas].filter(Boolean).join('&')
            ret = await fetch(`${new URL(endpoint).origin}${url.pathname}?${query}`, {
                method: request.method,
                headers,
                body: request.method === 'PUT' ? await request.arrayBuffer() : undefined,
            })
        }
    }
    return ret
}

// Azurite starts alongside the edge, so the container is retried until it answers
for (let attempt = 1; ; attempt++) {
    try {
        await ensureContainer()
        break
    } catch (error) {
        if (attempt === 30) {
            throw error
        }
        await Bun.sleep(1000)
    }
}

const forwardSas = containerSas('rcwdl')
const env = {
    SITES_DOMAIN: process.env.SITES_DOMAIN ?? 'sites.localhost',
    SITES_STORAGE_URL: endpoint,
    SITES_STORAGE_SAS: containerSas('r'),
    PORTAL_BASE_URL: process.env.PORTAL_BASE_URL ?? 'http://localhost:9090',
    UI_HOSTS: '',
}
Bun.serve({ port, hostname: '0.0.0.0', fetch: request => worker.fetch(request, env) })
Bun.serve({ port: storagePort, hostname: '0.0.0.0', fetch: handleStorage })
console.log(`Serving the sites under ${env.SITES_DOMAIN} on port ${port} from ${endpoint}/${CONTAINER}, `
            + `and the storage gateway on port ${storagePort}`)
