<template>
    <crud-entity-add-edit :crud-service-identifier="crudServiceIdentifier"
                          title="Role"
                          :identity="identity"
                          :entity.sync="role">

        <template #basic-info="{ entity }" >
            <v-text-field v-model="entity.description" label="Description"></v-text-field>
        </template>

        <template #additional-info="{ entity }">
            <crud-identifiable-association-dialog
                    :crud-service-identifier="accessPolicyCrudServiceIdentifier"
                    :associated-identifiables.sync="entity.accessPolicies"
                    title="Access Policies">
            </crud-identifiable-association-dialog>
        </template>
    </crud-entity-add-edit>
</template>

<script lang="ts">
import { Component, Prop, Vue } from 'vue-property-decorator'
import { Role } from '@/frontends/iam/models/Role'
import { ServiceIdentifierConstants } from '@/frontends/iam/Constants'
import CrudEntityAddEdit from '@/frontends/continuum/components/CrudEntityAddEdit.vue'
import CrudIdentifiableAssociationDialog from '@/frontends/continuum/components/CrudIdentifiableAssociationDialog.vue'

// noinspection TypeScriptValidateTypes
@Component({
    components: { CrudEntityAddEdit, CrudIdentifiableAssociationDialog }
})
export default class RoleAddEdit extends Vue {

    @Prop({type: String, required: false, default: null})
    public identity!: string | null

    /**
     * Data Vars
     */
    private crudServiceIdentifier: string = ServiceIdentifierConstants.ROLE_SERVICE
    private accessPolicyCrudServiceIdentifier: string = ServiceIdentifierConstants.ACCESS_POLICY_SERVICE
    private role: Role = new Role('')

    constructor() {
        super()
    }

}
</script>

<style scoped>

</style>
