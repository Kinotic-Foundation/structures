
/**
 * Represents a query parameter for a named query.
 */
export class QueryParameter {
    public key: string
    public value: any

    constructor(key: string, value: any) {
        this.key = key
        this.value = value
    }
}
