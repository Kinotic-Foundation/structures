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
                        dense
                        item-key="id"
                        loading-text="Loading... Please wait"
                        class="elevation-1"
                        @click:row="rowClicked"
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

                    <template v-slot:item.name="{ item }">
                        <a :href="'/openapi-ui.html?namespace='+item.name" target="_blank" >{{ item.name }}</a>
                    </template>
                    <template v-slot:item.description="{ item }">
                        {{ item.description }}
                    </template>
                    <template v-slot:item.updated="{ item }">
                        {{ formatDate(item.updated) }}
                    </template>
                    <template v-slot:item.action="{ item }" >
                        <v-btn icon >
                            <v-icon medium
                                    class="mr-2"
                                    @click="editItem(item)"
                                    title="Edit" >
                              {{icons.edit}}
                            </v-icon>
                        </v-btn>
                        <v-btn icon >
                            <v-icon medium
                                    class="mr-2"
                                    @click="deleteItem(item)"
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

<!--                    <template v-slot:expanded-item="{ item }" >-->
<!--                        <td :colspan="headers.length" class="pa-1">-->

<!--                        </td>-->
<!--                    </template>-->

                    <template v-slot:top>
                        <v-toolbar flat>
                            <v-toolbar-title>Namespaces</v-toolbar-title>
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
                                    <v-btn color="primary" dark class="mb-2" v-on="on">New Namespace</v-btn>
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
                                                <v-text-field v-model="editedItem.name"
                                                              label="Name"
                                                              :error-messages="namespaceErrorMessage"
                                                              @keydown.space.prevent >
                                                </v-text-field>
                                            </v-list-item-content>
                                        </v-list-item>
                                        <v-list-item>
                                            <v-list-item-content>
                                                <v-text-field v-model="editedItem.description" label="Description"></v-text-field>
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
import {IStructureManager, ITraitManager} from "@/frontends/structures-admin/services"
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
import {INamespaceManager} from "@/frontends/structures-admin/pages/structures/namespaces/INamespaceManager";
import { Namespace } from './Namespace'
import {StructureHolder} from "@/frontends/structures-admin/pages/structures/structures/StructureHolder";
import {IndexNameHelper} from "@/frontends/structures-admin/pages/structures/util/IndexNameHelper";

@Component({
    components: { },
    props: { }
})
export default class Namespaces extends Vue {

    @inject()
    private namespaceManager!: INamespaceManager
    @inject()
    private structureManager!: IStructureManager

    private computedHeight: number = (window.innerHeight - 225)

    public items: Namespace[] = []
    // TODO: maybe we add a row expander that shows associated structures
    // public structuresForNamespace: Map<number,StructureHolder[]> = new Map<number,StructureHolder[]>()
    public loading: boolean = true
    public finishedInitialLoad: boolean = false
    public dialog: boolean = false
    public editedIndex: number = -1
    public defaultItem: Namespace = new Namespace("", "", 0)
    public editedItem: Namespace = Object.assign({}, this.defaultItem)

    public serverErrors: string = ""
    public namespaceErrorMessage: string = ""

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
        { text: 'Description', value: 'description', sortable: false },
        { text: 'Last Updated', value: 'updated' },
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

    public async rowClicked(event: any){
        // {
        //     index: number
        //     item: InternalDataTableItem
        //     columns: InternalDataTableHeader[]
        //     isExpanded: (item: DataTableItem) => boolean
        //     toggleExpand: (item: DataTableItem) => void
        //     isSelected: (items: DataTableItem[]) => boolean
        //     toggleSelect: (item: DataTableItem) => void
        // }

        // let response: StructureHolder[] = await this.structureManager.getAllNamespaceEquals(this.items[event.index].name, 100, 0, "name", true)
    }

    public async getAll() {
        try {
            this.loading = true
            let returnedItems: any = await this.namespaceManager.getAll(this.options.itemsPerPage, this.options.page-1, this.options.sortBy[0], this.options.sortDesc[0])
            this.loading = false
            this.options.totalItems = returnedItems.totalElements
            this.items = returnedItems.content

            if(!this.finishedInitialLoad){
                setTimeout(() => {
                    this.finishedInitialLoad = true
                }, 500)
            }
        }catch(error: any){
            console.log(error.stack)
            this.serverErrors = error.message

            if(!this.finishedInitialLoad){
                setTimeout(() => {
                    this.finishedInitialLoad = true
                }, 500)
            }
        }
    }

    get formTitle () {
        return this.editedIndex === -1 ? 'New Namespace' : 'Edit Namespace'
    }

    public async editItem(item: Namespace) {
        this.editedIndex = this.items.indexOf(item)
        try {
            let fresh: Namespace = await this.namespaceManager.getNamespace(item.name)
            this.editedItem = Object.assign({}, fresh)
            this.dialog = true
        }catch(error: any){
            console.log(error.stack)
            this.serverErrors = error.message
        }
    }

    public async deleteItem(item: Namespace) {
        const index = this.items.indexOf(item)
        if(confirm('Are you sure you want to Delete this Namespace?')) {
            try {
                await this.namespaceManager.delete(item.name)
                this.items.splice(index, 1)
                this.options.totalItems--
                if((this.options.totalItems/this.options.itemsPerPage) < this.options.page && this.options.page > 1){
                    this.options.page--
                    await this.getAll()
                }
            }catch(error: any){
                console.log(error.stack)
                this.serverErrors = error.message
            }
        }
    }

    public async save() {
        this.namespaceErrorMessage = IndexNameHelper.checkNameAndNamespace(this.editedItem.name, "Namespace")

        if(this.namespaceErrorMessage.length === 0){
            try{
                // FIXME: add some JSON validation around the schema and esSchema
                let item: Namespace = await this.namespaceManager.save(this.editedItem)
                if (this.editedIndex > -1) {
                    Object.assign(this.items[this.editedIndex], item)
                } else {
                    this.options.totalItems++
                    this.items.push(item)
                }
                this.close()
            }catch (error: any){
                console.log(error.stack)
                this.serverErrors = error.message
            }
        }
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
