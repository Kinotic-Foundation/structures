import {Address} from './Address.js'

export class Person {
    public id: string | null = null
    public firstName: string = ''
    public lastName: string = ''
    public address!: Address
}
