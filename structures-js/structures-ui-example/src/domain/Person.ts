import {Entity, Id, MultiTenancyType} from '@kinotic/structures-data'
import {Address} from './Address'
import {Car, Motorcycle, Vehicle} from './Vehicles'

@Entity(MultiTenancyType.SHARED)
export class Person {

    @Id
    public id!: string

    public firstName!: string

    public lastName!: string

    public age?: number

    public address!: Address

    public vehicle!: Car | Motorcycle

    public favoriteColors!: string[]

    public favoriteVehicles!: Vehicle[]

    public favoriteNumbers!: number[]

}
