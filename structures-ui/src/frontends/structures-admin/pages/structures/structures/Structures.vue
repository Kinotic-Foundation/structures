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
                        item-key="structure.id"
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

                    <template v-slot:item.id="{ item }">
                        {{ item.structure.id }}
                    </template>
                    <template v-slot:item.name="{ item }">
                        {{ item.structure.name }}
                    </template>
                    <template v-slot:item.namespace="{ item }">
                        <a :href="'/openapi-ui.html?namespace='+item.structure.namespace" target="_blank" >{{ item.structure.namespace }}</a>
                    </template>
                    <template v-slot:item.description="{ item }">
                        {{ item.structure.description }}
                    </template>
                    <template v-slot:item.created="{ item }">
                        {{ formatDate(item.structure.created) }}
                    </template>
                    <template v-slot:item.updated="{ item }">
                        {{ formatDate(item.structure.updated) }}
                    </template>
                    <template v-slot:item.publishedTimestamp="{ item }">
                        {{ formatDate(item.structure.publishedTimestamp) }}
                    </template>
                    <template v-slot:item.published="{ item }">
                        <v-icon medium
                                class="mr-2"
                                v-show="item.structure.published"
                                title="Structure Published">
                            fab fa-product-hunt
                        </v-icon>
                    </template>


                    <template v-slot:expanded-item="{ item }" >
                        <td :colspan="headers.length" class="pa-6">
                            <v-simple-table :key="item.traits.length" >
                                <template v-slot:default>
                                    <thead>
                                    <tr>
                                        <th></th>
                                        <th class="text-left">Field Name</th>
                                        <th class="text-left">Trait Name</th>
                                        <th class="text-left">Required</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                        <tr v-for="entry in item.traits" :key="entry.order" >
                                            <td></td>
                                            <td>{{ entry.fieldName }}</td>
                                            <td>{{ entry.fieldTrait.name }}</td>
                                            <td>
                                                <v-icon small
                                                        class="mr-2"
                                                        title="Required">
                                                    {{ entry.fieldTrait.required ? "fas fa-check" : "" }}
                                                </v-icon>
                                            </td>
                                        </tr>
                                    </tbody>
                                </template>
                            </v-simple-table>
                        </td>
                    </template>


                    <template v-slot:item.action="{ item }" >
                        <v-btn icon >
                            <v-icon medium
                                    class="mr-2"
                                    @click="editItem(item)"
                                    title="Edit">
                                {{icons.edit}}
                            </v-icon>
                        </v-btn>
                        <v-btn icon >
                            <v-icon medium
                                    class="mr-2"
                                    @click="deleteItem(item)"
                                    title="Delete">
                                {{icons.delete}}
                            </v-icon>
                        </v-btn>
                        <v-btn icon
                               v-show="!item.structure.published"
                               :disabled="publishingId.length > 0"
                               :loading="publishingId === item.structure.id"
                               :key="item.structure.id" >
                            <v-icon medium
                                    class="mr-2"
                                    @click="publish(item)"
                                    title="Publish">
                                fab fa-product-hunt
                            </v-icon>
                        </v-btn>
                        <v-btn icon v-show="item.structure.published">
                            <v-icon medium
                                    class="mr-2"
                                    @click="toStructureItemsPage(item)"
                                    title="Manage">
                                {{icons.database}}
                            </v-icon>
                        </v-btn>
                    </template>

                    <template v-slot:no-data >
                        <div class="py-12" >
                            <v-btn color="primary" @click="getAll" v-show="!loading">No Data - Push To Search Again</v-btn>
                        </div>
                    </template>

                    <template v-slot:top>
                        <v-toolbar flat >
                            <v-toolbar-title>Structures</v-toolbar-title>
                            <v-divider
                                    class="mx-4"
                                    inset
                                    vertical>
                            </v-divider>
                            <v-alert dense
                                     outlined
                                     type="error"
                                     style="margin: 0 auto; text-align: center"
                                     v-show="serverErrors.length > 0">
                                {{ serverErrors }}
                            </v-alert>
                            <v-spacer></v-spacer>
                            <v-dialog v-model="dialog" fullscreen hide-overlay persistent transition="dialog-bottom-transition">
                                <template v-slot:activator="{ on }">
                                    <v-btn color="primary" dark class="mb-2" v-on="on">New Structure</v-btn>
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
                                        <v-btn dark text @click="close" >Cancel</v-btn>
                                        <v-btn dark text @click="save" :disabled="editedItem.structure.published && !structureChanged" >Save</v-btn>
                                    </v-app-bar>

                                    <v-list two-line rounded >
                                        <v-subheader>Basic Information</v-subheader>
                                        <v-alert dense
                                                 outlined
                                                 type="error"
                                                 v-show="serverErrors.length > 0">
                                            {{ serverErrors }}
                                        </v-alert>
                                        <v-list-item>
                                            <v-list-item-content>
                                                <v-text-field v-model="editedItem.structure.name"
                                                              label="Name"
                                                              autofocus
                                                              :error-messages="nameErrorMessage"
                                                              :readonly="editedItem.structure.published"
                                                              @keydown.space.prevent >
                                                </v-text-field>
                                            </v-list-item-content>
                                        </v-list-item>
                                        <v-list-item>
                                            <v-list-item-content>
                                                <v-select
                                                    label="Namespace"
                                                    v-model="selectedNamespace"
                                                    :items="namespaces"
                                                    :disabled="editedItem.structure.published"
                                                    :hint="selectedNamespace.name + (selectedNamespace.description ? ': ' + selectedNamespace.description : '')"
                                                    :error-messages="namespaceErrorMessage"
                                                    item-text="name"
                                                    return-object
                                                    persistent-hint
                                                    hide-selected
                                                    single-line >
                                                </v-select>
                                            </v-list-item-content>
                                        </v-list-item>
                                        <v-list-item>
                                            <v-list-item-content>
                                                <v-text-field @change="structureChanged = true" v-model="editedItem.structure.description" label="Description"></v-text-field>
                                            </v-list-item-content>
                                        </v-list-item>
                                        <v-list-item>
                                            <v-list-item-content>
                                                <v-switch v-model="requireIdForEditedItem"
                                                          label="Require 'id' field in all requests"
                                                          class="ma-2"
                                                          :readonly="editedItem.structure.published"
                                                          @change="updateEditedItemWithIdRequiredValue">
                                                </v-switch>
                                            </v-list-item-content>
                                        </v-list-item>
