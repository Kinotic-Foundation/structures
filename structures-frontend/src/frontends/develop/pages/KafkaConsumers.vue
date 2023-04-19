<template>
  <v-container fluid>
    <v-row no-gutters>
      <v-col>
          <v-data-table
              v-resize.quiet="onResize"
              :height="computedHeight"
              :headers="headers"
              :items="items"
              :loading="loading"
              :search="search"
              item-key="groupId"
              loading-text="Loading... Please wait"
              class="elevation-1"
              show-expand
              fixed-header
              disable-pagination
              hide-default-footer>

            <template v-slot:top>
              <v-toolbar flat >
                <v-text-field clearable
                              hide-details
                              single-line
                              v-model="search"
                              :append-icon="icons.searchIcon"
                              label="Search"
                              @keydown.space.prevent>
                </v-text-field>
                <v-spacer></v-spacer>
                <v-chip
                    class="ma-2"
                    color="primary"
                    text-color="white">
                  {{activeConsumersLabel}}
                  <v-icon right>{{icons.chipIcon}}</v-icon>
                </v-chip>
              </v-toolbar>
            </template>

            <template v-slot:expanded-item="{ headers, item }">
              <td v-for="instance in item.consumerInstances" :key="item.message" :colspan="headers.length">
                <v-row>
                  <v-col md="6" sm='12'>
                    <v-simple-table dense>
                      <template v-slot:default>
                        <thead>
                          <tr>
                            <td colspan="3"></td>
                          </tr>
                        </thead>
                        <tbody>
                        <tr>
                          <td class="font-weight-bold td--border-bottom-none">Member ID:</td>
                          <td class="td--border-bottom-none">{{instance.memberId}}</td>
                        </tr>
                        <tr>
                          <td class="font-weight-bold td--border-bottom-none">Client ID:</td>
                          <td class="td--border-bottom-none">{{instance.clientId}}</td>
                        </tr>
                        <tr>
                          <td class="font-weight-bold td--border-bottom-none">Hostname:</td>
                          <td class="td--border-bottom-none">{{instance.host}}</td>
                        </tr>
                        </tbody>
                      </template>
                    </v-simple-table>
                  </v-col>
                  <v-col md="6" sm='12'>
                    <v-simple-table dense
                                    fixed-header>
                      <template v-slot:default>
                        <thead>
                        <tr>
                          <th class="text-left">
                            Topic - Partition
                          </th>
                          <th class="text-left">
                            Lag
                          </th>
                          <th class="text-left">
                            Offset
                          </th>
                          <th class="text-left">
                            End
                          </th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr
                            v-for="assignment in instance.instanceAssignments"
                            :key="assignment.topic + assignment.partition">

                          <td>{{assignment.topic}} - {{assignment.partition}}</td>
                          <td>{{assignment.lag}}</td>
                          <td>{{assignment.currentOffset}}</td>
                          <td>{{assignment.logEndOffset}}</td>
                        </tr>
                        </tbody>
                      </template>
                    </v-simple-table>
                  </v-col>
                </v-row>
              </td>
            </template>

            <template v-slot:no-data >
              <div class="py-12" v-if="search.length === 0" >
                Loading, please wait.
              </div>
            </template>

          </v-data-table>
      </v-col>
    </v-row>
  </v-container>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator'
import { IKafkaService } from '@/frontends/develop/services'
import { inject } from 'inversify-props';
import { mdiLan, mdiMagnify } from '@mdi/js'
import { KafkaConsumerGroupInfo } from '@/frontends/develop/models/KafkaConsumerGroupInfo'
import { DataTableHeader } from 'vuetify'
import { Subscription } from 'rxjs'

@Component({
  components: { },
  props: { }
})
export default class KafkaConsumers extends Vue {

  @inject()
  private kafkaService!: IKafkaService

  private headers: DataTableHeader[] = [
    { text: 'Group Id', value: 'groupId', sortable: true },
    { text: 'Group State', value: 'groupState', sortable: true },
    { text: 'Coordinator', value: 'coordinator', sortable: false },
    { text: 'Strategy', value: 'partitionAssignor', sortable: false }
  ]

  /**
   * Icons
   */
  private icons = {
    searchIcon: mdiMagnify,
    chipIcon: mdiLan
  }

  /**
   * Internal state management vars
   */
  private computedHeight: number = (window.innerHeight - 166)
  private loading: boolean = true

  private items: KafkaConsumerGroupInfo[] = []
  private subscription: Subscription | null = null

  private search: string = ''

  constructor() {
    super()
  }

  // Lifecycle hook
  public mounted() {
    this.subscription = this.kafkaService.findAllKafkaConsumers().subscribe(
        (data: KafkaConsumerGroupInfo) => {
          this.items.push(data)
        },
        (error:any) => {
          this.loading = false
          this.displayAlert(error)
        },
        () => {
          this.loading = false
          this.subscription = null
        }
    )
  }

  public beforeDestroy() {
    if (this.subscription != null) {
      this.subscription.unsubscribe()
    }
  }

  get activeConsumersLabel(): string {
    const length = this.items.length
    return length + ' Consumer Groups'+(length > 1 ? 's' :'')+' Active'
  }

  // methods
  public onResize() {
    this.computedHeight = (window.innerHeight - 225)
  }

  private hideAlert() {
    (this.$notify as any as { close: (value: string) => void }).close('kafkaConsumersAlert')
  }

  private displayAlert(text: string) {
    this.$notify({ group: 'alert', type: 'kafkaConsumersAlert', text})
  }


}
</script>

<style scoped>
  .td--border-bottom-none{
    border-bottom: 0 none !important;
  }
</style>
