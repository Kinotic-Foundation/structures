import {
    Entity,
    Id,
    MultiTenancyType,
    Precision,
    PrecisionType,
    EntityServiceDecorators,
    $Role, $Policy,
    Policy, TenantId,
} from '@kinotic/structures-api'
import {Address} from './Address.js'

@EntityServiceDecorators(
    {
        allCreate: [
            $Policy([['data:create']]),
            $Role(['admin'])
        ],
    }
)
@Entity(MultiTenancyType.SHARED, true)
@Policy([['data:read']])
export class Person {

    @Id
    public id!: string

    public firstName!: string

    public lastName!: string

    public birthDate!: Date

    @TenantId
    public tenantId!: string

    @Precision(PrecisionType.SHORT)
    public age?: number

    @Policy([['data:create']])
    public address!: Address

}
