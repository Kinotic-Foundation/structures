import {Entity, Id, MultiTenancyType} from '@kinotic/structures-data'
import {Address} from './Address'

@Entity(MultiTenancyType.SHARED)
export class Person {

    @Id
    public id!: string

    public firstName!: string

    public lastName!: string

    public age!: number

    public address!: Address
}
