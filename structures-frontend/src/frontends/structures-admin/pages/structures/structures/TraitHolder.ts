import {Trait} from '@/frontends/structures-admin/pages/structures/traits/Trait'


export class TraitHolder {

    public order: number
    public fieldName: string
    public fieldTrait: Trait

    constructor(order: number, fieldName: string, fieldTrait: Trait) {
        this.order = order
        this.fieldName = fieldName
        this.fieldTrait = fieldTrait
    }
}
