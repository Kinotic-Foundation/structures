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
    private started: boolean = false
    private completedPromise: Promise<void> | null = null
    private completeResolver: ((value: void) => void) | null = null
    private totalTasksNeeded: number = 0
    private taskSubmitted: number = 0
    private totalRecordsPendingCreation: number = 0

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

        // calculate the total number of tasks needed
        if(loadGenerationConfig.totalNumberOfRecords > loadGenerationConfig.batchSize) {
            this.totalTasksNeeded = Math.ceil(loadGenerationConfig.totalNumberOfRecords / loadGenerationConfig.batchSize)
        }else{
            this.totalTasksNeeded = 1
        }
        console.log(`For ${loadGenerationConfig.totalNumberOfRecords} records, ${this.totalTasksNeeded} tasks are needed with ${loadGenerationConfig.batchSize} records per task`)

        this.queue.on('empty', async () => {
            if(this.taskSubmitted >= this.totalTasksNeeded){
               await this.queue.onIdle() // make sure all inflight tasks are completed
               await this.stop().catch(err => console.error(err, "Error stopping load generation service"))
            }
            this.enqueueTasks()
        });
    }

    public async start(): Promise<void>{
        if(!this.started) {
            this.started = true
            console.log('Starting Load Generation Service')
            const connectionInfo: ConnectionInfo = {
                host: this.loadGenerationConfig.structuresHost,
                port: this.loadGenerationConfig.structuresPort,
                maxConnectionAttempts: 5,
                connectHeaders: {
                    login   : 'admin',
                    passcode: 'structures',
                    tenantId: this.loadGenerationConfig.mockUserTenantId
                }
            }
            await this.continuum.connect(connectionInfo)
            this.completedPromise = new Promise<void>((resolve) => {
                this.completeResolver = resolve
            })
            this.enqueueTasks()
            this.queue.start()
        }else{
            throw new Error('Load Generation Service already started')
        }
    }

    public async stop(): Promise<void> {
        if (this.started) {
            this.started = false
            console.log('Stopping Load Generation Service')
            this.queue.pause()
            this.queue.clear()
            this.taskSubmitted = 0
            this.totalTasksNeeded = 0
            this.totalRecordsPendingCreation = 0
            await this.continuum.disconnect()
            if (this.completeResolver) {
                this.completeResolver()
                this.completeResolver = null
            }
        }
    }

    public async waitForCompletion(): Promise<void> {
        if(this.completedPromise){
            return this.completedPromise
        }else{
            throw new Error('Load Generation Service not started')
        }
    }

    private enqueueTasks(): void {
        let numberOfTasks = Math.min(this.totalTasksNeeded - this.taskSubmitted, 10)
        for(let i = 0; i < numberOfTasks; i++) {

            let recordsToCreate = this.loadGenerationConfig.batchSize
            const recordsRemaining = this.loadGenerationConfig.totalNumberOfRecords - this.totalRecordsPendingCreation
            if(recordsToCreate > recordsRemaining){
                recordsToCreate = recordsRemaining
            }

            this.queue.add(() => {
                return this.persistFakeData(recordsToCreate)
            }).catch((e) => {
                console.error(e, "Error saving batch");
            })

            this.totalRecordsPendingCreation += recordsToCreate
        }
        this.taskSubmitted += numberOfTasks
    }

    private persistFakeData(recordsToCreate: number): Promise<void> {
        return this.continuum.execute(async () => {
            const people = generatePeople(recordsToCreate)
            await this.personService.bulkSave(people)
        })
    }

}
