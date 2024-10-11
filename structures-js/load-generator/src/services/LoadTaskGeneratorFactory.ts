import {LoadTestConfig} from '@/config/LoadTestConfig.js'
import {StructuresConnectionConfig} from '@/config/StructuresConnectionConfig.js'
import {ITaskGenerator} from '@/tasks/ITaskGenerator.js'
import {ITaskGeneratorFactory} from '@/tasks/ITaskGeneratorFactory.js'
import {MultiTenantTaskGeneratorDelegator, TenantId} from '@/tasks/MultiTenantTaskGeneratorDelegator.js'
import {SavePeopleTaskGenerator} from '@/tasks/SavePeopleTaskGenerator.js'
import {ConnectionInfo} from '@kinotic/continuum-client'


export class LoadTaskGeneratorFactory {

    public static createTaskGenerator(structuresConfig: StructuresConnectionConfig,
                                      loadTestConfig: LoadTestConfig): ITaskGenerator {

        if(loadTestConfig.testName === 'bulkLoadSmall') {

            const peopleGenFactory: ITaskGeneratorFactory<TenantId>
                      = (tenantId) => {
                return new SavePeopleTaskGenerator(
                    this.createConnectionInfo(tenantId, structuresConfig),
                    1000,
                    1000)
            }

            return new MultiTenantTaskGeneratorDelegator(loadTestConfig.beginTenantIdNumber,
                                                         loadTestConfig.numberOfTenants,
                                                         peopleGenFactory)

        }else if(loadTestConfig.testName === 'bulkLoadMedium') {

            const peopleGenFactory: ITaskGeneratorFactory<TenantId>
             = (tenantId) => {
                return new SavePeopleTaskGenerator(
                    this.createConnectionInfo(tenantId, structuresConfig),
                    2000,
                    10000)
            }

            return new MultiTenantTaskGeneratorDelegator(loadTestConfig.beginTenantIdNumber,
                                                         loadTestConfig.numberOfTenants,
                                                         peopleGenFactory)
        }else {
            throw new Error(`Unsupported test name: ${loadTestConfig.testName}`)
        }
    }

    private static createConnectionInfo(tenantId: string,
                                        structuresConfig: StructuresConnectionConfig):() => Promise<ConnectionInfo> {
        return  async () => {
            return {
                host                 : structuresConfig.structuresHost,
                port                 : structuresConfig.structuresPort,
                useSSL               : structuresConfig.stucturesUseSsl,
                maxConnectionAttempts: 5,
                connectHeaders       : {
                    login   : 'admin',
                    passcode: 'structures',
                    tenantId: tenantId
                }
            }
        }
    }

}
