<template>
    <v-container fluid fill-height>
        <v-layout justify-center>
            <v-flex text-xs-center mr-3>
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
                    @page-count="pageCount = $event"
                    :fixed-header=true
                    :footer-props="{
                          showFirstLastPage: true,
                          firstIcon: icons.arrowLeft,
                          lastIcon: icons.arrowRight,
                          prevIcon: icons.minus,
                          nextIcon: icons.plus,
                          'items-per-page-options':options.rowsPerPageItems
                        }">

                    <template v-slot:body="{ items }">
                        <tbody>
                        <tr v-if="items.length > 0" v-for="item in items" :key="item.id">
                            <td v-for="(key, index) in keys">
                                {{ types.get(key).type === "date" ? formatDate(item[key]) : item[key] }}
                            </td>
                            <td>
                                {{ formatDate(item.createdTime) }}
                            </td>
                            <td>
                                {{ formatDate(item.updatedTime) }}
                            </td>
                            <td>
                                <v-icon medium
                                        class="mr-2"
                                        @click="editItem(item)"
                                        title="Edit">
                                    {{ icons.edit }}
                                </v-icon>
                                <v-icon medium
                                        class="mr-2"
                                        @click="deleteItem(item)"
                                        title="Delete">
                                    {{ icons.delete }}
                                </v-icon>
                            </td>
                        </tr>
                        <tr v-if="items.length === 0">
                            <td class="py-12" style="margin: 0 auto; text-align: center" :colspan="headers.length">
                                <v-btn color="primary" @click="checkForData" v-show="!loading">No Data - Push To Search
                                    Again
                                </v-btn>
                            </td>
                        </tr>
                        </tbody>
                    </template>

                    <template v-slot:top>
                        <v-toolbar flat>
                            <v-toolbar-title>{{ structureName }}</v-toolbar-title>
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
                            <v-text-field autofocus
                                          clearable
                                          dense
                                          hide-details
                                          single-line
                                          v-model="search"
                                          label="Search"
                                          @click:clear="search = ''">
                            </v-text-field>
                            <v-spacer></v-spacer>
                            <v-dialog v-model="dialog" fullscreen hide-overlay persistent
                                      transition="dialog-bottom-transition">
                                <template v-slot:activator="{ on }">
                                    <v-btn color="primary" dark class="mb-2" v-on="on">New {{ structureName }}</v-btn>
                                </template>
                                <v-card>

                                    <v-toolbar>
                                        <v-toolbar-title>{{ formTitle }}</v-toolbar-title>
                                        <v-spacer></v-spacer>
                                        <v-toolbar-items>
                                            <v-btn icon @click="close">
                                                <v-icon>{{ icons.close }}</v-icon>
                                            </v-btn>
                                        </v-toolbar-items>
                                    </v-toolbar>

                                    <v-app-bar dark fixed bottom color="primary">
                                        <v-spacer></v-spacer>
                                        <v-spacer></v-spacer>
                                        <v-btn dark text @click="close">Cancel</v-btn>
                                        <v-btn dark text @click="save" :disabled="!valid">Save</v-btn>
                                    </v-app-bar>

                                    <v-list two-line rounded>
                                        <v-subheader>
                                            {{ structureName }} Information
                                            <v-spacer></v-spacer>
                                            {{
                                                editedIndex === -1 ? "" : "Created " + formatDate(editedItem.createdTime)
                                            }}<br/>{{
                                                editedIndex === -1 ? "" : "Updated " + formatDate(editedItem.updatedTime)
                                            }}
                                        </v-subheader>
                                        <v-alert dense
                                                 outlined
                                                 type="error"
                                                 v-show="serverErrors.length > 0">
                                            {{ serverErrors }}
                                        </v-alert>

                                        <v-form ref="form"
                                                v-model="valid"
                                                lazy-validation>
                                            <v-list-item v-for="(key, index) in keys">
                                                <v-list-item-content v-if="types.get(key).type === 'string'">
                                                    <v-textarea clearable
                                                                :clear-icon="icons.closeCircle"
                                                                :ref="key"
                                                                :key="editedIndex+key"
                                                                :name="editedIndex+key"
                                                                :autofocus="Number(index) === 0"
                                                                :label="headers[index].text"
                                                                :disabled="systemManaged.indexOf(key) !== -1"
                                                                :required="required.indexOf(key) === -1"
                                                                :rules="[checkData(key, editedItem[key])]"
                                                                :maxlength="typeof(types.get(key).maxLength) === 'undefined' ? -1 : types.get(key).maxLength"
                                                                :minlength="typeof(types.get(key).minLength) === 'undefined' ? -1 : types.get(key).minLength"
                                                                auto-grow
                                                                rows="1"
                                                                v-model="editedItem[key]"
                                                                @keydown.space="(event) => { if(key === 'id') { event.preventDefault() } } ">
                                                    </v-textarea>
                                                </v-list-item-content>
                                                <v-list-item-content v-else-if="types.get(key).type === 'boolean'">
                                                    <v-switch v-model="editedItem[key]"
                                                              class="ma-2"
                                                              :ref="key"
                                                              :key="editedIndex+key"
                                                              :name="editedIndex+key"
                                                              :rules="[checkData(key, editedItem[key])]"
                                                              :label="headers[index].text"
                                                              :autofocus="Number(index) === 0"
                                                              :disabled="systemManaged.indexOf(key) !== -1"
                                                              :required="required.indexOf(key) === -1">
                                                    </v-switch>
                                                </v-list-item-content>
                                                <v-list-item-content v-else-if="types.get(key).type === 'number'">
                                                    <v-text-field
                                                        v-model="editedItem[key]"
                                                        class="mt-0 pt-0"
                                                        type="number"
                                                        :ref="key"
                                                        :key="editedIndex+key"
                                                        :name="editedIndex+key"
                                                        :rules="[checkData(key, editedItem[key])]"
                                                        :label="headers[index].text"
                                                        :autofocus="Number(index) === 0"
                                                        :disabled="systemManaged.indexOf(key) !== -1"
                                                        :required="required.indexOf(key) === -1"
                                                        :min="typeof(types.get(key).minimum) !== 'undefined' ? types.get(key).minimum : 0"
                                                        :max="typeof(types.get(key).maximum) !== 'undefined' ? types.get(key).maximum : 100">
                                                    </v-text-field>
                                                </v-list-item-content>
                                                <v-list-item-content v-else-if="types.get(key).type === 'date'">
                                                    <DateTimePicker width="35%"
                                                                    maxWidth="50%"
                                                                    :datetime="editedItem[key]"
                                                                    :key="editedIndex+key"
                                                                    :name="editedIndex+key"
                                                                    :label="headers[index].text"
                                                                    :fullWidth=true
                                                                    @input="(time) => editedItem[key] = time"
                                                                    :required="required.indexOf(key) === -1"
                                                                    :validator="checkData"
                                                                    :identity="key">
                                                    </DateTimePicker>
                                                </v-list-item-content>
                                                <v-list-item-content v-else-if="types.get(key).type === 'ref'">
                                                    <ReferenceSelect :model="editedItem[key]"
                                                                     :reference="types.get(key)"
                                                                     :label="headers[index].text"
                                                                     :ref="key"
                                                                     :key="editedIndex+key"
                                                                     :name="editedIndex+key"
                                                                     @input="(selected) => editedItem[key] = selected"
                                                                     :validator="checkData"
                                                                     :identity="key">
                                                    </ReferenceSelect>
                                                </v-list-item-content>
                                                <v-list-item-content v-else="">
                                                    Not implemented
                                                </v-list-item-content>
                                            </v-list-item>

                                        </v-form>

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
import {Component, Vue, Watch} from 'vue-property-decorator'
import {IItemManager, IStructureManager} from "@/frontends/structures-admin/services"
import DateTimePicker from '@/frontends/continuum/components/DateTimePicker.vue'
import ReferenceAutocomplete from '@/frontends/structures-admin/components/ReferenceAutocomplete.vue'
import ReferenceSelect from '@/frontends/structures-admin/components/ReferenceSelect.vue'
import {inject} from 'inversify-props'
import {
    mdiPlus,
    mdiPencil,
    mdiDelete,
    mdiClose,
    mdiCloseCircle,
    mdiMinusCircle,
    mdiPlusCircle,
    mdiArrowCollapseLeft,
    mdiArrowCollapseRight
} from '@mdi/js'

