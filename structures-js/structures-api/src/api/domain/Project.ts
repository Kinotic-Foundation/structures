import { Identifiable } from '@kinotic/continuum-client'
export class Project implements Identifiable<string> {
    public id: string | null = null;
    public applicationId: string
    public name: string
    public description: string
    public updated: number | null = null;

    constructor(id: string | null, applicationId: string, name: string, description: string) {
        this.id = id;
        this.applicationId = applicationId;
        this.name = name;
        this.description = description;
    }
}