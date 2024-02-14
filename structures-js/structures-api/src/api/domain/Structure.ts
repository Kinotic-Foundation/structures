import {ObjectC3Type} from '@kinotic/continuum-idl'
import {Identifiable} from '@kinotic/continuum-client'

export class Structure implements Identifiable<string> {
    public id!: string | null
    public namespace!: string
    public name!: string
    public entityDefinition!: ObjectC3Type
    public description?: string | null
    public created!: number // do not ever set, system managed
    public updated!: number // do not ever set, system managed
    public published!: boolean // do not ever set, system managed
    public publishedTimestamp!: number // do not ever set, system managed

    constructor(namespace: string,
                name: string,
                entityDefinition: ObjectC3Type,
                description?: string | null) {
        this.namespace = namespace
        this.name = name
        this.entityDefinition = entityDefinition
        this.description = description
    }

}