@Component({
    components: {DateTimePicker, ReferenceAutocomplete, ReferenceSelect},
    props: {
        structureId: {
            type: String
        }
    }
})
export default class Traits extends Vue {

    @inject()
    private itemManager!: IItemManager
    @inject()
    private structureManager!: IStructureManager

    private computedHeight: number = (window.innerHeight - 225)

    public items: any[] = []
    public loading: boolean = true
    public dialog: boolean = false
    public editedIndex: number = -1
    public editedItem: any = {}
    public finishedInitialLoad: boolean = false
    public structureJsonSchema: any = {}
    public structureJsonSchemaString: string = ""
    public structureName: string = ""
    public defaultSortBy: string = ""
    public valid: boolean = true
    public serverErrors: string = ""
    public defaultTraitRegex: RegExp = new RegExp(/^createdTime|updatedTime|deleted|deletedTime|structureId|structureName$/)

    public structureHasRefs: boolean = false

    public options: any = {
        mustSort: true,
        sortDesc: [true],
        page: 1,
        totalItems: 0,
        itemsPerPage: 10,
        rowsPerPageItems: [5, 10, 25, 50, 75, 100, -1]
    }

    private icons = {
        close: mdiClose,
        closeCircle: mdiCloseCircle,
        add: mdiPlus,
        edit: mdiPencil,
        delete: mdiDelete,
        minus: mdiMinusCircle,
        plus: mdiPlusCircle,
        arrowLeft: mdiArrowCollapseLeft,
        arrowRight: mdiArrowCollapseRight
    }

