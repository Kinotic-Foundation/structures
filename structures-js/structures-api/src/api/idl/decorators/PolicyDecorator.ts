import {EntityServiceDecorator} from '@/api/idl/decorators/EntityServiceDecorator'

export class PolicyDecorator extends EntityServiceDecorator {

    public policies: [string[]]

    constructor(policies: [string[]]) {
        super()
        this.type = 'PolicyDecorator'
        this.policies = policies
    }
}
