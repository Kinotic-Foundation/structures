
export class Trait {

    public id: string
    public name: string
    public describeTrait: string
    public schema: string
    public esSchema: string

    public created: number
    public updated: number

    public required: boolean // should the GUI require a field to be filled out when looking at the item
    public includeInLabel: boolean
    public includeInQRCode: boolean
    public systemManaged: boolean // is a system managed default - cannot modify
    public operational: boolean // field that says we do not really add to the schema but provide some type of process
    public collection: boolean

    constructor(id: string,
                name: string,
                describeTrait: string,
                schema: string,
                esSchema: string,
                created: number,
                updated: number,
                required: boolean,
                includeInLabel: boolean,
                includeInQRCode: boolean,
                operational: boolean,
                collection: boolean) {
        this.id = id
        this.name = name
        this.describeTrait = describeTrait
        this.schema = schema
        this.esSchema = esSchema
        this.created = created
        this.updated = updated
        this.required = required
        this.includeInLabel = includeInLabel
        this.includeInQRCode = includeInQRCode
        this.systemManaged = false
        this.operational = operational
        this.collection = collection
    }

}
