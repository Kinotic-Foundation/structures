/**
 * AccessPath defines a path that can be referenced by an {@link AccessPolicy}
 */
export class AccessPattern {

    public pattern: string


    constructor(path: string) {
        this.pattern = path
    }

}
