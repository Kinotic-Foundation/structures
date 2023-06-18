import {DecoratedProperty, ObjectC3Type} from '@kinotic/continuum-idl-js'
import {Identifiable} from '@kinotic/continuum'

export class Structure implements Identifiable<string> {

    public id: string
    public name: string
    public namespace: string
    public description: string
    public entityDefinition: ObjectC3Type
    public created: number // do not ever set, system managed
    public updated: number // do not ever set, system managed
    public published: boolean // do not ever set, system managed
    public publishedTimestamp: number // do not ever set, system managed
    public itemIndex: string // do not ever set, system managed
    public decoratedProperties: DecoratedProperty[] // do not ever set, system managed


    constructor(id: string,
                name: string,
                namespace: string,
                description: string,
                entityDefinition: ObjectC3Type,
                created: number,
                updated: number,
                published: boolean,
                publishedTimestamp: number,
                itemIndex: string,
                decoratedProperties: DecoratedProperty[]) {
        this.id = id
        this.name = name
        this.namespace = namespace
        this.description = description
        this.entityDefinition = entityDefinition
        this.created = created
        this.updated = updated
        this.published = published
        this.publishedTimestamp = publishedTimestamp
        this.itemIndex = itemIndex
        this.decoratedProperties = decoratedProperties
    }
}
