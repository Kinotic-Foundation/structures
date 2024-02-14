<template>
    <v-app class="login" >
        <v-main>
            <v-container fluid fill-height>

            </v-container>
        </v-main>
    </v-app>
</template>

<script lang="ts">

import {Component, Prop, Vue} from "vue-property-decorator"
import {CONTINUUM_UI} from "@/frontends/continuum"


@Component({
    components: { }
})
export default class LoginKeycloak extends Vue {

    @Prop({type: String, required: false, default: null})
    public referer!: string | null

    public async created() {
        // only could have reached here if we have properly been authenticated

        if(this.referer) {
            await CONTINUUM_UI.navigate(this.referer)
        }else{
            await CONTINUUM_UI.navigate('/')
        }
    }
}

</script>

