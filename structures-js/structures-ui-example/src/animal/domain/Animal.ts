import {Discriminator, Entity, Id, MultiTenancyType} from '@kinotic/structures-api'

export class Animal {
    public type!: string
    public species!: string
}

export class Bear {
    public type = 'Bear'
    public species!: string
}

export class Cat {
    public type = 'Cat'
    public species!: string
}

export class Dog {
    public type = 'Dog'
    public species!: string
}

export class Rabbit {
    public type = 'Rabbit'
    public species!: string
}


@Entity(MultiTenancyType.SHARED)
export class AnimalDen {
    @Id
    public id!: string

    @Discriminator('type')
    public livesHere!: Bear | Cat | Dog | Rabbit

    public madeOf!: string
}
