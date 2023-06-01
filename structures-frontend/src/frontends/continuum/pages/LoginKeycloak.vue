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
    import {CONTINUUM_UI} from "@/frontends/continuum"


    @Component({
        components: { }
    })
    export default class LoginKeycloak extends Vue {

        public async created() {
          // only could have reached here if we have properly been authenticated

          await CONTINUUM_UI.navigate('/')

        }

    }

</script>

<style scoped>
    /*.login {*/
    /*    background: url("../assets/loginBackground_w_3840.jpg") no-repeat;*/
    /*    background-size: cover;*/
    /*}*/
</style>
