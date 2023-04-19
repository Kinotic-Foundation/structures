<template>
    <v-list>
        <template v-for='(step, index) in steps'>
            <v-list-group
                    v-if='step.steps && step.steps.length'
                    v-model="step.selected"
                    :key="'group'+index"
                    no-action>

                <template #prependIcon>
                    <orbit-spinner
                            v-if="isActive(step)"
                            :animation-duration="1000"
                            :size="24"
                            color="#2196f3"
                    />
                    <v-icon :color="getColor(step)" v-else>{{getPrependIcon(step)}}</v-icon>
                </template>

                <template #activator>
                    <v-list-item-content>
                        <v-list-item-title v-text="step.description"></v-list-item-title>
                        <v-list-item-subtitle v-if="showProgress(step)">
                            {{step.progress.message}}
                            <v-progress-linear :value="step.progress.percentageComplete"></v-progress-linear>
                        </v-list-item-subtitle>
                    </v-list-item-content>
                </template>

                <GrindList class="py-0 pl-6" :steps='step.steps'/>
            </v-list-group>
            <v-list-item @click='' v-else
                         :key="'items'+index">
                <v-list-item-icon>
                    <orbit-spinner
                            v-if="isActive(step)"
                            :animation-duration="1000"
                            :size="24"
                            color="#2196F3"
                    />
                    <v-icon :color="getColor(step)" v-else>{{getPrependIcon(step)}}</v-icon>
                </v-list-item-icon>
                <v-list-item-content>
                    <v-list-item-title>{{step.description}}</v-list-item-title>
                    <v-list-item-subtitle v-if="showProgress(step)">
                        {{step.progress.message}}
                        <v-progress-linear :value="step.progress.percentageComplete"></v-progress-linear>
                    </v-list-item-subtitle>
                </v-list-item-content>
            </v-list-item>
            <v-divider
                    v-if="index < steps.length - 1"
                    :key="'divider'+index"
                    inset
            ></v-divider>
        </template>
    </v-list>
</template>

<script lang="ts">
    import { Component, Prop, Vue } from 'vue-property-decorator'
    import { PropType } from 'vue'
    import { Step, StepState } from '../domain/grind/Step'
    import {
        mdiAlertCircleOutline,
        mdiCheckboxBlankCircleOutline,
        mdiCheckCircleOutline,
    } from '@mdi/js'
    import { OrbitSpinner } from 'epic-spinners'

    @Component({
        name: 'GrindList',
        components: { GrindList, OrbitSpinner}
    })
    export default class GrindList extends Vue {

        @Prop({ type: Array as PropType<Step[]> , required: true })
        public steps!: Step[]

        constructor() {
            super()
        }

        private showProgress(step: Step){
            return step.progress !== undefined && step.state !== StepState.FINISHED
        }

        private isActive(step: Step): boolean {
            return step.state === StepState.ACTIVE
        }

        private getPrependIcon(step: Step): string {
            let ret: string = mdiCheckboxBlankCircleOutline
            switch (step.state) {
                case StepState.FINISHED:
                    ret = mdiCheckCircleOutline
                    break
                case StepState.FAILED:
                    ret = mdiAlertCircleOutline
                    break
            }
            return ret
        }

        private getColor(step: Step): string {
            let ret: string = 'info'
            switch (step.state) {
                case StepState.FINISHED:
                    ret = 'success'
                    break
                case StepState.FAILED:
                    ret = 'error'
                    break
            }
            return ret
        }


    }
</script>

<style scoped>

</style>
