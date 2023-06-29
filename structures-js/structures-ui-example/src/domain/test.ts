import {Entity, Id, MultiTenancyType} from '@kinotic/structures-data'


export abstract class Vehicle {
    public vehicleType!: string
}

export class Car extends Vehicle {
    public numberOfDoors!: number
}

export class Motorcycle extends Vehicle {
    public hasSideCar!: boolean
}

export class Address{
    public street1!: string

    public street2!: string

    public city!: string

    public state!: string

    public zip!: string
}



@Entity(MultiTenancyType.SHARED)
export class Person {

    @Id
    public id!: string

    public firstName!: string

    public lastName!: string

    public age!: number

    public address!: Address

    public vehicle!: Car | Motorcycle
}
