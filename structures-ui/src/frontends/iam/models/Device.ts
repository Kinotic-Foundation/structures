import { IamParticipant } from '@/frontends/iam/models/IamParticipant'

export interface DeviceMetadata {
    type: 'device'
    description: string
}

export class Device extends IamParticipant {

    public metadata: DeviceMetadata = {
        type: 'device',
        description: ''
    }


    constructor(identity: string) {
        super(identity)
    }
}