    // NOTE: Cannot Sort on Fields that are set up for Full Text Search.
    public headers: any = []
    public keys: string[] = []
    public types: Map<string, any> = new Map<string, any>()
    public systemManaged: string[] = []
    public required: string[] = []

    public search: string = ""
    public searchTimeoutHandle: number = -1
    public searchTimeoutInterval: number = 1500
    public searchTimeoutStartTime: number = 0

    constructor() {
        super()
    }

    // Lifecycle hook
    public mounted() {
        this.structureManager.getJsonSchema(this.$props.structureId).then((jsonSchema: string) => {
            this.structureJsonSchemaString = jsonSchema
            this.structureJsonSchema = JSON.parse(jsonSchema)
            this.systemManaged = this.structureJsonSchema.systemManaged
            this.required = this.structureJsonSchema.required
            this.structureName = this.structureJsonSchema.structure
            for (let key in this.structureJsonSchema.properties) {
                if (this.structureJsonSchema.properties.hasOwnProperty(key)
                    && !this.defaultTraitRegex.test(key)) {
                    let definition: any = this.structureJsonSchema.properties[key]
                    let fieldName = key.charAt(0).toUpperCase() + key.slice(1)
                    let sortable: boolean = true
                    // FIXME: how to ensure we don't try and sort on full text search fields?
                    if (typeof (definition.type) !== "undefined"
                        && definition.type === "ref") {
                        sortable = false
                        this.structureHasRefs = true
                    }else if(definition.description && definition.description === "no-sort"){
                        sortable = false
                    }
                    if (this.defaultSortBy.length === 0 && sortable) {
                        this.defaultSortBy = key
                        this.headers.push({text: fieldName, align: 'left', value: key, sortable: sortable})
                    } else {
                        this.headers.push({text: fieldName, value: key, sortable: sortable})
                    }
                    this.keys.push(key)
                    this.types.set(key, definition)
                }
            }
            this.headers.push({text: 'Created Time', value: 'createdTime', sortable: true})
            this.headers.push({text: 'Updated Time', value: 'updatedTime', sortable: true})
            this.headers.push({text: 'Actions', value: 'action', sortable: false})
            if (this.defaultSortBy.length === 0) {
                this.defaultSortBy = "createdTime"
            }
            this.options.sortBy = [this.defaultSortBy]
            this.checkForData()
        })
        .catch((error: any) => {
            console.log(error.stack)
            this.serverErrors = error.message
        })
    }

