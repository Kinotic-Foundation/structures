<template>
    <v-app class="login" >
        <v-main>
            <v-container fluid fill-height>
                <v-layout align-center justify-center>
                    <v-flex xs12 sm8 md6 lg4>
                        <v-card class="elevation-1 pa-3">
                            <v-card-text>
                                <div class="layout column align-center">
                                    <v-icon x-large>{{authenticated ? lockOpen : lock}}</v-icon>
                                </div>
                                <p style="padding-top: 15px"
                                   class="text-h4 text-center">
                                    {{title}}
                                </p>
                                <p class="text-subtitle-2 text-center">
                                    {{message}}
                                </p>
                            </v-card-text>
                            <v-overlay :absolute="true"
                                       :value="loading">
                                <v-progress-circular
                                    indeterminate
                                    size="64"
                                ></v-progress-circular>
                            </v-overlay>
                        </v-card>
                    </v-flex>
                </v-layout>
            </v-container>
        </v-main>
    </v-app>
</template>

<script lang="ts">

import {Component, Prop, Vue} from 'vue-property-decorator'
import {IUserState} from '@/frontends/continuum'
import {StructuresStates} from '@/frontends/states'
import {ISessionUpgradeService, SESSION_UPGRADE_SERVICE} from '@/frontends/continuum/services/ISessionUpgradeService'
import {Continuum} from '@kinotic/continuum-client'
import { mdiLockOpen, mdiLock } from '@mdi/js';

@Component({
    components: { }
})
export default class CliSessionUpgrade extends Vue {

    @Prop({type: String, required: false, default: null})
    public id!: string | null
    private loading: boolean = false
    private authenticated: boolean = false
    private title: string = 'Authenticating'
    private message: string = 'Connecting CLI to Continuum...'
    private userState: IUserState = StructuresStates.getUserState()
    private sessionUpgradeService: ISessionUpgradeService = SESSION_UPGRADE_SERVICE

    private lockOpen = mdiLockOpen
    private lock = mdiLock

    public async mounted() {
        if(this.id){
            if(this.userState?.connectedInfo?.sessionId) {
                this.loading = true
                try {

                    const decodedId = decodeURIComponent(this.id)

                    await this.sessionUpgradeService.upgradeSession(decodedId, this.userState.connectedInfo.sessionId)

                    // we abandon connection so session can be used by CLI
                    // TODO: add true upgrade session logic and remove this
                    await Continuum.disconnect(true)

                }catch (e) {
                    this.setStatus('Error connecting CLI to Continuum: ' + e, false, false)
                } finally {
                    this.setStatus('You can close this tab and return to your command line.', true, false)
                }
            }else{
                this.setStatus('Error connecting CLI to Continuum: No session found.', false, false)
            }
        }else{
            this.setStatus('Error connecting CLI to Continuum: No upgrade id provided.', false, false)
        }
    }

    private setStatus(message: string, authenticated: boolean, loading: boolean){
        this.title = (loading ? 'Authenticating' : (authenticated ? 'Authentication Successful' : 'Authentication Failed'))
        this.message = message
        this.authenticated = authenticated
        this.loading = loading
    }
}

</script>

<style scoped>
.login {
    background: url("../assets/md-pattern_aygfiy_c_scale,w_3845.jpg") no-repeat;
    background-size: cover;
}

</style>