<!--                                      FIXME: Need to Support Uploading of image/logo-->
<!--                                        <v-list-item>-->
<!--                                            <v-list-item-content>-->
<!--                                                <v-text-field @change="structureChanged = true" v-model="pathToIcon" label="Path To Icon"></v-text-field>-->
<!--                                            </v-list-item-content>-->
<!--                                        </v-list-item>-->
                                    </v-list>
                                    <v-divider></v-divider>
                                    <v-list two-line subheader rounded style="margin-bottom: 3em;" >
                                        <v-subheader>Configured Traits - Sortable</v-subheader>
                                        <v-list-item>
                                            <v-list-item-content>
                                                <v-simple-table>
                                                    <template v-slot:default>
                                                        <thead>
                                                        <tr>
                                                            <th>
                                                                <v-fab-transition>
                                                                    <v-btn dark
                                                                           absolute
                                                                           top
                                                                           right
                                                                           fab
                                                                           color="primary"
                                                                           title="Add Traits"
                                                                           @click="openAddTraitDialog">
                                                                        <v-icon>{{icons.add}}</v-icon>
                                                                    </v-btn>
                                                                </v-fab-transition>
                                                            </th>
                                                            <th class="text-left">Field Name</th>
                                                            <th class="text-left">Trait</th>
                                                            <th class="text-left">Required</th>
                                                            <th class="text-left" v-show="!editedItem.structure.published">Actions</th>
                                                            <th></th>
                                                        </tr>
                                                        </thead>
                                                        <draggable :list="editedItem.traits"
                                                                   @end="onMoveCallback"
                                                                   tag="tbody"
                                                                   handle=".handle">
                                                            <tr v-for="entry in editedItem.traits"
                                                                :key="entry.fieldTrait.id"
                                                                style="cursor: grab;">
                                                                <td></td>
                                                                <td>{{ entry.fieldName }}</td>
                                                                <td>{{ entry.fieldTrait.name }}</td>
                                                                <td>
                                                                    <v-icon small
                                                                            class="mr-2"
                                                                            title="Required">
                                                                        {{ entry.fieldTrait.required ? "fas fa-check" : "" }}
                                                                    </v-icon>
                                                                </td>
                                                                <td>
                                                                    <v-icon medium
                                                                            @click="deleteTrait(entry.fieldName)"
                                                                            title="Delete"
                                                                            v-show="!editedItem.structure.published && !defaultTrait(entry)">
                                                                      {{icons.delete}}
                                                                    </v-icon>
                                                                </td>
                                                                <td><i class="fa fa-align-justify handle"></i></td>
                                                            </tr>
                                                        </draggable>
                                                    </template>
                                                </v-simple-table>
                                            </v-list-item-content>
                                        </v-list-item>
                                    </v-list>

                                </v-card>
                            </v-dialog>

                            <v-dialog v-model="traitDialog" max-width="600px">
                                <v-card>
                                    <v-list two-line subheader rounded>
                                        <v-subheader>
                                          Trait Information
                                          <div style="min-width: 80%" >
                                            <v-btn icon @click="closeTraitDialog" class="float-right bg-surface-variant">
                                              <v-icon>{{icons.close}}</v-icon>
                                            </v-btn>
                                          </div>
                                        </v-subheader>
                                        <v-list-item>
                                            <v-list-item-content>
                                                <v-text-field ref="traitCreation"
                                                              v-model="newTraitName"
                                                              label="Trait Field Name"
                                                              autofocus
                                                              :error-messages="traitFieldNameErrorMessage"
                                                              @keydown.space.prevent>
                                                </v-text-field>
                                            </v-list-item-content>
                                        </v-list-item>
                                        <v-list-item>
                                            <v-list-item-content>
                                                <v-text-field v-model="newTrait.describeTrait"
                                                              label="Describe Trait Field" >
                                                </v-text-field>
                                            </v-list-item-content>
                                        </v-list-item>
                                        <v-list-item>
                                            <v-list-item-content >

                                                <v-select
                                                        v-model="dummyTrait"
                                                        :items="traits"
                                                        :menu-props="{ top: true, offsetY: true }"
                                                        :item-disabled="checkTrait"
                                                        @change="cloneTrait"
                                                        item-text="name"
                                                        label="Traits"
                                                        persistent-hint
                                                        return-object
                                                        single-line>
                                                </v-select>

                                            </v-list-item-content>
                                        </v-list-item>
                                        <v-list-item v-if="newTrait.created !== 0">
                                            <v-list-item-content>
                                                <v-switch v-model="newTrait.required"
                                                          class="ma-2"
                                                          label="Required"
                                                          messages="Makes field required on all input forms.">
                                                </v-switch>
                                                <v-switch v-model="newTrait.operational"
                                                          class="ma-2"
                                                          label="Operational"
                                                          messages="Field provides functional operations to be performed, will never be shown in a GUI; i.e. VPN IP address.">
                                                </v-switch>
                                            </v-list-item-content>
                                        </v-list-item>
                                    </v-list>
                                    <v-card-actions>
                                        <v-spacer></v-spacer>
                                        <v-btn color="blue darken-1" text @click="closeTraitDialog">Done</v-btn>
                                        <v-btn color="blue darken-1" text @click="addNewTrait(newTraitName, newTrait)" :disabled="newTraitName.length === 0 || newTrait.created === 0">Add Trait</v-btn>
                                    </v-card-actions>
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
import {ITraitManager, IStructureManager} from "@/frontends/structures-admin/services"
import { Structure } from '@/frontends/structures-admin/pages/structures/structures/Structure'
import { Trait } from '@/frontends/structures-admin/pages/structures/traits/Trait'
import draggable from 'vuedraggable'
import {StructureHolder} from "@/frontends/structures-admin/pages/structures/structures/StructureHolder";
import {TraitHolder} from "@/frontends/structures-admin/pages/structures/structures/TraitHolder";
import {INamespaceManager} from "@/frontends/structures-admin/pages/structures/namespaces/INamespaceManager";
import {Namespace} from "@/frontends/structures-admin/pages/structures/namespaces/Namespace";
import { inject } from 'inversify-props'
import {
  mdiPlus,
  mdiPencil,
  mdiDelete,
  mdiClose,
  mdiDatabase,
  mdiMinusCircle,
  mdiPlusCircle,
  mdiArrowCollapseLeft,
  mdiArrowCollapseRight
} from '@mdi/js'
import {IndexNameHelper} from "@/frontends/structures-admin/pages/structures/util/IndexNameHelper";

