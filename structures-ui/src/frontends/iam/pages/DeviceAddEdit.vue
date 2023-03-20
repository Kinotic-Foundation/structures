<template>
    <crud-entity-add-edit :crud-service-identifier="crudServiceIdentifier"
                          title="Device"
                          :identity="identity"
                          identity-label="MAC*"
                          :entity.sync="iamParticipant"
                          @after-load="onAfterLoad"
                          @before-save="onBeforeSave">

        <template #basic-info="{ entity }" >
            <v-text-field v-model="entity.metadata.description" label="Description"></v-text-field>
        </template>

        <template #additional-info="{ entity }">

            <v-card flat>
                <v-card-title>
                    <v-subheader class="pa-0">Legacy Certificates</v-subheader>
                    <v-spacer></v-spacer>
                </v-card-title>

                <v-simple-table fixed-header class="elevation-1" >
                    <template v-slot:default>
                        <thead v-if="legacySharedSecretAuthenticators.length > 0">
                        <tr>
                            <th class="text-left">Access Key</th>
                            <th class="text-right">Actions</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr v-for="(item, index) in legacySharedSecretAuthenticators"
                            :key="index">
                            <td>
                                {{ item.accessKey }}
                            </td>
                            <td class="text-end">
                                <v-icon title="Delete"
                                        small
                                        @click="deleteLegacySharedSecretAuthenticator(index)">
                                    {{deleteIcon}}
                                </v-icon>
                            </td>
                        </tr>
                        <tr v-if="legacySharedSecretAuthenticators.length === 0">
                            <td style="text-align:center; width: 100%; opacity:0.8">No Shared Secrets</td>
                        </tr>
                        </tbody>
                    </template>
                </v-simple-table>
            </v-card>

            <br>

            <crud-identifiable-association-dialog
                    :crud-service-identifier="roleCrudServiceIdentifier"
                    :associated-identifiables.sync="entity.roles"
                    title="Roles">
            </crud-identifiable-association-dialog>
        </template>

    </crud-entity-add-edit>
</template>

<script lang="ts">
import { Component, Prop, Vue } from 'vue-property-decorator'
import { ServiceIdentifierConstants } from '@/frontends/iam/Constants'
import CrudEntityAddEdit from '@/frontends/continuum/components/CrudEntityAddEdit.vue'
import CrudIdentifiableAssociationDialog from '@/frontends/continuum/components/CrudIdentifiableAssociationDialog.vue'
import { Device } from '@/frontends/iam/models/Device'
import { Authenticator } from '@/frontends/iam/models/Authenticator'
import { mdiDelete } from '@mdi/js'

// noinspection TypeScriptValidateTypes
@Component({
    components: { CrudIdentifiableAssociationDialog, CrudEntityAddEdit }
})
export default class DeviceAddEdit extends Vue {

    @Prop({type: String, required: false, default: null})
    public identity!: string | null

    /**
     * Icons
     */
    private deleteIcon: string = mdiDelete

    /**
     * Data Vars
     */
    private crudServiceIdentifier: string = ServiceIdentifierConstants.DEVICE_SERVICE
    private roleCrudServiceIdentifier: string = ServiceIdentifierConstants.ROLE_SERVICE
    private iamParticipant: Device = new Device('')
    private changedAuthenticators: boolean = false
    private legacySharedSecretAuthenticators: Authenticator[] = []

    constructor() {
        super()
    }

    public deleteLegacySharedSecretAuthenticator(index: number) {
        this.legacySharedSecretAuthenticators.splice(index, 1)
    }

    private onAfterLoad(device: Device) {
        if (device.authenticators != null) {
            for (const authenticator of device.authenticators) {
                if (authenticator.type === 'legacy') {
                    this.legacySharedSecretAuthenticators.push(authenticator)
                }
            }
        }
    }

    private onBeforeSave(device: Device) {
        if (!this.changedAuthenticators) {
            // The backend will automatically used the saved authenticators if we return null for authenticators.
            // Any value such as an empty array or new authenticators will be used if provided
            device.authenticators = null
        }
    }

}
</script>

<style scoped>
    .subTitle {
        font-size: 16px;
        color: rgba(0, 0, 0, 0.6);
    }
</style>
