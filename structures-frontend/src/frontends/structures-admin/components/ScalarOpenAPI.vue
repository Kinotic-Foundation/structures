<template>
    <v-container class="no-gutters" fill-height fluid>
        <v-row class="no-gutters fill-height">
            <v-col>

                <ApiReference :configuration="{ isEditable: true, spec: { url: this.url } }" />

            </v-col>
        </v-row>
    </v-container>
</template>

<script lang="ts">
import {Vue, Component, Prop} from 'vue-property-decorator'
import { ApiReference } from '@scalar/api-reference'

@Component({
    components: {ApiReference}
})
export default class ScalarOpenAPI extends Vue {

    @Prop({type: String, required: true})
    public id!: string
    public url: string = ''

    constructor() {
        super()
    }

    public beforeMount() {
        this.url = `${this.getBaseUrl()}/api-docs/${this.id}/openapi.json`
    }

    getBaseUrl(){
        let prefix = 'http'
        let port = '8080'
        if(window.location.protocol.startsWith('https')){
            prefix = 'https'
            port = '443'
        }
        return `${prefix}://${window.location.hostname}:${port}`
    }

}
</script>

<style scoped>

</style>
