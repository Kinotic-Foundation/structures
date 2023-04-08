<template>
    <v-container fluid fill-height>
        <v-layout justify-center>
            <v-flex text-xs-center mr-3 >
                <v-data-table
                        :height="computedHeight"
                        :max-height="computedHeight"
                        :headers="headers"
                        :items="items"
                        :server-items-length="options.totalItems"
                        :options.sync="options"
                        :loading="loading"
                        :single-expand="true"
                        show-expand
                        dense
                        item-key="id"
                        loading-text="Loading... Please wait"
                        class="elevation-1"
                        @page-count="pageCount = $event"
                        :fixed-header=true
                        :footer-props="{
                          showFirstLastPage: true,
                          firstIcon: icons.arrowLeft,
                          lastIcon: icons.arrowRight,
                          prevIcon: icons.minus,
                          nextIcon: icons.plus,
                          'items-per-page-options':options.rowsPerPageItems
                        }" >

                    <template v-slot:item.created="{ item }">
                        {{ formatDate(item.created) }}
                    </template>
                    <template v-slot:item.updated="{ item }">
                        {{ formatDate(item.updated) }}
                    </template>
                    <template v-slot:item.required="{ item }">
                        <v-icon small
                                class="mr-2"
                                title="Required">
                            {{ item.required ? "fas fa-check" : "" }}
                        </v-icon>
                    </template>
                    <template v-slot:item.systemManaged="{ item }">
                        <v-icon small
                                class="mr-2"
                                title="System Managed">
                            {{ item.systemManaged ? "fas fa-check" : "" }}
                        </v-icon>
                    </template>
                    <template v-slot:item.operational="{ item }">
                        <v-icon small
                                class="mr-2"
                                title="Operational">
                            {{ item.operational ? "fas fa-check" : "" }}
                        </v-icon>
                    </template>
                    <template v-slot:item.collection="{ item }">
                        <v-icon small
                                class="mr-2"
                                title="Collection">
                            {{ item.collection ? "fas fa-check" : "" }}
                        </v-icon>
                    </template>
                    <template v-slot:item.action="{ item }" >
                        <v-btn icon >
                            <v-icon medium
                                    class="mr-2"
                                    @click="editItem(item)"
                                    v-show="!item.systemManaged"
                                    title="Edit" >
                              {{icons.edit}}
                            </v-icon>
                        </v-btn>
                        <v-btn icon >
                            <v-icon medium
                                    class="mr-2"
                                    @click="deleteItem(item)"
                                    v-show="!item.systemManaged"
                                    title="Delete" >
                              {{icons.delete}}
                            </v-icon>
                        </v-btn>
                    </template>

                    <template v-slot:no-data>
                        <div class="py-12" >
                            <v-btn color="primary" @click="getAll" v-show="!loading">No Data - Push To Search Again</v-btn>
                        </div>
                    </template>

                    <template v-slot:expanded-item="{ item }" >
                        <td :colspan="headers.length" class="pa-1">
                            <v-list >
                                <v-list-item>
                                    JS Schema : {{item.schema}}
                                </v-list-item>
                                <v-list-item>
                                    ES Schema : {{item.esSchema}}
                                </v-list-item>
                            </v-list>
                        </td>
                    </template>


                    <template v-slot:top>
                        <v-toolbar flat>
                            <v-toolbar-title>Trait Templates</v-toolbar-title>
                            <v-divider
                                    class="mx-4"
                                    inset
                                    vertical
                            ></v-divider>
                            <v-alert dense
                                     outlined
                                     type="error"
                                     style="margin: 0 auto; text-align: center"
                                     v-show="serverErrors.length > 0">
                                {{ serverErrors }}
                            </v-alert>
                            <v-spacer></v-spacer>
                            <v-dialog v-model="dialog" fullscreen hide-overlay persistent transition="dialog-bottom-transition" >
                                <template v-slot:activator="{ on }">
                                    <v-btn color="primary" dark class="mb-2" v-on="on">New Trait</v-btn>
                                </template>
                                <v-card>

                                    <v-toolbar>
                                        <v-toolbar-title>{{ formTitle }}</v-toolbar-title>
                                        <v-spacer></v-spacer>
                                        <v-toolbar-items>
                                            <v-btn icon @click="close">
                                                <v-icon>{{icons.close}}</v-icon>
                                            </v-btn>
                                        </v-toolbar-items>
                                    </v-toolbar>

                                    <v-app-bar dark fixed bottom color="primary" >
                                        <v-spacer></v-spacer>
                                        <v-spacer></v-spacer>
                                        <v-btn dark text @click="close">Cancel</v-btn>
                                        <v-btn dark text @click="save">Save</v-btn>
                                    </v-app-bar>

                                    <v-list two-line rounded style="margin-bottom: 3em;" >
                                        <v-subheader>Basic Information</v-subheader>
                                        <v-alert dense
                                                 outlined
                                                 type="error"
                                                 v-show="serverErrors.length > 0">
                                            {{ serverErrors }}
                                        </v-alert>
                                        <v-list-item>
                                            <v-list-item-content>
                                                <v-text-field v-model="editedItem.name" label="Trait Name" autofocus></v-text-field>
                                            </v-list-item-content>
                                        </v-list-item>
                                        <v-list-item>
                                            <v-list-item-content>
                                                <v-text-field v-model="editedItem.schema" label="JS Schema"></v-text-field>
                                            </v-list-item-content>
                                        </v-list-item>
                                        <v-list-item>
                                            <v-list-item-content>
                                                <v-text-field v-model="editedItem.esSchema" label="ES Schema"></v-text-field>
                                            </v-list-item-content>
                                        </v-list-item>
                                        <v-list-item>
                                            <v-list-item-content>
                                                <v-text-field v-model="editedItem.describeTrait" label="Describe Trait"></v-text-field>
                                            </v-list-item-content>
                                        </v-list-item>
                                        <v-list-item>
                                            <v-list-item-content>
                                                <v-switch v-model="editedItem.required"
                                                          class="ma-2"
                                                          label="Required"
                                                          messages="Makes field required">
                                                </v-switch>
                                                <v-switch v-model="editedItem.operational"
                                                          class="ma-2"
                                                          label="Operational"
                                                          messages="Field provides functional operations to be performed, will never be shown in a GUI; i.e. External IP address.">
                                                </v-switch>
                                                <v-switch v-model="editedItem.collection"
                                                          class="ma-2"
                                                          label="Collection"
                                                          messages="Field provides a collection (array) of values.">
                                                </v-switch>
                                            </v-list-item-content>
                                        </v-list-item>
                                    </v-list>
                                </v-card>
                            </v-dialog>
                        </v-toolbar>
                    </template>
                </v-data-table>
            </v-flex>
        </v-layout>
    </v-container>
