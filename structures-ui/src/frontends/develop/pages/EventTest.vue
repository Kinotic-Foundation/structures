<template>
    <v-container fluid class="pa-2" fill-height>
    <v-layout justify-center row fill-height>
        <v-flex md6 lg6>
            <v-card class="fill-height mr-1" ref="form">
                <v-card-title primary-title>
                        <h2 class="headline mb-0">Event Test</h2>
                </v-card-title>

                <v-card-text>

                    <v-text-field
                            v-model="destination"
                            label="Destination">
                    </v-text-field>

                    <v-toolbar flat>
                        <v-toolbar-title>Headers</v-toolbar-title>
                        <v-spacer></v-spacer>
                        <v-dialog v-model="dialog" max-width="500px">
                            <template v-slot:activator="{ on }">
                                <v-btn color="primary" v-on="on">New Header</v-btn>
                            </template>
                            <v-card>
                                <v-card-title>
                                    <span class="headline">{{ formTitle }}</span>
                                </v-card-title>

                                <v-card-text>
                                    <v-container grid-list-md>
                                        <v-layout wrap>
                                            <v-flex xs12 sm6 md6>
                                                <v-text-field v-model="editedItem.name" label="Name"></v-text-field>
                                            </v-flex>
                                            <v-flex xs12 sm6 md6>
                                                <v-text-field v-model="editedItem.value" label="Value"></v-text-field>
                                            </v-flex>
                                         </v-layout>
                                    </v-container>
                                </v-card-text>

                                <v-card-actions>
                                    <v-spacer></v-spacer>
                                    <v-btn color="blue darken-1" text @click="closeDialog">Cancel</v-btn>
                                    <v-btn color="blue darken-1" text @click="saveDialog">Save</v-btn>
                                </v-card-actions>
                            </v-card>
                        </v-dialog>
                    </v-toolbar>

                    <v-data-table
                            :headers="tableHeaders"
                            :items="eventHeaders"
                            :server-items-length="eventHeaders.length"
                            hide-default-footer>
                        <template v-slot:items="props">
                            <td>
                                <v-edit-dialog
                                        :return-value.sync="props.item.name"
                                        large
                                        lazy>
                                    {{ props.item.name }}
                                    <template v-slot:input>
                                        <v-text-field
                                                v-model="props.item.name"
                                                label="Edit"
                                                single-line
                                                counter
                                        ></v-text-field>
                                    </template>
                                </v-edit-dialog>
                            </td>
                            <td class="text-xs-right">
                                <v-edit-dialog
                                        :return-value.sync="props.item.value"
                                        large
                                        lazy>

                                    <div>{{ props.item.value }}</div>

                                    <template v-slot:input>
                                        <div class="mt-3 title">Update Value</div>
                                    </template>
                                    <template v-slot:input>
                                        <v-text-field
                                                v-model="props.item.value"
                                                label="Edit"
                                                single-line
                                                counter
                                                autofocus>
                                        </v-text-field>
                                    </template>
                                </v-edit-dialog>
                            </td>
                        </template>

                        <!--  Add Action columns  -->
                        <template v-slot:item.action="{ item }" >
                            <v-icon title="Edit"
                                    small
                                    class="mr-2"
                                    @click="editItem(item)">
                                {{editIcon}}
                            </v-icon>
                            <v-icon title="Delete"
                                    small
                                    @click="deleteItem(item)">
                                {{deleteIcon}}
                            </v-icon>
                        </template>

                    </v-data-table>
                    <v-divider class="mt-5"></v-divider>

                    <div style="height:200px;width:100%;">
                        <AceEditor v-model="body" :config="config" />
                    </div>

                </v-card-text>
                <v-divider class="mt-5"></v-divider>
                <v-card-actions>
                    <v-btn text @click="cancel">Cancel</v-btn>
                    <v-spacer></v-spacer>
                    <v-btn color="primary" text @click="submit">Submit</v-btn>
                </v-card-actions>
            </v-card>
        </v-flex>
        <v-flex md6 lg6 fill-height>
            <v-card class="ml-1">
                <v-card-title primary-title>
                    <v-toolbar text>
                        <v-toolbar-title>Subscriptions</v-toolbar-title>
                        <v-spacer></v-spacer>
                        <v-btn color="primary" text @click="clear">Clear</v-btn>
                    </v-toolbar>
                </v-card-title>

                <v-card-text>
                    <div class="event-list">
                        <div v-for="event of events" >
                            <div class="event">
                                <highlight-code lang="json">
                                    {{convertToJsonString(event)}}
                                </highlight-code>
                            </div>
                        </div>
                    </div>
                </v-card-text>
            </v-card>
        </v-flex>
    </v-layout>
    </v-container>
