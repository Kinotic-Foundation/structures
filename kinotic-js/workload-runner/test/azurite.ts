/** Signs requests to the Azurite emulator for the tests of the entrypoints that act on the sites account. */
import { createHmac } from 'node:crypto'

/** Azurite's well-known development account, reached where AZURITE_BLOB_ENDPOINT points or on its default port. */
const ACCOUNT = 'devstoreaccount1'
const ACCOUNT_KEY = 'Eby8vdM02xNOcqFlqUwJPLlmEtlCDXJ1OUzFT50uSRZ6IFsuFq2UVErCz4I6tq/K1SZFPTOtr/KBHBeksoGMGw=='
export const ENDPOINT = process.env.AZURITE_BLOB_ENDPOINT ?? `http://127.0.0.1:10000/${ACCOUNT}`
export const CONTAINER = 'sites'
const SAS_VERSION = '2020-12-06'

/** Shared-key headers for a request to the emulator, whose URL path starts with the account the canonical resource repeats. */
export function sharedKeyHeaders(method: string, url: string, contentLength: number = 0, extra: Record<string, string> = {}): Record<string, string> {
    const date = new Date().toUTCString()
    const headers: Record<string, string> = { 'x-ms-date': date, 'x-ms-version': SAS_VERSION, ...extra }
    const canonicalHeaders = Object.keys(headers)
        .filter(name => name.startsWith('x-ms-'))
        .sort()
        .map(name => `${name}:${headers[name]}`)
        .join('\n')
    const { pathname, searchParams } = new URL(url)
    const canonicalQuery = [...searchParams.keys()].sort()
        .map(name => `${name.toLowerCase()}:${searchParams.get(name)}`)
        .join('\n')
    const canonicalResource = `/${ACCOUNT}${pathname}${canonicalQuery ? '\n' + canonicalQuery : ''}`
    const stringToSign = [method, '', '', contentLength ? String(contentLength) : '', '', '', '', '', '', '', '', '',
                          canonicalHeaders, canonicalResource].join('\n')
    const signature = createHmac('sha256', Buffer.from(ACCOUNT_KEY, 'base64')).update(stringToSign, 'utf-8').digest('base64')
    headers.Authorization = `SharedKey ${ACCOUNT}:${signature}`
    return headers
}

/** A container SAS allowing create, write, delete and list for an hour, the permissions the platform's upload URL carries. */
export function containerSas(): string {
    const start = new Date(Date.now() - 5 * 60_000).toISOString().replace(/\.\d{3}Z$/, 'Z')
    const expiry = new Date(Date.now() + 60 * 60_000).toISOString().replace(/\.\d{3}Z$/, 'Z')
    const permissions = 'cwdl'
    const stringToSign = [permissions, start, expiry, `/blob/${ACCOUNT}/${CONTAINER}`, '', '', 'https,http', SAS_VERSION,
                          'c', '', '', '', '', '', '', ''].join('\n')
    const signature = createHmac('sha256', Buffer.from(ACCOUNT_KEY, 'base64')).update(stringToSign, 'utf-8').digest('base64')
    const query = new URLSearchParams({ sp: permissions, st: start, se: expiry, spr: 'https,http', sv: SAS_VERSION, sr: 'c', sig: signature })
    return query.toString()
}

export async function azuriteUp(): Promise<boolean> {
    try {
        const url = `${ENDPOINT}?comp=list`
        const response = await fetch(url, { headers: sharedKeyHeaders('GET', url) })
        return response.ok
    } catch {
        return false
    }
}

export async function readBlob(path: string): Promise<Response> {
    const url = `${ENDPOINT}/${CONTAINER}/${path}`
    return fetch(url, { headers: sharedKeyHeaders('GET', url) })
}
