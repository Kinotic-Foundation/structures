import {Trait} from '@/frontends/structures-admin/pages/structures/traits/Trait'

export class Structure {

    public id: string
    public description: string
    public created: number
    public published: boolean
    public publishedTimestamp: number
    public traits: Map<string, Trait>
    public metadata: Map<string, string>
    public updated: number


    constructor(id: string,
                description: string,
                created: number,
                published: boolean,
                publishedTimestamp: number,
                deleted: boolean,
                deletedTimestamp: number,
                traits: Map<string, Trait>,
                metadata: Map<string, string>,
                updated: number) {
        this.id = id
        this.description = description
        this.created = created
        this.published = published
        this.publishedTimestamp = publishedTimestamp
        this.traits = traits
        this.metadata = metadata
        this.updated = updated
    }

}
