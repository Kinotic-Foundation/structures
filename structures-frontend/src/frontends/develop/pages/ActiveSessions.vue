<template>
  <v-container fluid>
    <v-row no-gutters>
      <v-col>
        <div>
          <v-data-table
              v-resize.quiet="onResize"
              :height="computedHeight"
              :headers="headers"
              :items="items"
              :loading="loading"
              :search="search"
              item-key="sessionId"
              loading-text="Loading... Please wait"
              class="elevation-1"
              :fixed-header=true
              :disable-pagination=true
              :hide-default-footer=true>

            <template v-slot:top>
              <v-toolbar flat >
                <v-text-field clearable
                              hide-details
                              single-line
                              v-model="search"
                              :append-icon="searchIcon"
                              label="Search"
                              @keydown.space.prevent>
                </v-text-field>
                <v-spacer></v-spacer>
                <v-chip
                    class="ma-2"
                    color="primary"
                    text-color="white">
                  {{activeSessionsLabel}}
                  <v-icon right>{{chipIcon}}</v-icon>
                </v-chip>
              </v-toolbar>
            </template>

            <!--  Add Action columns  -->
            <template v-slot:item.action="{ item }" >
              <v-icon title="Send"
                      small
                      :disabled="item.participantType !== 'device'"
                      @click="openEventDialog(item)">
                {{sendIcon}}
              </v-icon>
            </template>

            <template v-slot:no-data >
              <div class="py-12" v-if="search.length === 0" >
                Loading, please wait.
              </div>
            </template>

          </v-data-table>

          <confirm ref="confirm"></confirm>
        </div>
      </v-col>
    </v-row>
    <v-dialog v-model="dialog" >
      <v-card>
        <v-card-title>
          <span class="headline">Send Device Message</span>
        </v-card-title>
        <v-card-text>
          <v-container>
            <v-row>
              <v-col cols="12">
                <v-text-field label="Service Identifier*"
                              v-model="serviceIdentifier"
                              required>
                </v-text-field>
              </v-col>
              <!--                            <v-col cols="12">-->
              <!--                                <v-text-field label="Password*" type="password" required></v-text-field>-->
              <!--                            </v-col>-->
            </v-row>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue darken-1" text @click="closeEventDialog">Close</v-btn>
          <v-btn color="blue darken-1" text @click="sendEvent">Send</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-container>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator'
import { DataTableHeader } from 'vuetify'
import { mdiLan, mdiMagnify, mdiSend } from '@mdi/js'
import { StreamData, StreamOperation } from '@kinotic-foundation/continuum-js'
import { inject } from 'inversify-props'
import { IDeviceRpcService, ISessionInformationService, SessionMetadata } from '@/frontends/develop/services'
import { Subscription } from 'rxjs'
import Confirm from '@/frontends/continuum/components/Confirm.vue'


@Component({
  components: { Confirm }
})
export default class ActiveSessions extends Vue {

  private headers: DataTableHeader[] = [
    { text: 'Participant Identity', value: 'participantIdentity', sortable: true },
    { text: 'Participant Type', value: 'participantType', sortable: true },
    { text: 'Session Id', value: 'sessionId', sortable: false },
    { text: 'Last Used Date', value: 'lastUsedDate', sortable: false },
    { text: 'Actions', value: 'action', sortable: false, align: 'end' }
  ]

  /**
   * Services
   */
  @inject()
  private sessionInformationService!: ISessionInformationService

  @inject()
  private deviceRpcService!: IDeviceRpcService

  /**
   * Icons
   */
  private searchIcon: string = mdiMagnify
  private chipIcon: string = mdiLan
  private sendIcon: string = mdiSend

  /**
   * Internal state management vars
   */
  private computedHeight: number = (window.innerHeight - 166)
  private loading: boolean = true


  private items: SessionMetadata[] = []

  private search: string = ''

  private subscription: Subscription | null = null

  // Send Event RPC request data
  private dialog: boolean = false
  private selectedSessionMetadata: SessionMetadata | null = null
  private serviceIdentifier: string = 'sysinf'

  constructor() {
    super()
  }

  // Lifecycle hooks
  public mounted() {
    this.subscription = this.sessionInformationService.listActiveSessionsContinuous().subscribe(
        (data: StreamData<string, SessionMetadata>) => {
          if(this.loading){
            this.loading = false
          }
          if(data.streamOperation === StreamOperation.EXISTING){
            this.items.push(data.value)
          }else if(data.streamOperation === StreamOperation.UPDATE){
            const index = this.items.findIndex(value => value.sessionId === data.value.sessionId);
            if(index != -1){
              this.items[index] = data.value
            }
          }else if(data.streamOperation === StreamOperation.REMOVE){
            const index = this.items.findIndex(value => value.sessionId === data.value.sessionId);
            if (index != -1){
              this.items.splice(index,1)
            }
          }

        },
        (error:any) => this.displayAlert(error),
        () => this.displayAlert('Data Stream completed prematurely')
    )
  }

  public beforeDestroy() {
    if (this.subscription != null) {
      this.subscription.unsubscribe()
    }
  }

  // computed

  get activeSessionsLabel(): string {
    const length = this.items.length
    return length + ' Session'+(length > 1 ? 's' :'')+' Active'
  }

  // methods
  public onResize() {
    this.computedHeight = (window.innerHeight - 225)
  }

  public openEventDialog(sessionMetadata: SessionMetadata): void {
    this.selectedSessionMetadata = sessionMetadata
    // Temporarily this way  till we want the dialog to have control over send
    this.sendEvent()
    //this.dialog = true
  }

  public async sendEvent() {
    try {
      if (this.selectedSessionMetadata != null) {
        let text: string = await this.deviceRpcService.invoke(this.selectedSessionMetadata.participantIdentity, this.serviceIdentifier, null)
        console.log(text)
        this.$notify({group: 'info', type: 'activeSessionsInfo', text})
      }
      this.dialog = false
    } catch (error: any) {
      this.dialog = false
      this.displayAlert(error.message)
    }
  }

  public closeEventDialog(): void {
    this.selectedSessionMetadata = null
    this.dialog = false
  }

  private hideAlert() {
    (this.$notify as any as { close: (value: string) => void }).close('activeSessionsAlert')
  }

  private displayAlert(text: string) {
    this.$notify({ group: 'alert', type: 'activeSessionsAlert', text})
  }

}
</script>

<style scoped>

</style>
