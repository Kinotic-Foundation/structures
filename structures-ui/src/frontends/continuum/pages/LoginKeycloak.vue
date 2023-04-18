<template>
    <v-app class="login" >
        <v-main>
            <v-container fluid fill-height>

            </v-container>
        </v-main>

    </v-app>
</template>

<script lang="ts">

    import {Component, Vue} from "vue-property-decorator"
    import {inject} from "inversify-props"
    import {IContinuumUI} from "@/frontends/continuum"


    @Component({
        components: { }
    })
    export default class LoginKeycloak extends Vue {

        @inject()
        private continuumUI!: IContinuumUI

        public async created() {
          // only could have reached here if we have properly been authenticated

          await this.continuumUI.navigate('/')

        }

    }

</script>

<style scoped>
    /*.login {*/
    /*    background: url("../assets/loginBackground_w_3840.jpg") no-repeat;*/
    /*    background-size: cover;*/
    /*}*/
</style>
