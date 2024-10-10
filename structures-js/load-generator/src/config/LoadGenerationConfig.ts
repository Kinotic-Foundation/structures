export class LoadGenerationConfig {

    public structuresHost: string
    public structuresPort?: number
    public stucturesUseSsl: boolean = false
    public mockUserTenantId: string
    public maxRequestsPerSecond: number
    public batchSize: number
    public totalNumberOfRecords: number = 0

    constructor(mockUserTenantId: string,
                maxRequestsPerSecond: number,
                batchSize: number,
                totalNumberOfRecords: number,
                stucturesUseSsl: boolean,
                structuresHost: string,
                structuresPort?: number) {
        this.mockUserTenantId = mockUserTenantId
        this.maxRequestsPerSecond = maxRequestsPerSecond
        this.batchSize = batchSize
        this.totalNumberOfRecords = totalNumberOfRecords
        this.stucturesUseSsl = stucturesUseSsl
        this.structuresHost = structuresHost
        this.structuresPort = structuresPort
    }

    public static fromEnv(): LoadGenerationConfig {
        const mockUserTenantId = process.env.MOCK_USER_TENANT_ID || "test01"
        const maxRequestsPerSecond = parseInt(process.env.MAX_REQUESTS_PER_SECOND || "10")
        const batchSize = parseInt(process.env.BATCH_SIZE || "10")
        const totalNumberOfRecords = parseInt(process.env.TOTAL_NUMBER_OF_RECORDS || "1000")
        const structuresHost = process.env.STRUCTURES_HOST || "127.0.0.1"
        const structuresPort = process.env.STRUCTURES_PORT ? parseInt( process.env.STRUCTURES_PORT) : undefined
        const stucturesUseSsl = process.env.STRUCTURES_USE_SSL === "true"
        return new LoadGenerationConfig(mockUserTenantId,
                                        maxRequestsPerSecond,
                                        batchSize,
                                        totalNumberOfRecords,
                                        stucturesUseSsl,
                                        structuresHost,
                                        structuresPort)
    }

    public print(): void {
        console.log(`MOCK_USER_TENANT_ID=${this.mockUserTenantId}`)
        console.log(`MAX_REQUESTS_PER_SECOND=${this.maxRequestsPerSecond}`)
        console.log(`BATCH_SIZE=${this.batchSize}`)
        console.log(`TOTAL_NUMBER_OF_RECORDS=${this.totalNumberOfRecords}`)
        console.log(`STRUCTURES_USE_SSL=${this.stucturesUseSsl}`)
        console.log(`STRUCTURES_HOST=${this.structuresHost}`)
        console.log(`STRUCTURES_PORT=${this.structuresPort}`)
    }

}
