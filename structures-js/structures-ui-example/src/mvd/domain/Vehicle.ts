import {Entity, Id, MultiTenancyType} from '@kinotic/structures-api'
import {Person} from './Person'


@Entity(MultiTenancyType.SHARED)
export class Vehicle {

    @Id
    public id!: string
    public color!: string
    public manufacturer!: string
    public model!: string
    public type!: string
    public owner!: Person
}