    public beforeDestroy() {
    }

    @Watch('options')
    public watchPagination(value: any, oldValue: any) {
        if (value.sortBy.length === 0) {
            value.sortBy = [this.defaultSortBy]
            value.sortDesc = [true]
        }
        this.options = value
        if (this.finishedInitialLoad) {
            this.getItemList()
        }
    }

    @Watch('dialog')
    public watchDialog(value: boolean, oldValue: boolean) {
        value || this.close()
    }

    @Watch('search')
    public watchSearch(value: string | null | undefined, oldValue: string | null | undefined) {
        if(!this.finishedInitialLoad){
            // could happen if we don't have any records in the db
            // we don't try to do anything, once a value is stored
            return
        }
        if (oldValue !== null && oldValue !== undefined && oldValue.length > 0 && (value === null || value === undefined || value.length === 0)) {
            // new value is cleared and we had an old value
            this.getItemList()
        } else if (value !== null && value !== undefined && value.length >= 3) {
            // check for just spaces, no need to query
            let spaceCheck: string = value.replace(/\s/g, "")
            if(spaceCheck.length === 0){
                return
            }
            this.serverErrors = ""
            // start to search when we have a couple characters.
            if (this.searchTimeoutStartTime === 0) {
                this.searchTimeoutStartTime = new Date().getTime()
            } else if ((new Date().getTime() - this.searchTimeoutStartTime) < this.searchTimeoutInterval) {
                clearTimeout(this.searchTimeoutHandle)
                this.searchTimeoutStartTime = new Date().getTime()
            }

            this.searchTimeoutHandle = setTimeout(() => {
                this.loading = true
                this.searchTimeoutStartTime = 0
                this.searchTimeoutHandle = -1
                this.itemManager.searchWithSort(this.$props.structureId, this.search.trim().endsWith(':') ? this.search.trim()+"*" : this.search.trim(), this.options.itemsPerPage, this.options.page - 1, this.options.sortBy[0], this.options.sortDesc[0])
                    .then((returnedItems: any) => {
                        this.handleItemLoad(returnedItems)
                    })
                    .catch((error: any) => {
                        this.loading = false
                        console.log(error.stack)
                        this.serverErrors = error.message
                    })
            }, this.searchTimeoutInterval)

        }
    }

    get formTitle() {
        return this.editedIndex === -1 ? `New ${this.structureName}` : `Edit ${this.structureName}`
    }

    public async checkForData(){
        this.loading = true

        // await this.itemManager.requestBulkUpdatesForStructure(this.$props.structureId)
        // for (let i: number = 0; i < 6000; i++){
        //     let obj: any = {}
        //     obj.name = `Device-${i}`
        //     obj.description = `This is a description for Device-${i}`
        //     await this.itemManager.pushItemForBulkUpdate(this.$props.structureId, obj)
        // }
        // await this.itemManager.flushAndCloseBulkUpdate(this.$props.structureId)

        let count: number = await this.itemManager.count(this.$props.structureId)
        if(count > 0){
            await this.getItemList()
        }else{
            this.loading = false
        }
    }

    public checkData(key: string, value: any) {
        if (this.types.has(key)) {
            // we are saving and performing validation.   we can now use the field data and the type to perform this validation.
            let messages: string[] = []
            let fieldName = key.charAt(0).toUpperCase() + key.slice(1)

            // check if its required first
            if (this.required.indexOf(key) !== -1 && this.systemManaged.indexOf(key) !== -1) {
                if (value === undefined) {
                    messages.push(fieldName + ' field is required. ')
                } else if (value === null) {
                    messages.push(fieldName + ' field is required. ')
                } else if (this.types.get(key).type === 'string' && value.trim().length === 0) {
                    messages.push(fieldName + ' field is required. ')
                }
            }

            if (this.types.get(key).type === 'string') {
                if (typeof (this.types.get(key).format) !== "undefined") {

                }
                if (typeof (this.types.get(key).pattern) !== "undefined") {
                    let regex: RegExp = new RegExp(this.types.get(key).pattern)
                    if (!regex.test(value)) {
                        messages.push(fieldName + ' is not in correct format. ')
                    }
                }
                if(key === 'id' && String(value).includes(' ')){
                    messages.push('id must not have spaces')
                }
            }

            if (this.types.get(key).type === 'number') {

            }

            if (this.types.get(key).type === 'date') {

            }

            if (messages.length > 0) {
                this.valid = false
                return messages.join(' ')
            }
        }
        return true
    }

