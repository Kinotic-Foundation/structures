<template>
    <crud-entity-add-edit :crud-service-identifier="crudServiceIdentifier"
                          title="Access Policy"
                          :identity="identity"
                          :entity.sync="accessPolicy">

        <template #basic-info="{ entity }" >
            <v-text-field v-model="entity.description" label="Description"></v-text-field>
        </template>

        <template #additional-info="{ entity }">
            <v-card flat>
                <v-card-title>
                    Send Access Patterns
                    <v-spacer></v-spacer>
                    <v-text-field
                            v-model="sendAccessPatternToAdd"
                            label="New Send Pattern"
                            hint="Press Enter to Add"
                            :append-icon="addIcon"
                            style="max-width: 800px"
                            @keydown.enter.capture="addSendAccessPattern"
                            @keydown.space.prevent>
                    </v-text-field>
                </v-card-title>



                <v-simple-table class="elevation-1" >
                    <template v-slot:default>
                        <draggable :list="entity.allowedSendPatterns"
                                   handle=".sortHandleSend"
                                   tag="tbody" >
                            <tr v-for="(item, index) in entity.allowedSendPatterns"
                                :key="index">
                                <td class="px-1" style="width: 48px">
                                    <v-btn style="cursor: move" icon class="sortHandleSend"><v-icon>{{dragIcon}}</v-icon></v-btn>
                                </td>
                                <td>
                                    <v-edit-dialog :return-value.sync="item.pattern">
                                        {{ item.pattern }}
                                        <template v-slot:input>
                                            <v-text-field
                                                    v-model="item.pattern"
                                                    label="Edit">
                                            </v-text-field>
                                        </template>
                                    </v-edit-dialog>
                                </td>
                                <td class="text-end">
                                    <v-icon title="Delete"
                                            small
                                            @click="deleteSendAccessPattern(index)">
                                        {{deleteIcon}}
                                    </v-icon>
                                </td>
                            </tr>
                            <tr v-if="entity.allowedSendPatterns === null || entity.allowedSendPatterns.length === 0">
                                <td style="text-align:center; width: 100%; opacity:0.8">No Data</td>
                            </tr>
                        </draggable>
                    </template>
                </v-simple-table>
            </v-card>

            <v-card flat>
                <v-card-title class="accessPatternsTitle">
                    Subscription Access Patterns
                    <v-spacer></v-spacer>
                    <v-text-field
                            v-model="subscriptionAccessPatternToAdd"
                            label="New Subscription Pattern"
                            hint="Press Enter to Add"
                            :append-icon="addIcon"
                            style="max-width: 800px"
                            @keyup.enter.capture="addSubscriptionAccessPattern"
                            @keydown.space.prevent>
                    </v-text-field>
                </v-card-title>



                <v-simple-table class="elevation-1" >
                    <template v-slot:default>
                        <draggable :list="entity.allowedSubscriptionPatterns"
                                   handle=".sortHandleSub"
                                   tag="tbody" >
                            <tr v-for="(item, index) in entity.allowedSubscriptionPatterns"
                                :key="index">
                                <td class="px-1" style="width: 48px">
                                    <v-btn style="cursor: move" icon class="sortHandleSub"><v-icon>{{dragIcon}}</v-icon></v-btn>
                                </td>
                                <td>
                                    <v-edit-dialog :return-value.sync="item.pattern">
                                        {{ item.pattern }}
                                        <template v-slot:input>
                                            <v-text-field
                                                    v-model="item.pattern"
                                                    label="Edit">
                                            </v-text-field>
                                        </template>
                                    </v-edit-dialog>
                                </td>
                                <td class="text-end">
                                    <v-icon title="Delete"
                                            small
                                            @click="deleteSubscriptionAccessPattern(index)">
                                        {{deleteIcon}}
                                    </v-icon>
                                </td>
                            </tr>
                            <tr v-if="entity.allowedSubscriptionPatterns === null || entity.allowedSubscriptionPatterns.length === 0">
                                <td style="text-align:center; width: 100%; opacity:0.8">No Data</td>
                            </tr>
                        </draggable>
                    </template>
                </v-simple-table>
            </v-card>
        </template>

    </crud-entity-add-edit>
</template>

<script lang="ts">
import { Component, Prop, Vue } from 'vue-property-decorator'
import { AccessPolicy } from '@/frontends/iam/models/AccessPolicy'
import { AccessPattern } from '@/frontends/iam/models/AccessPattern'
import { ServiceIdentifierConstants } from '@/frontends/iam/Constants'
import { mdiPlus, mdiDelete, mdiDragHorizontalVariant } from '@mdi/js'
import draggable from 'vuedraggable'
import CrudEntityAddEdit from '@/frontends/continuum/components/CrudEntityAddEdit.vue'

// noinspection TypeScriptValidateTypes
@Component({
    components: { draggable, CrudEntityAddEdit }
})
export default class AccessPolicyAddEdit extends Vue {

    @Prop({type: String, required: false, default: null})
    public identity!: string | null

    /**
     * Icons
     */
    private addIcon: string = mdiPlus
    private deleteIcon: string = mdiDelete
    private dragIcon: string = mdiDragHorizontalVariant

    /**
     * Data Vars
     */
    private crudServiceIdentifier: string = ServiceIdentifierConstants.ACCESS_POLICY_SERVICE
    private accessPolicy: AccessPolicy = new AccessPolicy('')
    private sendAccessPatternToAdd: string = ''
    private subscriptionAccessPatternToAdd: string = ''

    constructor() {
        super()
    }

    public addSendAccessPattern() {
        if (typeof (this.sendAccessPatternToAdd) !== 'undefined' && this.sendAccessPatternToAdd.length > 0 ) {
            this.accessPolicy.allowedSendPatterns.push(new AccessPattern(this.sendAccessPatternToAdd))
            this.sendAccessPatternToAdd = ''
        }
    }

    public deleteSendAccessPattern(index: number) {
        this.accessPolicy.allowedSendPatterns.splice(index, 1)
    }

    public addSubscriptionAccessPattern() {
        if (typeof (this.subscriptionAccessPatternToAdd) !== 'undefined' && this.subscriptionAccessPatternToAdd.length > 0 ) {
            this.accessPolicy.allowedSubscriptionPatterns.push(new AccessPattern(this.subscriptionAccessPatternToAdd))
            this.subscriptionAccessPatternToAdd = ''
        }
    }

    public deleteSubscriptionAccessPattern(index: number) {
        this.accessPolicy.allowedSubscriptionPatterns.splice(index, 1)
    }

}
</script>

<style scoped>

</style>
