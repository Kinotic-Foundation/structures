<template>
    <div>
        <v-card flat>
            <v-card-title >
                <v-subheader class="pa-0">{{title}}</v-subheader>
                <v-spacer></v-spacer>
                <v-btn color="primary"
                       fab
                       small
                       @click="openAddAssociatedIdentifiablesDialog">
                    <v-icon dark>{{addIcon}}</v-icon>
                </v-btn>
            </v-card-title>

            <v-simple-table fixed-header class="elevation-1" >
                <template v-slot:default>
                    <thead v-if="typeof(syncedAssociatedIdentifiables) !== 'undefined' && syncedAssociatedIdentifiables.length > 0">
                    <tr>
                        <th class="text-left">Name</th>
                        <th class="text-left">Description</th>
                        <th class="text-right">Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr v-for="(item, index) in syncedAssociatedIdentifiables"
                        :key="index">
                        <td>
                            {{ item.id }}
                        </td>
                        <td>
                            {{ item.description }}
                        </td>
                        <td class="text-end">
                            <v-icon title="Delete"
                                    small
                                    @click="deleteAssociatedIdentifiables(index)">
                                {{deleteIcon}}
                            </v-icon>
                        </td>
                    </tr>
                    <tr v-if="syncedAssociatedIdentifiables.length === 0">
                        <td style="text-align:center; width: 100%; opacity:0.8">No {{title}}</td>
                    </tr>
                    </tbody>
                </template>
            </v-simple-table>
        </v-card>

        <v-dialog v-model="dialog" scrollable persistent transition="fade-transition" >
            <v-card flat>
                <v-card-title>
                    <span class="title">{{title}} to Add</span>
                </v-card-title>

                <v-simple-table v-resize.quiet="onResize"
                                fixed-header
                                dense
                                :height="dialogComputedHeight" >
                    <template v-slot:default>
                        <thead v-if="availableAssociatedIdentifiables.length > 0">
                        <tr>
                            <th></th>
                            <th class="text-left">Name</th>
                            <th class="text-left">Description</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr v-for="(item, index) in availableAssociatedIdentifiables"
                            :key="index">
                            <td style="width: 48px">
                                <v-checkbox dense v-model="selectedAssociatedIdentifiables" :value="item"></v-checkbox>
                            </td>
                            <td>
                                {{ item.id }}
                            </td>
                            <td>
                                {{ item.description }}
                            </td>
                        </tr>
                        <tr v-if="availableAssociatedIdentifiables.length === 0">
                            <td style="text-align:center; width: 100%; opacity:0.8">No Data</td>
                        </tr>
                        </tbody>
                    </template>
                </v-simple-table>

                <v-divider></v-divider>
                <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn color="blue darken-1" text @click="cancelAdding">Close</v-btn>
                    <v-btn color="blue darken-1" text @click="doneAdding">Done</v-btn>
                </v-card-actions>
            </v-card>
            <v-overlay :value="loading" absolute>
                <v-progress-circular indeterminate size="64"></v-progress-circular>
            </v-overlay>
        </v-dialog>
    </div>
</template>

<script lang="ts">
import { Component, Prop, Vue, PropSync } from 'vue-property-decorator'
import {
  Continuum,
  Direction,
  ICrudServiceProxy,
  ICrudServiceProxyFactory,
  Identifiable,
  Order,
  Page,
  Pageable
} from '@kinotic/continuum-client'
import { mdiDelete, mdiPlus } from '@mdi/js'

@Component({
    components: { }
})
export default class CrudIdentifiableAssociationDialog extends Vue {

    /**
     * The crud service identifier to look up {@link Identifiable}'s for association
     */
    @Prop({type: String, required: true})
    public crudServiceIdentifier!: string

    @Prop({type: String, required: true})
    public title!: string

    @PropSync('associatedIdentifiables', {type: Array, required: false, default: { id: '' }})
    public syncedAssociatedIdentifiables!: Array<Identifiable<string>>

    /**
     * Services
     */
    private crudServiceProxy!: ICrudServiceProxy<any>

    /**
     * Icons
     */
    private addIcon: string = mdiPlus
    private deleteIcon: string = mdiDelete

    /**
     * Data Vars
     */
    private dialog: boolean = false
    private dialogComputedHeight: number = (window.innerHeight - 225)
    private loading: boolean = false
    private selectedAssociatedIdentifiables: Array<Identifiable<string>> = new Array<Identifiable<string>>()
    private availableAssociatedIdentifiables: Array<Identifiable<string>> = new Array<Identifiable<string>>()

    constructor() {
        super()
    }

    public mounted() {
        this.crudServiceProxy = Continuum.crudServiceProxy(this.crudServiceIdentifier)
    }

    public async openAddAssociatedIdentifiablesDialog() {
        this.hideAlert()
        this.availableAssociatedIdentifiables.splice(0, this.availableAssociatedIdentifiables.length)
        this.selectedAssociatedIdentifiables.splice(0, this.selectedAssociatedIdentifiables.length)
        this.loading = true
        this.dialog = true

        const ids: string[] = []
        for (const policy of this.syncedAssociatedIdentifiables) {
            if(policy.id !== null){
                ids.push(policy.id)
            }
        }

        try {
            const pageable = Pageable.create(0, 50, { orders: [new Order('id', Direction.ASC)] })

            const page: Page<Identifiable<string>> = await this.crudServiceProxy.findByIdNotIn(ids, pageable)
            this.availableAssociatedIdentifiables = page.content ?? []

        } catch (error: any) {
            this.displayAlert(error.message)
        }
        this.loading = false
    }

    public cancelAdding() {
        this.dialog = false
        this.availableAssociatedIdentifiables.splice(0, this.availableAssociatedIdentifiables.length)
    }

    public doneAdding() {
        this.syncedAssociatedIdentifiables.push(...this.selectedAssociatedIdentifiables)
        this.dialog = false
    }

    public deleteAssociatedIdentifiables(index: number) {
        this.syncedAssociatedIdentifiables.splice(index, 1)
    }

    public onResize() {
        this.dialogComputedHeight = (window.innerHeight - 225)
    }

    private hideAlert() {
        (this.$notify as any as { close: (value: string) => void }).close('crudIdentifiableAssociationDialogAlert')
    }

    private displayAlert(text: string) {
        this.$notify({ group: 'alert', type: 'crudIdentifiableAssociationDialogAlert', text })
    }

}
</script>

<style scoped>

</style>