    public formatDate(timeInMills: number) {
        let ret: string = ""
        if (timeInMills !== 0) {
            let [date, time] = new Date(Number(timeInMills)).toLocaleString('en-US', {hour12: false}).split(', ')
            ret = date + " " + time
        }
        return ret
    }

    public handleItemLoad(returnedItems: any) {
        this.loading = false
        this.options.totalItems = returnedItems.totalElements
        this.items.length = 0 // reset the list

        if (this.structureHasRefs) {
            returnedItems.content.forEach((item: any) => {
                this.itemManager.getItemById(this.$props.structureId, item.id).then((data) => {
                    this.items.push(data)
                })
                .catch((error: any) => {
                    console.log(error.stack)
                    this.serverErrors = error.message
                })
            })
        } else {
            this.items = returnedItems.content
        }
    }

    public getItemList() {

        this.itemManager.searchWithSort(this.$props.structureId, this.search.trim().length > 0 ? this.search.trim() : "*", this.options.itemsPerPage, this.options.page - 1, this.options.sortBy[0], this.options.sortDesc[0]).then((returnedItems: any) => {
            this.handleItemLoad(returnedItems)

            if (!this.finishedInitialLoad) {
                setTimeout(() => {
                    this.finishedInitialLoad = true
                }, 500)
            }

        })
        .catch((error: any) => {
            this.loading = false
            console.log(error.stack)
            this.serverErrors = error.message

            if (!this.finishedInitialLoad) {
                setTimeout(() => {
                    this.finishedInitialLoad = true
                }, 500)
            }

        })

    }

    public editItem(item: any) {
        this.editedIndex = this.items.indexOf(item)
        this.itemManager.getItemById(this.$props.structureId, item.id).then((fresh) => {
            this.editedItem = Object.assign({}, fresh)
            this.dialog = true
        })
    }

    public deleteItem(item: any) {
        const index = this.items.indexOf(item)
        if (confirm(`Are you sure you want to Delete this ${this.structureName} Item?`)) {
            this.itemManager.delete(this.$props.structureId, item.id).then((data) => {
                this.items.splice(index, 1)
                this.options.totalItems--
                if ((this.options.totalItems / this.options.itemsPerPage) < this.options.page && this.options.page > 1) {
                    this.options.page--
                    this.getItemList()
                }
            })
                .catch((error: any) => {
                    console.log(error.stack)
                    this.serverErrors = error.message
                })
        }
    }

    public save() {

        let fail: boolean = false

        this.keys.forEach(key => {
            if (typeof (this.checkData(key, this.editedItem[key])) !== "boolean" && !fail) {
                fail = true
            }
        })

        let form: any = this.$refs.form as any
        let inputsArray: any[] = form['inputs'] as []
        inputsArray.forEach(input => {
            let ret: boolean = input.validate(true)
            if (!ret && !fail) {
                fail = true
            }
        })

        if (fail) {
            this.valid = false
            this.$forceUpdate()
            return
        }


        this.itemManager.upsertItem(this.$props.structureId, this.editedItem).then((data) => {
            this.getItemList()
            this.close()
        })
        .catch((error: any) => {
            console.log(error.stack)
            this.serverErrors = error.message
        })
    }

    public close() {
        this.dialog = false
        this.valid = true
        this.editedItem = {}
        this.editedIndex = -1
        this.serverErrors = ""
    }
}
</script>

<style scoped>

</style>
