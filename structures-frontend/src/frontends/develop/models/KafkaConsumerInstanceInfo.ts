import { KafkaConsumerInstanceAssignment } from '@/frontends/develop/models/KafkaConsumerInstanceAssignment'

/**
 * Information about a Kafka Consumer Instance
 *
 * Created by Navid Mitchell on 12/5/20
 */
export class KafkaConsumerInstanceInfo {
    public memberId: string = ''
    public groupInstanceId: string = ''
    public clientId: string = ''
    public host: string = ''
    public instanceAssignments: KafkaConsumerInstanceAssignment[] = []
}