</template>

<script lang="ts">
import { Component, Vue, Watch } from 'vue-property-decorator'
import { ITraitManager } from "@/frontends/structures-admin/services"
import { Trait } from '@/frontends/structures-admin/pages/structures/traits/Trait'
import { inject } from 'inversify-props'
import {
  mdiPlus,
  mdiPencil,
  mdiDelete,
  mdiClose,
  mdiMinusCircle,
  mdiPlusCircle,
  mdiArrowCollapseLeft,
  mdiArrowCollapseRight
} from '@mdi/js'

@Component({
    components: { },
    props: { }
})
export default class Traits extends Vue {

    @inject()
    private traitManager!: ITraitManager

    private computedHeight: number = (window.innerHeight - 225)

    public items: Trait[] = []
    public loading: boolean = true
    public finishedInitialLoad: boolean = false
    public dialog: boolean = false
    public editedIndex: number = -1
    public defaultItem: Trait = new Trait("","","","","",0,0,true,false,false,false, false)
    public editedItem: Trait = Object.assign({}, this.defaultItem)

    public serverErrors: string = ""

    public options: any = {
        mustSort: true,
        sortDesc: [true],
        page: 1,
        totalItems: 0,
        itemsPerPage: 10,
        sortBy: ["name"],
        rowsPerPageItems: [5,10,25,50,75,100,-1]
    }