</template>

<script lang="ts">
import Vue from 'vue'
import { Component, Prop, Emit } from 'vue-property-decorator'
import { Event, IEventBus, EventConstants, IEvent } from '@kinotic-foundation/continuum-js'
import { Subscription } from 'rxjs'
import { inject } from 'inversify-props'
import { NoCache } from '@/frontends/decorators'
import {
    mdiPencil,
    mdiDelete
} from '@mdi/js'
import { UUID } from 'angular2-uuid'

const replyToDestination = EventConstants.SERVICE_DESTINATION_PREFIX + UUID.UUID() + '@continuum.js.test.EventTest/replyHandler'

class EventHeader {
    public name: string = ''
    public value: string = ''
}

@Component({
    components: { },
    props: { }
})
export default class EventTest extends Vue {

    @inject()
    public eventBus!: IEventBus

    public destination: string = 'srv://org.kinotic.testapplication.services.TestService/getFreeMemory'
    public eventHeaders: EventHeader[] = [{name: 'content-type', value: 'application/json'},
                                          {name: 'reply-to', value: replyToDestination},
                                          {name: EventConstants.CORRELATION_ID_HEADER, value: UUID.UUID()}]
    public body: string = ''

    public dialog: boolean = false
    public tableHeaders: any = [
        {
            text: 'Name',
            value: 'name',
            sortable: false
        },
        {
            text: 'Value',
            value: 'value',
            sortable: false
        },
        { text: 'Actions', value: 'action', sortable: false, align: 'end' }
    ]
    public editedIndex: number = -1
    public editedItem: EventHeader = {name: '', value: ''}
    public defaultItem: EventHeader = {name: '', value: ''}

    public events: IEvent[] = []
    public subscription: Subscription | null = null

    private editIcon: string = mdiPencil
    private deleteIcon: string = mdiDelete


    public config: any = {
        lang: 'json',
        theme: 'dracula'
    }

    // Lifecycle hook
    public mounted() {
        this.subscription = this.eventBus.observe(replyToDestination).subscribe((event: IEvent) => {
            this.events.push(event)
        })
    }

    public beforeDestroy() {
        if (this.subscription != null) {
            this.subscription.unsubscribe()
        }
    }

    // Computed props

    @NoCache // FIXME: remove when done testing custom decorator
    get formTitle() {
        return this.editedIndex === -1 ? 'New Item' : 'Edit Item'
    }

    // Methods
    public convertToJsonString(event: IEvent): string {
        const dataAsString = event.getDataString()
        const headers: any = {}
        event.headers.forEach ((v: string, k: string) => { headers[k] = v })

        let data = ''
        if(dataAsString.length > 0){
            if(event.headers.get(EventConstants.CONTENT_TYPE_HEADER) === EventConstants.CONTENT_JSON){
                data = JSON.parse(dataAsString)
            }else{
                data = dataAsString
            }
        }

        const jsonObj = {
            headers,
            data: data
        }

        return JSON.stringify(jsonObj, null, 2)
    }

    public editItem(item: EventHeader) {
        this.editedIndex = this.eventHeaders.indexOf(item)
        this.editedItem = Object.assign({}, item)
        this.dialog = true
    }

    public deleteItem(item: EventHeader) {
        const index = this.eventHeaders.indexOf(item)
        this.eventHeaders.splice(index, 1)
    }

    public closeDialog() {
        this.editedItem = Object.assign({}, this.defaultItem)
        this.editedIndex = -1
        this.dialog = false
    }

    public saveDialog() {
        if (this.editedIndex > -1) {
            const temp: EventHeader = Object.assign({}, this.editedItem)
            this.eventHeaders.splice(this.editedIndex, 1, temp)
        } else {
            this.eventHeaders.push(this.editedItem)
        }
        this.closeDialog()
    }

    public clear() {
        this.events.splice(0) // Doing this in a vue specific way so it will see the update .length will be missed
    }

    public cancel() {
        this.destination = ''
        this.eventHeaders = []
        this.body = ''
    }

    public submit() {
        const event: Event = new Event(this.destination)

        for (const header of this.eventHeaders) {
            event.setHeader(header.name, header.value)
        }
        if (this.body != null && this.body.length > 0) {
            event.setDataString(this.body)
        }

        // send event
        this.eventBus.send(event)
    }

}
</script>

<style scoped>
    .event{
        margin:8px 0;
    }
    .event-list{
        overflow-y: scroll;
    }
</style>

<style>
    .v-application code {
        padding: 0.5em;
        background: rgb(43, 43, 43);
        color: #bababa;
    }
</style>
