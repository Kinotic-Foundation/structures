import { KafkaConsumerInstanceInfo } from '@/frontends/develop/models/KafkaConsumerInstanceInfo'

/**
 * The consumer group state
 *
 * Created by Navid Mitchell on 11/25/20
 */
export enum ConsumerGroupState {

    UNKNOWN = "NONE",
    PREPARING_REBALANCE = "PREPARING_REBALANCE",
    COMPLETING_REBALANCE = "COMPLETING_REBALANCE",
    STABLE = "STABLE",
    DEAD = "DEAD",
    EMPTY = "EMPTY"

}

/**
 * Information about the status of a Kafka Consumer Group
 *
 * Created by Navid Mitchell on 11/25/20
 */
export class KafkaConsumerGroupInfo {
    public groupId!: string
    public groupState!: ConsumerGroupState
    public coordinator!: string
    public partitionAssignor!: string
    public consumerInstances: KafkaConsumerInstanceInfo[] = []
    public monitored: boolean = false
}
