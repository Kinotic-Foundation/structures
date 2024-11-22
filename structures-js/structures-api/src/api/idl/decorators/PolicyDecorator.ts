import {EntityServiceDecorator} from '@/api/idl/decorators/EntityServiceDecorator.js'

export class PolicyDecorator extends EntityServiceDecorator {

    public policies: [string[]]

    constructor(policies: [string[]]) {
        super()
        this.type = 'Policy'
        this.policies = policies
    }
}