@Component({
    components: { draggable },
    props: { }
})
export default class Traits extends Vue {

    @inject()
    private traitManager!: ITraitManager
    @inject()
    private structureManager!: IStructureManager
    @inject()
    private namespaceManager!: INamespaceManager

    private computedHeight: number = (window.innerHeight - 225)

    public items: StructureHolder[] = []
    public traits: Trait[] = []
    public namespaces: Namespace[] = []
    public loading: boolean = true
    public finishedInitialLoad: boolean = false
    public dialog: boolean = false
    public traitDialog: boolean = false
    public editedIndex: number = -1
    public newTraitName: string = ""
    public pathToIcon: string = ""
    public selectedNamespace: Namespace = new Namespace("", "", 0)
    public newTrait: Trait = new Trait("","","","","",0,0,true,true,true,true)
    public dummyTrait: Trait = new Trait("","","","","",0,0,true,true,true,true)
    public defaultTraits: TraitHolder[] = []
    public editedItem: StructureHolder = new StructureHolder(new Structure("","", "",0,false,0, "", false,0,new Map<string, Trait>(),new Map<string, string>(),0),this.defaultTraits)
    public structureChanged: boolean = false
    public publishingId: string = ""
    public requireIdForEditedItem: boolean = false

    public nameErrorMessage: string = ""
    public namespaceErrorMessage: string = ""
    public traitFieldNameErrorMessage: string = ""
    public serverErrors: string = ""


