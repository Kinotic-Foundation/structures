export class LoadGenerationConfig {

    public structuresHost: string
    public structuresPort: number
    public mockUserTenantId: string
    public maxRequestsPerSecond: number
    public batchSize: number
    public totalNumberOfRecords: number = 0

    constructor(structuresHost: string,
                structuresPort: number,
                mockUserTenantId: string,
                maxRequestsPerSecond: number,
                batchSize: number,
                totalNumberOfRecords: number) {
        this.structuresHost = structuresHost
        this.structuresPort = structuresPort
        this.mockUserTenantId = mockUserTenantId
        this.maxRequestsPerSecond = maxRequestsPerSecond
        this.batchSize = batchSize
        this.totalNumberOfRecords = totalNumberOfRecords
    }

    public static fromEnv(): LoadGenerationConfig {
        const structuresHost = process.env.STRUCTURES_HOST || "127.0.0.1"
        const structuresPort = parseInt(process.env.STRUCTURES_PORT || "58503")
        const mockUserTenantId = process.env.MOCK_USER_TENANT_ID || "test01"
        const maxRequestsPerSecond = parseInt(process.env.MAX_REQUESTS_PER_SECOND || "10")
        const batchSize = parseInt(process.env.BATCH_SIZE || "10")
        const totalNumberOfRecords = parseInt(process.env.TOTAL_NUMBER_OF_RECORDS || "1000")
        return new LoadGenerationConfig(structuresHost, structuresPort, mockUserTenantId, maxRequestsPerSecond, batchSize, totalNumberOfRecords)
    }

    public print(): void {
        console.log(`STRUCTURES_HOST=${this.structuresHost}`)
        console.log(`STRUCTURES_PORT=${this.structuresPort}`)
        console.log(`MOCK_USER_TENANT_ID=${this.mockUserTenantId}`)
        console.log(`MAX_REQUESTS_PER_SECOND=${this.maxRequestsPerSecond}`)
        console.log(`BATCH_SIZE=${this.batchSize}`)
        console.log(`TOTAL_NUMBER_OF_RECORDS=${this.totalNumberOfRecords}`)
    }

}
