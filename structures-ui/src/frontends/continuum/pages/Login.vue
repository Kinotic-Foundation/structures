<template>
    <v-app class="login" >
        <v-main>
            <v-container fluid fill-height>
                <v-layout align-center justify-center>
                    <v-flex xs12 sm8 md6 lg4>
                        <v-card class="elevation-1 pa-3">
                            <v-card-text>
                                <div class="layout column align-center">
                                    <v-img
                                            :src="require('../assets/logo.png')"
                                            contain
                                    ></v-img>
                                </div>
                                <v-form ref="form"
                                        v-model="valid"
                                        lazy-validation>
                                    <v-text-field
                                            :append-icon="icons.user"
                                            name="login"
                                            label="Login"
                                            type="text"
                                            required
                                            v-model="login"
                                            :rules="loginRules"
                                            @focus="hideAlert">
                                    </v-text-field>
                                    <v-text-field
                                            :append-icon="icons.password"
                                            name="password"
                                            label="Password"
                                            id="password"
                                            type="password"
                                            required
                                            v-model="password"
                                            :rules="passwordRules"
                                            @keyup.enter.capture="handleLogin"
                                            @focus="hideAlert">
                                    </v-text-field>
                                </v-form>
                            </v-card-text>
                            <div>
<!--                                <v-btn icon>-->
<!--                                    <v-icon color="blue">fa fa-facebook-square fa-lg</v-icon>-->
<!--                                </v-btn>-->
<!--                                <v-btn icon>-->
<!--                                    <v-icon color="red">fa fa-google fa-lg</v-icon>-->
<!--                                </v-btn>-->
<!--                                <v-btn icon>-->
<!--                                    <v-icon color="light-blue">fa fa-twitter fa-lg</v-icon>-->
<!--                                </v-btn>-->
                                <v-spacer></v-spacer>
                                <v-btn
                                        block
                                        color="primary"
                                        @click="handleLogin"
                                        :loading="loading">
                                    Login
                                </v-btn>
                            </div>
                        </v-card>
                    </v-flex>
                </v-layout>
            </v-container>
        </v-main>

        <notifications group="alert"
                       position="bottom center"
                       width="100%"
                       :duration="30000">
            <template slot="body" slot-scope="props">
                <v-alert type="error"
                         prominent
                         @click="props.close">
                    <v-row align="center">
                        <v-col class="grow">{{props.item.text}}</v-col>
                        <v-col class="shrink">
                            <v-btn @click="props.close"
                                   color="black"
                                   text>
                                Close
                            </v-btn>
                        </v-col>
                    </v-row>
                </v-alert>
            </template>
        </notifications>

    </v-app>
</template>

<script lang="ts">

    import {
        mdiLock,
        mdiAccount,
    } from '@mdi/js'
    import {Component, Vue} from "vue-property-decorator"
    import {inject} from "inversify-props";
    import {IContinuumUI, IUserState} from "@/frontends/continuum";

    type RuleValidator = (value: any) => string | boolean

    @Component({
        components: { }
    })
    export default class Login extends Vue {

        @inject()
        private userState!: IUserState

        @inject()
        private continuumUI!: IContinuumUI

        private icons: any = {
            user: mdiAccount,
            password: mdiLock
        }

        private valid: boolean = true
        private login: string = ''
        private password: string = ''
        private loading: boolean = false
        private loginRules: RuleValidator[] = [ (v) => !!v || 'Login required']
        private passwordRules: RuleValidator[] = [ (v) => !!v || 'Password required']


        public async handleLogin() {
            if ((this.$refs.form as Vue & { validate: () => boolean }).validate()) {
                this.loading = true
                try{

                    let uri: string = 'ws://127.0.0.1:58503/v1'
                    if(window.location.hostname !== "127.0.0.1"
                        && window.location.hostname !== "localhost"){
                        let prefix: string = 'ws'
                        if(window.location.protocol.startsWith('https')){
                            prefix = 'wss'
                        }
                        let port: string = ''
                        if(window.location.port !== ''){
                            // non standard prod deployment, use our specific port
                            port = ':58503'
                        }
                        uri = `${prefix}://${window.location.hostname}${port}/v1`
                    }

                    await this.userState.authenticate(uri, this.login, this.password)

                    await this.continuumUI.navigate('/')

                } catch (error: any) {
                    this.displayAlert(error.message)
                }
                this.loading = false
            }
        }

        private hideAlert() {
            (this.$notify as any as { close: (value: string) => void }).close('loginAlert')
        }

        private displayAlert(text: string) {
            this.$notify({ group: 'alert', type: 'loginAlert', text })
        }


    }

</script>

<style scoped>
    .login {
        background: url("../assets/loginBackground_w_3840.jpg") no-repeat;
        background-size: cover;
    }

    /*@media only screen and (max-width: 320px) {*/
    /*    .login {*/
    /*        background: url("../assets/loginBackground.jpg") no-repeat;*/
    /*    }*/
    /*}*/

</style>
