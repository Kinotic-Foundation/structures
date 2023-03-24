export class Namespace {
    public name: string
    public description: string
    public updated: number

    constructor(name: string, description: string, updated: number) {
        this.name = name;
        this.description = description;
        this.updated = updated;
    }
}