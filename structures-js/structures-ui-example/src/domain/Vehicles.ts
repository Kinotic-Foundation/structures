export abstract class Vehicle {
    public vehicleType!: string
}

export class Car extends Vehicle {
    public numberOfDoors!: number
}

export class Motorcycle extends Vehicle {
    public hasSideCar!: boolean
}
