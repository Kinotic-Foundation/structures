import {Entity, Id, MultiTenancyType} from '@kinotic/structures-data'
import {Address} from './Address.js'
import {Car, Motorcycle, Vehicle} from './Vehicles.js'
import {RetirementStatus} from './RetirementStatus.js'
// import {RetirementStatus} from './RetirementStatus.js'


@Entity(MultiTenancyType.SHARED)
export class Person {

    @Id
    public id!: string

    public firstName!: string

    public lastName!: string

    public birthDate!: Date

    public retirementStatus?: RetirementStatus

    public age?: number

    public address!: Address

    public vehicle!: Car | Motorcycle

    public favoriteColors!: string[]

    public favoriteVehicles!: Vehicle[]

    public favoriteNumbers!: number[]
}
