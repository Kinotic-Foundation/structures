<template>
    <crud-entity-add-edit :crud-service-identifier='crudServiceIdentifier'
                          title='User'
                          :identity='identity'
                          identity-label='Email*'
                          :entity.sync='user'
                          @before-save='onBeforeSave'>

        <template #basic-info='{ entity }'>
            <v-row dense>
                <v-col md='6' sm='12'>
                    <v-text-field v-model='entity.metadata.firstName' label='First Name'></v-text-field>
                </v-col>
                <v-col md='6' sm='12'>
                    <v-text-field v-model='entity.metadata.lastName' label='Last Name'></v-text-field>
                </v-col>
                <v-col v-if='!changePassword' sm='12'>
                    <v-btn @click='changePassword = true'
                           text color='primary'>
                        Change Password
                    </v-btn>
                </v-col>
                <v-col v-if='changePassword' md='6' sm='12'>
                    <v-text-field v-model='password'
                                  label='Password*'
                                  type='password'
                                  :rules='passwordRules'>
                    </v-text-field>
                </v-col>
                <v-col v-if='changePassword' md='6' sm='12'>
                    <v-text-field v-model='passwordConfirm'
                                  label='Confirm Password*'
                                  type='password'
                                  :rules='confirmPassRules'>
                    </v-text-field>
                </v-col>
            </v-row>
        </template>

        <template #additional-info='{ entity }'>
            <crud-identifiable-association-dialog
                    :crud-service-identifier='roleCrudServiceIdentifier'
                    :associated-identifiables.sync='entity.roles'
                    title='Roles'>
            </crud-identifiable-association-dialog>
        </template>
    </crud-entity-add-edit>
</template>

<script lang='ts'>
import { Component, Prop, Vue } from 'vue-property-decorator'
import { ServiceIdentifierConstants } from '@/frontends/iam/Constants'
import CrudEntityAddEdit from '@/frontends/continuum/components/CrudEntityAddEdit.vue'
import CrudIdentifiableAssociationDialog from '@/frontends/continuum/components/CrudIdentifiableAssociationDialog.vue'
import { User } from '@/frontends/iam/models/User'
import bcrypt from 'bcryptjs'
import { PasswordAuthenticator } from '@/frontends/iam/models/PasswordAuthenticator'

// Function that takes an input value as an argument and return either true / false or a string with an error message
type RuleValidator = (value: any) => string | boolean

// noinspection TypeScriptValidateTypes
@Component({
    components: { CrudEntityAddEdit, CrudIdentifiableAssociationDialog }
})
export default class UserAddEdit extends Vue {

    @Prop({type: String, required: false, default: null})
    public identity!: string | null

    /**
     * Data Vars
     */
    private crudServiceIdentifier: string = ServiceIdentifierConstants.USER_SERVICE
    private roleCrudServiceIdentifier: string = ServiceIdentifierConstants.ROLE_SERVICE
    private user: User = new User('')
    private password: string = ''
    private passwordConfirm: string = ''
    private changePassword: boolean = false
    private passwordRules: RuleValidator[] = [ (v) => !!v || 'Password required']
    private confirmPassRules: RuleValidator[] = [
                                                  (v) => !!v || 'Confirm password required',
                                                  (v) => v === this.password || 'Passwords do not match'
                                                ]

    constructor() {
        super()
    }

    public mounted() {
        // if identity is null this is a new user else we are editing in that case we do not edit pass by default
        this.changePassword = this.identity == null
    }

    private onBeforeSave(user: User) {
        if (this.changePassword) {
            const salt = bcrypt.genSaltSync(12)
            const hash = bcrypt.hashSync(this.password, salt)
            user.authenticators = [ new PasswordAuthenticator(user.identity, hash) ]
        } else {
            // The backend will automatically use the saved authenticators if we return null for authenticators.
            // Any value such as an empty array or new authenticators will be used if provided
            user.authenticators = null
        }
    }


}
</script>

<style scoped>

</style>
