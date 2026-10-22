/**
 * Reaches the Azurite emulator through the local edge's storage gateway (deployment/docker-compose/sites-edge.ts),
 * as the publish and removal workloads do, with directory SASes signed the way the system server signs them.
 */
import { createHmac } from 'node:crypto'

/** Azurite's well-known development account, behind the gateway SITES_STORAGE_ENDPOINT points at or its default port. */
const ACCOUNT = 'devstoreaccount1'
const ACCOUNT_KEY = 'Eby8vdM02xNOcqFlqUwJPLlmEtlCDXJ1OUzFT50uSRZ6IFsuFq2UVErCz4I6tq/K1SZFPTOtr/KBHBeksoGMGw=='
export const ENDPOINT = process.env.SITES_STORAGE_ENDPOINT ?? `http://127.0.0.1:8089/${ACCOUNT}`
const CONTAINER = 'sites'
const SAS_VERSION = '2026-06-06'

/** A SAS for one directory of the sites container, valid for an hour unless an expiry is given. */
export function directorySas(directory: string, permissions: string, expiry: Date = new Date(Date.now() + 60 * 60_000)): string {
    const se = expiry.toISOString().replace(/\.\d{3}Z$/, 'Z')
    const stringToSign = [permissions, '', se, `/blob/${ACCOUNT}/${CONTAINER}/${directory}`, '', '', '', SAS_VERSION,
                          'd', '', '', '', '', '', '', ''].join('\n')
    const signature = createHmac('sha256', Buffer.from(ACCOUNT_KEY, 'base64')).update(stringToSign, 'utf-8').digest('base64')
    return new URLSearchParams({ sv: SAS_VERSION, se, sr: 'd', sp: permissions, sdd: String(directory.split('/').length), sig: signature }).toString()
}

/** The URL of a site's directory with a SAS for it, the shape of the upload and removal URLs the platform issues. */
export function siteUrl(directory: string, permissions: string = 'cwdl'): string {
    return `${ENDPOINT}/${CONTAINER}/${directory}?${directorySas(directory, permissions)}`
}

function blobUrl(path: string, permissions: string): string {
    const directory = path.slice(0, path.indexOf('/'))
    return `${ENDPOINT}/${CONTAINER}/${path}?${directorySas(directory, permissions)}`
}

export async function gatewayUp(): Promise<boolean> {
    try {
        const directory = `probe-${crypto.randomUUID()}`
        const response = await fetch(`${ENDPOINT}/${CONTAINER}?restype=container&comp=list&prefix=${directory}/&${directorySas(directory, 'l')}`)
        return response.ok
    } catch {
        return false
    }
}

export async function putBlob(path: string, body: string, headers: Record<string, string> = {}): Promise<Response> {
    return fetch(blobUrl(path, 'w'), { method: 'PUT', body, headers: { 'x-ms-blob-type': 'BlockBlob', ...headers } })
}

export async function readBlob(path: string): Promise<Response> {
    return fetch(blobUrl(path, 'r'))
}
