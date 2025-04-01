import {Entity, MultiTenancyType, EntityType} from '@kinotic/structures-api'

@Entity(MultiTenancyType.SHARED, EntityType.STREAM)
export class Alert {

}