import {LoadGenerationConfig} from '@/config/LoadGenerationConfig.js'
import {PersonEntityService} from '@/entity/services/PersonEntityService.js'
import {generatePeople} from '@/utils/DataUtil.js'
import {ContinuumSingleton} from '@kinotic/continuum-client'
import PQueue from 'p-queue'
import {ConnectionInfo} from '@kinotic/continuum-client'

export class LoadGenerationService {

    private loadGenerationConfig: LoadGenerationConfig
    private continuum: ContinuumSingleton
    private personService: PersonEntityService
    private queue: PQueue
    private completedPromise: Promise<void> | null = null
    private completeResolver: ((value: void) => void) | null = null
    private totalRecordsCreated: number = 0

    constructor(loadGenerationConfig: LoadGenerationConfig) {

        this.loadGenerationConfig = loadGenerationConfig
        this.continuum = new ContinuumSingleton()
        this.personService = new PersonEntityService()
        this.queue = new PQueue({
                                    concurrency: 1,
                                    interval: 1000,
                                    intervalCap: loadGenerationConfig.maxRequestsPerSecond,
                                    carryoverConcurrencyCount: true,
                                    autoStart: false
                                })

        this.queue.on('error', error => {
            console.error(error, "Error saving batch")
        })

        this.queue.on('empty', async () => {
            if(this.totalRecordsCreated >= this.loadGenerationConfig.totalNumberOfRecords){
               await this.stop().catch(err => console.error(err, "Error stopping load generation service"))
            }
            this.totalRecordsCreated += this.loadGenerationConfig.batchSize * 10
            this.enqueueTasks(10)
        });

    }

    public async start(): Promise<void>{
        console.log('Starting Load Generation Service')
        const connectionInfo: ConnectionInfo = {
            host          : this.loadGenerationConfig.structuresHost,
            port          : this.loadGenerationConfig.structuresPort,
            maxConnectionAttempts: 5,
            connectHeaders: {login: 'admin', passcode: 'structures', tenantId: this.loadGenerationConfig.mockUserTenantId}
        }
        await this.continuum.connect(connectionInfo)
        this.completedPromise = new Promise<void>((resolve) => {
            this.completeResolver = resolve
        })
        this.enqueueTasks(10)
        this.queue.start()
    }

    public async stop(): Promise<void> {
        console.log('Stopping Load Generation Service')
        this.queue.pause()
        this.queue.clear()
        this.totalRecordsCreated = 0
        await this.continuum.disconnect()
        if(this.completeResolver){
            this.completeResolver()
            this.completeResolver = null
        }
    }

    public async waitForCompletion(): Promise<void> {
        if(this.completedPromise){
            return this.completedPromise
        }else{
            throw new Error('Load Generation Service not started')
        }
    }

    private enqueueTasks(numberOfTasks: number): void {
        for(let i = 0; i < numberOfTasks; i++) {
            this.queue.add(() => {
                return this.persistFakeData(this.loadGenerationConfig.batchSize)
            }).catch((e) => {
                console.error(e, "Error saving batch");
            })
        }
    }

    private persistFakeData(recordsToCreate: number): Promise<void> {
        return this.continuum.execute(async () => {
            const people = generatePeople(recordsToCreate)
            return this.personService.bulkSave(people)
        })
    }

}
