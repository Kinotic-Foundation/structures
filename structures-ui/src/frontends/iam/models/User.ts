import { IamParticipant } from '@/frontends/iam/models/IamParticipant'

export interface UserMetadata {
    type: 'user'
    firstName: string
    lastName: string
}

export class User extends IamParticipant {

    public metadata: UserMetadata = {
        type: 'user',
        firstName: '',
        lastName: ''
    }


    constructor(identity: string) {
        super(identity)
    }
}
