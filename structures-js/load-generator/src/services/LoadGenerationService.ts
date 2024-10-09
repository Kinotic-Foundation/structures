import {PersonEntityService} from '@/entity/services/PersonEntityService.js'
import {generatePeople} from '@/utils/DataUtil.js'
import {ConnectedInfo, ConnectionInfo, ContinuumSingleton} from '@kinotic/continuum-client'

export class LoadGenerationService {

    private readonly continuumConnectionInfo: ConnectionInfo
    private continuum: ContinuumSingleton
    private personService: PersonEntityService

    constructor(continuumConnectionInfo: ConnectionInfo) {
        this.continuumConnectionInfo = continuumConnectionInfo
        this.continuum = new ContinuumSingleton()
        this.personService = new PersonEntityService()
    }

    public connect(): Promise<ConnectedInfo>{
        return this.continuum.connect(this.continuumConnectionInfo)
    }

    public disconnect(): Promise<void> {
       return this.continuum.disconnect();
    }

    public persistFakeData(recordsToCreate: number): Promise<void> {
        return this.continuum.execute(async () => {
            const people = generatePeople(recordsToCreate)
            return this.personService.bulkSave(people)
        })
    }

}
