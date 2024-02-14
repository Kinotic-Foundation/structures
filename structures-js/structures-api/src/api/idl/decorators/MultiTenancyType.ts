/**
 * MultiTenancyType is an enum that represents the different types of multi-tenancy that can be applied to an entity.
 */
export enum MultiTenancyType {
    /**
     * Default, no multi-tenancy
     */
    NONE,

    /**
     * Shared table multi-tenancy
     */
    SHARED
    // We will support these in the future
//    ISOLATED,
//    HYBRID
}
