import {PersonEntityService} from '@/entity/services/PersonEntityService.js'
import {ContinuumOperationTaskGenerator} from '@/tasks/ContinuumOperationTaskGenerator.js'
import {ITaskFactory} from '@/tasks/ITaskFactory.js'
import {ITaskGenerator} from '@/tasks/ITaskGenerator.js'
import {generateMultipleDeterministicIds} from '@/utils/DataUtil.js'
import {ConnectionInfo, ContinuumSingleton, Pageable} from '@kinotic/continuum-client'
import {EntitiesService, EntityContext} from '@kinotic/structures-api'
import { ITask } from './ITask';
import opentelemetry, {SpanKind, SpanStatusCode, Tracer} from '@opentelemetry/api'
import info from '../../package.json' assert {type: 'json'}

/**
 * This class will generate tasks to find fake people
 */
export class MultiTenantFindTaskGenerator implements ITaskGenerator {

    private continuumTaskGenerator: ContinuumOperationTaskGenerator
    private personEntityService: PersonEntityService
    private entityContext: EntityContext
    private tracer: Tracer

    constructor(connectionInfoSupplier: () => Promise<ConnectionInfo>,
                totalToExecute: number,
                pageSize: number,
                totalTenants: number) {

        const continuum = new ContinuumSingleton()
        this.personEntityService = new PersonEntityService(new EntitiesService(continuum.serviceRegistry))

        this.continuumTaskGenerator = new ContinuumOperationTaskGenerator(connectionInfoSupplier,
                                                                          continuum,
                                                                          totalToExecute,
                                                                          this.createTaskFactory(pageSize))
        const ids = generateMultipleDeterministicIds(totalTenants)
        this.entityContext = {
            multiTenantSelection: ids
        }
        this.tracer = opentelemetry.trace.getTracer(
            'structures.load-generator',
            info.version
        )
    }

    getNextTask(): ITask {
        return this.continuumTaskGenerator.getNextTask()
    }

    hasMoreTasks(): boolean {
        return this.continuumTaskGenerator.hasMoreTasks()
    }

    private createTaskFactory(pageSize: number): ITaskFactory {
        return {
            createTask: () => {
                return {
                    name   : () => 'Find All People',
                    execute: async () => {
                        return this.tracer.startActiveSpan(
                            `MultiTenantFindTaskGenerator/findAll`,
                            {
                                kind: SpanKind.CLIENT
                            },
                            async(span) => {
                                return this.personEntityService.findAll(Pageable.create(0, pageSize), this.entityContext)
                                           .then(
                                               async (value) => {
                                                   span.end()
                                                   return value
                                               },
                                               async (ex) => {
                                                   span.recordException(ex)
                                                   span.setStatus({ code: SpanStatusCode.ERROR })
                                                   span.end()
                                                   throw ex
                                               })
                                           .then(() => {
                                           })
                            })
                    }
                }
            }
        }
    }

}
