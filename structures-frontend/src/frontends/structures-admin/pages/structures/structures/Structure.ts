import {DecoratedProperty, ObjectC3Type} from '@kinotic/continuum-idl-js'

export class Structure {

    public id: string
    public name: string
    public namespace: string
    public description: string
    public entityDefinition: ObjectC3Type
    public created: number
    public updated: number
    public published: boolean
    public publishedTimestamp: number
    public itemIndex: string
    public decoratedProperties: DecoratedProperty[]


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
