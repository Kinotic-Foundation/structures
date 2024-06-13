import {Id} from '@kinotic/structures-api'

/**
 * Data joined via GQL Federation
 */
export class Person {

    @Id
    public id!: string

    public __typename: string = "Person"

}
