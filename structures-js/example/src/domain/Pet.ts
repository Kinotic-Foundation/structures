export enum PetType {
    DOG = 'DOG',
    CAT = 'CAT'
}

export class Pet {
    public name: string = ''
    public type: PetType = PetType.DOG
}

export class Dog extends Pet {
    public breed: string = ''
    public isGoodBoy: boolean = true

    constructor() {
        super()
        this.type = PetType.DOG
    }
}

export class Cat extends Pet {
    public lives: number = 9
    public isIndoor: boolean = true

    constructor() {
        super()
        this.type = PetType.CAT
    }
} 