    public options: any = {
        mustSort: true,
        sortDesc: [true],
        page: 1,
        totalItems: 0,
        itemsPerPage: 10,
        sortBy: ["id"],
        rowsPerPageItems: [5,10,25,50,75,100,-1]
    }

    // NOTE: Cannot Sort on Fields that are set up for Full Text Search.
    public headers: any = [
        { text: '', value: 'data-table-expand', sortable: false },
        { text: 'Id',align: 'left',value: 'id'},
        { text: 'Namespace',align: 'left',value: 'namespace'},
        { text: 'Name',align: 'left',value: 'name'},
        { text: 'Description', value: 'description', sortable: false },
        { text: 'Created', value: 'created' },
        { text: 'Last Updated', value: 'updated' },
        { text: 'Published',align: 'center', value: 'published' },
        { text: 'Published On', value: 'publishedTimestamp' },
        { text: 'Actions', value: 'action', sortable: false }
      ]

    private icons = {
      close: mdiClose,
      add: mdiPlus,
      edit: mdiPencil,
      delete: mdiDelete,
      database: mdiDatabase,
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
        this.getAllNamespaces()
        this.getAllTraits()
        this.getAll()
    }

    public beforeDestroy() {
    }

    @Watch('options')
    public watchPagination(value: any, oldValue: any){
        if(value.sortBy.length === 0){
            value.sortBy = ["id"]
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

    get formTitle () {
        return this.editedIndex === -1 ? 'New Structure' : 'Edit Structure'
    }

    public updateEditedItemWithIdRequiredValue(){
        for(let entry of this.editedItem.traits){
            if(entry.fieldName === 'id'){
                entry.fieldTrait.required = this.requireIdForEditedItem
                break
            }
        }
    }

    public defaultTrait(item: any){
      let ret: boolean = false
      if(item.fieldName === 'id'
          || item.fieldName === 'deleted'
          || item.fieldName === 'deletedTime'
          || item.fieldName === 'updatedTime'
          || item.fieldName === 'structureId'){
        ret = true
      }
      return ret
    }

    public checkTrait(item: any){
        return item.name === `Reference ${this.editedItem.structure.id.trim()}`
    }

    public cloneTrait(item: any){
        this.newTrait = Object.assign({}, item)
        this.newTrait.describeTrait = ""
    }

    public formatDate(timeInMills: number){
        let ret: string = ""
        if(timeInMills !== 0){
            let [date, time] = new Date(timeInMills).toLocaleString('en-US', {hour12: false}).split(', ')
            ret = date + " " + time
        }
        return ret
    }

    public openAddTraitDialog(){
        this.traitDialog = true
        this.resetEditedItem()
    }

    public closeTraitDialog(){
        this.traitDialog = false
        this.resetEditedItem()
    }

    public deleteTrait(fieldName: string){
        this.editedItem.traits = this.editedItem.traits.filter((trait: TraitHolder) => {
            return trait.fieldName !== fieldName
        })
    }

    public async getAllNamespaces(){
        try {
            let returnedData: any = await this.namespaceManager.getAll(100, 0, "name", true)
            this.namespaces.length = 0
            this.namespaces = returnedData.content
        }catch (error: any){
            console.log(error.stack)
            this.serverErrors = error.message
        }
    }

    public async getAllTraits(){
        // FIXME: this could be turned into a smart select with autocomplete
        // or we need to get all pages
        try {
            let returnedItems: any = await this.traitManager.getAll(100, 0, "name", true)
            this.traits.length = 0
            this.defaultTraits.length = 0

            this.traits = returnedItems.content
            let defaultTraitRegex: RegExp = new RegExp(/^Id|Deleted|DeletedTime|UpdatedTime|StructureId$/)
            let index: number = 0
            this.traits = this.traits.filter((trait: Trait) => {
                if(defaultTraitRegex.test(trait.name)){
                    let fieldName = trait.name.charAt(0).toLowerCase() + trait.name.slice(1)
                    this.defaultTraits.push(new TraitHolder(index, fieldName, trait))
                    index++
                    return false
                }else{
                    return true
                }
            })
            this.editedItem = new StructureHolder(new Structure("", "", "",0,false,0, "", false,0,new Map<string, Trait>(),new Map<string, string>(),0),this.defaultTraits)
        }catch (error: any){
            console.log(error.stack)
            this.serverErrors = error.message
        }
    }

    public async getAll() {
        try {
            this.loading = true
            let returnedItems: any = await this.structureManager.getAll(this.options.itemsPerPage, this.options.page-1, this.options.sortBy[0], this.options.sortDesc[0])
            this.loading = false
            this.options.totalItems = returnedItems.totalElements
            this.items.length = 0 // reset the list
            this.items = returnedItems.content

            if(!this.finishedInitialLoad){
                setTimeout(() => {
                    this.finishedInitialLoad = true
                }, 500)
            }
        }catch (error: any){
            this.loading = false
            console.log(error.stack)
            this.serverErrors = error.message

            if(!this.finishedInitialLoad){
                setTimeout(() => {
                    this.finishedInitialLoad = true
                }, 500)
            }
        }
    }

    public async addNewTrait(name: string, trait: Trait){
        this.traitFieldNameErrorMessage = ""
        let proceed: boolean = true
        if(this.editedItem.structure.published){
            proceed = false
            // alert and ensure we are good to add new trait
            if(confirm(`Are you sure you want to create a new field '${name}' using Trait '${trait.name}' on an already published Structure? You will not be able to delete or modify new field.`)) {
                proceed = true
            }
        }

        if(proceed){
            let alreadyHasTraitName = this.editedItem.traits.findIndex((value) => {
                return value.fieldName === name
            })
            if(alreadyHasTraitName !== -1){
                this.traitFieldNameErrorMessage = "Structure already has a field with provided name, please change Trait Field Name to be unique."
            }else{
                if(this.editedItem.structure.published){
                    try{
                        await this.structureManager.addTraitToStructure(this.editedItem.structure.id, name, trait)
                        // happened successfully.. add to local object.
                        this.editedItem.traits.unshift(new TraitHolder(this.editedItem.traits.length+1,name, Object.assign({}, trait)))
                        this.resetEditedItem()
                    }catch (error: any){
                        console.log(error.stack)
                        this.serverErrors = error.message
                    }
                }else{
                    this.editedItem.traits.unshift(new TraitHolder(this.editedItem.traits.length+1,name, Object.assign({}, trait)))
                    this.resetEditedItem()
                }
            }
        }

    }

    private resetEditedItem(){
        this.newTraitName = ""
        this.newTraitDescribe = ""
        this.newTrait = new Trait("","","","","",0,0,true,true,true,true)
        this.dummyTrait = new Trait("","","","","",0,0,true,true,true,true)
        this.traitFieldNameErrorMessage = ""
        let ref: any = this.$refs["traitCreation"] as any
        ref.$refs.input.focus()
    }

    public async publish(item: StructureHolder){
        if(confirm('Are you sure you want to Publish this Structure?')) {
            try {
                this.publishingId = item.structure.id
                await this.structureManager.publish(item.structure.id)
                await this.getAllTraits()
                await this.getAll()// get updated list
                this.publishingId = ""
            }catch (error: any){
                this.publishingId = ""
                console.log(error.stack)
                this.serverErrors = error.message
            }
        }
    }

    public toStructureItemsPage(structureHolder: StructureHolder){
      this.$router.push({ path: `/structure-items/${structureHolder.structure.id}` })
    }

    public async editItem(item: StructureHolder) {
        this.editedIndex = this.items.indexOf(item)
        try {
            this.editedItem = await this.structureManager.getStructureById(item.structure.id)
            for(let entry of this.editedItem.traits){
                if(entry.fieldName === 'id'){
                    this.requireIdForEditedItem = entry.fieldTrait.required
                    break
                }
            }
            for(let namespace of this.namespaces){
                if(this.editedItem.structure.namespace === namespace.name){
                    this.selectedNamespace = namespace
                    break
                }
            }
            let meta: any = this.editedItem.structure.metadata
            this.pathToIcon = meta.pathToIcon // this is okay.. being javascript an all. :)
            this.dialog = true
        }catch(error: any){
            console.log(error.stack)
            this.serverErrors = error.message
        }
    }

    public async deleteItem(item: StructureHolder) {
        const index = this.items.indexOf(item)
        if(confirm('Are you sure you want to Delete this Structure?')) {
            try {
                await this.structureManager.delete(item.structure.id)
                this.items.splice(index, 1)
                this.options.totalItems--
                if((this.options.totalItems/this.options.itemsPerPage) < this.options.page && this.options.page > 1){
                    this.options.page--
                    await this.getAllTraits()
                    await this.getAll()
                }
            }catch(error: any){
                console.log(error.stack)
                this.serverErrors = error.message
            }
        }
    }

    public async onMoveCallback(evt: any) {
        // this is only needed if we are already published - since we save everything before publish.
        if (evt.oldIndex !== -1 && evt.newIndex !== -1 && this.editedItem.structure.published) {
            let movingItem: TraitHolder = this.editedItem.traits[evt.newIndex]

            try {
                if ((this.editedItem.traits.length - 1) === evt.newIndex) {
                    // if we are moving to the end we use insertAfter
                    let movingAfterItem: TraitHolder = this.editedItem.traits[this.editedItem.traits.length - 2]
                    await this.structureManager.insertTraitAfterAnotherForStructure(this.editedItem.structure.id, movingItem.fieldName, movingAfterItem.fieldName)
                } else {
                    let movingBeforeItem: TraitHolder = this.editedItem.traits[evt.newIndex + 1]
                    await this.structureManager.insertTraitBeforeAnotherForStructure(this.editedItem.structure.id, movingItem.fieldName, movingBeforeItem.fieldName)
                }
            } catch (error: any) {
                console.log(error.stack)
                this.serverErrors = error.message
            }
        }
    }

    public async save() {
        this.nameErrorMessage = IndexNameHelper.checkNameAndNamespace(this.editedItem.structure.name, "Name")
        this.namespaceErrorMessage = IndexNameHelper.checkNameAndNamespace(this.selectedNamespace?.name, "Namespace")
        this.serverErrors = IndexNameHelper.checkNameAndNamespace(this.editedItem.structure.namespace+this.editedItem.structure.name, "Index Name, namespace+name,")

        if(this.nameErrorMessage.length === 0 && this.namespaceErrorMessage.length === 0 && this.serverErrors.length === 0){
            // NOTE: save once published only saves the description
            let list: TraitHolder[] = []
            let order: number = 0
            for(let entry of this.editedItem.traits){
                entry.order = order
                list.push(entry)
                order++
            }
            this.editedItem.traits = list

            if(!this.editedItem.structure.published && this.editedItem.structure.namespace !== this.selectedNamespace.name){
                this.editedItem.structure.namespace = this.selectedNamespace.name
            }

            this.editedItem.structure.metadata = Object.assign(this.editedItem.structure.metadata, {pathToIcon: this.pathToIcon})

            try {
                let item: StructureHolder = await this.structureManager.save(this.editedItem)
                if (this.editedIndex > -1) {
                    Object.assign(this.items[this.editedIndex], item)
                } else {
                    this.items.push(item)
                    this.options.totalItems++
                }
                this.close()
            }catch(error: any){
                console.log(error.stack)
                this.serverErrors = error.message
            }
        }

    }

    public close () {
        if(this.editedItem.structure.published && !this.structureChanged){
            // possible trait reorder.. just update item
            Object.assign(this.items[this.editedIndex], this.editedItem)
        }
        this.dialog = false
        this.structureChanged = false
        this.requireIdForEditedItem = false
        setTimeout(() => {
            this.getAllTraits()
            this.editedIndex = -1
            this.selectedNamespace = new Namespace("", "", 0)
            this.pathToIcon = ""
            this.serverErrors = ""
            this.nameErrorMessage = ""
            this.namespaceErrorMessage = ""
        }, 300)
    }

}
</script>

<style scoped>

.handle {
    cursor: grabbing;
    float: left;
}

</style>
