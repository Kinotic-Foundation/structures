<template>
    <v-menu
            v-model="launcherOpen"
            transition="slide-y-transition"
            bottom
            offset-y
            left>
        <template v-slot:activator="{ on }" >
            <v-btn text icon v-on="on">
                <v-icon>{{appsIcon}}</v-icon>
            </v-btn>
        </template>
        <v-card>
            <v-container fluid grid-list-xs class="pa-1">
                <v-layout row wrap>
                    <v-flex v-for="app in frontends"
                            :key="app.path" xs6>
                        <v-btn text
                               @click.stop="frontendSelected(app)">
                            <v-icon>{{app.meta.icon}}</v-icon>
                            <!--<span>{{app.meta.title}}</span>-->
                        </v-btn>
                    </v-flex>
                </v-layout>
            </v-container>
        </v-card>
    </v-menu>
</template>

<script lang="ts">
import Vue, { PropType } from 'vue'
import { Component, Prop, Emit } from 'vue-property-decorator'
import { RouteConfig } from 'vue-router'
import { mdiApps } from '@mdi/js'


@Component
export default class FrontendLauncher extends Vue {

    /**
     * All applications to choose from.
     * This is a special type of RouteConfig it must have a meta.icon and a meta.title property
     * TODO: extend RoutConfig to enforce the required meta.icon and meta.title property
     */
    @Prop({ type: Array as PropType<RouteConfig[]> , required: true })
    public frontends!: RouteConfig[]

    private appsIcon: string = mdiApps

    private launcherOpen: boolean = false

    @Emit()
    public frontendSelected(frontend: RouteConfig): RouteConfig {
        this.launcherOpen = false
        return frontend
    }

}
</script>