    // NOTE: Cannot Sort on Fields that are set up for Full Text Search.
    public headers: any = [
        {text: 'Name',align: 'left',value: 'name'},
        { text: 'Description', value: 'describeTrait', sortable: false },
        // { text: 'JS Schema', value: 'schema', sortable: false },
        // { text: 'ES Schema', value: 'esSchema', sortable: false },
        // { text: 'Created', value: 'created' },
        // { text: 'Last Updated', value: 'updated' },
        { text: 'Required',align: 'center', value: 'required' },// should the GUI require a field to be filled out when looking at the item
        { text: 'System Managed',align: 'center', value: 'systemManaged' },// system managed traits
        { text: 'Operational',align: 'center', value: 'operational' },
        { text: 'Collection',align: 'center', value: 'collection' },
        { text: 'Actions', value: 'action', sortable: false }
      ]

    private icons = {
      close: mdiClose,
      add: mdiPlus,
      edit: mdiPencil,
      delete: mdiDelete,
      minus: mdiMinusCircle,
      plus: mdiPlusCircle,
      arrowLeft: mdiArrowCollapseLeft,
      arrowRight: mdiArrowCollapseRight
    }

    constructor() {
        super()
    }

    // Lifecycle hook
    public mounted() {
        this.getAll()
    }

    public beforeDestroy() {
    }

    @Watch('options')
    public watchPagination(value: any, oldValue: any){
        if(value.sortBy.length === 0){
            value.sortBy = ["name"]
            value.sortDesc = [true]
        }
        this.options = value
        if(this.finishedInitialLoad){
            this.getAll()
        }
    }

    @Watch('dialog')
    public watchDialog(value: boolean, oldValue: boolean){
        value || this.close()
    }

    public formatDate(timeInMills: number){
        let [date, time] = new Date(timeInMills).toLocaleString('en-US', {hour12: false}).split(', ')
        return date + " " + time
    }

    public getAll() {
        this.loading = true
        this.traitManager.getAll(this.options.itemsPerPage, this.options.page-1, this.options.sortBy[0], this.options.sortDesc[0]).then((returnedItems: any) => {
            this.loading = false
            this.options.totalItems = returnedItems.totalElements
            this.items = returnedItems.content

            if(!this.finishedInitialLoad){
                setTimeout(() => {
                    this.finishedInitialLoad = true
                }, 500)
            }

        })
        .catch((error: any) => {
            console.log(error.stack)
            this.serverErrors = error.message

            if(!this.finishedInitialLoad){
                setTimeout(() => {
                    this.finishedInitialLoad = true
                }, 500)
            }

        })
    }

    get formTitle () {
        return this.editedIndex === -1 ? 'New Trait' : 'Edit Trait'
    }

    public editItem(item: Trait) {
        this.editedIndex = this.items.indexOf(item)
        this.traitManager.getTraitById(item.id).then((fresh) => {
          this.editedItem = Object.assign({}, fresh)
          this.dialog = true
        })
    }

    public deleteItem(item: Trait) {
        const index = this.items.indexOf(item)
        if(confirm('Are you sure you want to Delete this Trait?')) {
            this.traitManager.delete(item.id).then((data) => {
                this.items.splice(index, 1)
                this.options.totalItems--
                if((this.options.totalItems/this.options.itemsPerPage) < this.options.page && this.options.page > 1){
                    this.options.page--
                    this.getAll()
                }
            }).catch((error: any) => {
                console.log(error.stack)
                this.serverErrors = error.message
            })
        }
    }

    public save() {
        // FIXME: add some JSON validation around the schema and esSchema
        this.traitManager.save(this.editedItem).then((item) => {
            if (this.editedIndex > -1) {
                Object.assign(this.items[this.editedIndex], item)
            } else {
                this.options.totalItems++
                this.items.push(item)
            }
            this.close()
        })
        .catch((error: any) => {
            console.log(error.stack)
            this.serverErrors = error.message
        })

    }

    public close () {
        this.dialog = false
        setTimeout(() => {
            this.editedItem = Object.assign({}, this.defaultItem)
            this.editedIndex = -1
            this.serverErrors = ""
        }, 300)
    }

}
</script>

<style scoped>

</style>
