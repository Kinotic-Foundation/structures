import { Identifiable } from '@kinotic/continuum'
export class Namespace implements Identifiable<string> {
    public id: string
    public description: string
    public updated: number

    constructor(id: string, description: string, updated: number) {
        this.id = id;
        this.description = description;
        this.updated = updated;
    }

}