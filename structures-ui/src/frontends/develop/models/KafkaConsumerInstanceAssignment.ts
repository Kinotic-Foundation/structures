/**
 * Represents a single Topic an Partition that a {@link KafkaConsumerInstanceInfo} has been assigned to
 *
 * Created by Navid Mitchell on 12/16/20
 */
export class KafkaConsumerInstanceAssignment {
    public topic!: string
    public partition!: number

    public currentOffset!: number
    public offsetMetadata: string = ''
    public logEndOffset!: number
    public lag!: number
}
